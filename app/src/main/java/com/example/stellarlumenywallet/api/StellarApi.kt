package com.example.stellarlumenywallet.api

import com.example.stellarlumenywallet.db.entities.Transaction as DbTransaction
import com.example.stellarlumenywallet.db.entities.Balance
import org.stellar.sdk.*
import org.stellar.sdk.responses.SubmitTransactionResponse
import org.stellar.sdk.responses.operations.CreateAccountOperationResponse
import org.stellar.sdk.responses.operations.PaymentOperationResponse
import java.io.InputStream
import java.net.URL

object StellarApi {
    private val serverUrl = "https://horizon-testnet.stellar.org"
    private val friendBotUrl = "https://friendbot.stellar.org"

    private val server = Server(serverUrl)

    fun createKeyPair(): KeyPair = KeyPair.random()

    suspend fun openAccount(keyPair: KeyPair) {
        val response: InputStream = URL("$friendBotUrl/?addr=${keyPair.accountId}").openStream()
    }

    suspend fun getBalances(accountId: String): List<Balance> {
        val account = server.accounts().account(accountId)
        val balances = mutableListOf<Balance>()

        account.balances.forEach {
            val balance = Balance(
                assetType = it.assetType,
                balance = it.balance,
                accountID = accountId
            )

            balances.add(balance)
        }

        return balances
    }

    suspend fun getTransactions(accountId: String): List<DbTransaction> {
        val transactions = mutableListOf<DbTransaction>()

        val operationsFromServer = server.operations().forAccount(accountId).execute()
        operationsFromServer.records.forEach {
            when (it) {
                is CreateAccountOperationResponse -> {
                    transactions.add(DbTransaction(id = it.id, startingBalance = it.startingBalance))
                }

                is PaymentOperationResponse -> {
                    val transactionType = if (it.from == accountId) "-" else "+"
                    transactions.add(DbTransaction(id = it.id, transactionType = transactionType, from = it.from, amount = it.amount, assetType = it.asset.type))
                }
            }
        }

        return transactions
    }

    suspend fun send(fromAccountKeyPair: KeyPair, toAccountId: String, asset: Asset = AssetTypeNative(), amount: String, note: String): SubmitTransactionResponse {
        val fromAccount = server.accounts().account(fromAccountKeyPair.accountId)

        val transaction = Transaction.Builder(fromAccount, Network.TESTNET)
            .addOperation(PaymentOperation.Builder(toAccountId, asset, amount).build())
            .setTimeout(180)
            .addMemo(Memo.text(note))
            .setBaseFee(Transaction.MIN_BASE_FEE)
            .build()

        transaction.sign(fromAccountKeyPair)
        return server.submitTransaction(transaction)
    }

    fun getPublicKeyFromSecretSeed(secretSeed: String): String = String(KeyPair.fromSecretSeed(secretSeed).publicKey)

    fun getAccountIdFromSecretSeed(secretSeed: String): String = KeyPair.fromSecretSeed(secretSeed).accountId
}