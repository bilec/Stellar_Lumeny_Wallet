package com.example.stellarlumenywallet.api

import android.util.Base64
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.example.stellarlumenywallet.db.entities.Transaction as DbTransaction
import org.stellar.sdk.*
import org.stellar.sdk.responses.AccountResponse
import org.stellar.sdk.responses.SubmitTransactionResponse
import org.stellar.sdk.responses.operations.CreateAccountOperationResponse
import org.stellar.sdk.responses.operations.PaymentOperationResponse
import shadow.okhttp3.OkHttpClient
import java.io.InputStream
import java.net.URL

object StellarApi {
    private val serverUrl = "https://horizon-testnet.stellar.org"
    private val friendBotUrl = "https://friendbot.stellar.org"

    private val server = createServer()

    private fun createServer(): Server {
        val server = Server(serverUrl)
        server.httpClient = OkHttpClient()
        return server
    }

    fun getAccount(accoundId: String): AccountResponse {
        val account = server.accounts().account(accoundId)
        return account
    }

    fun createKeyPair(): KeyPair = KeyPair.random()

    suspend fun openAccount(keyPair: KeyPair) {
        val response: InputStream = URL("$friendBotUrl/?addr=${keyPair.accountId}").openStream()
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

    suspend fun getBalance(accountId: String): String{
        getAccount(accountId).getBalances().forEach {
            if (it.assetType == AssetTypeNative().toString()){
                return it.balance
            }
        }
        throw Exception("AssetTypeNative not exist.")
    }

    suspend fun send(secretSeed: String, toAccountId: String, amount: String, note: String): SubmitTransactionResponse {
        val source: KeyPair = KeyPair.fromSecretSeed(secretSeed)
        val destination = KeyPair.fromAccountId(toAccountId)
        val sourceAccount = server.accounts().account(source.accountId)

        val transaction = Transaction.Builder(sourceAccount, Network.TESTNET)
            .addOperation(PaymentOperation.Builder(destination.accountId, AssetTypeNative(), amount).build())
            .setTimeout(180)
            .addMemo(Memo.text(note))
            .setBaseFee(Transaction.MIN_BASE_FEE)
            .build()

        transaction.sign(source)
        return server.submitTransaction(transaction)
    }

    fun secretSeedToString(secretSeed: CharArray): String {
        val sb = StringBuilder()
        secretSeed.forEach { sb.append(it) }
        return sb.toString()
    }

    fun getKeyPairFromSecretSeed(secretSeed: String): KeyPair = KeyPair.fromSecretSeed(secretSeed)
}