package com.example.stellarlumenywallet.fragments.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.stellarlumenywallet.R
import com.example.stellarlumenywallet.api.StellarApi
import com.example.stellarlumenywallet.databinding.FragmentRegisterBinding
import com.example.stellarlumenywallet.db.WalletRoomDatabase
import com.example.stellarlumenywallet.db.entities.Account
import com.example.stellarlumenywallet.db.repositories.AccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import android.util.Base64
import org.stellar.sdk.Server
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class RegisterFragment: Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)

        val database by lazy { WalletRoomDatabase.getDatabase(requireContext()) }
        val repository by lazy { AccountRepository(database.accountDao()) }
        val viewModelFactory = RegisterViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[RegisterViewModel::class.java]

        val keyPair = StellarApi.createKeyPair()
        val secretSeed = StellarApi.secretSeedToString(keyPair.secretSeed)
        val publicKey = Base64.encodeToString(keyPair.publicKey, 0)
        val accountId = keyPair.accountId

        GlobalScope.launch(Dispatchers.IO) {
            StellarApi.openAccount(keyPair)
        }

        binding.accountIdLayout.editText?.setText(accountId)
        binding.publicKeyLayout.editText?.setText(publicKey)
        binding.secretSeedLayout.editText?.setText(secretSeed)

        binding.buttonSave.setOnClickListener {
            val account = Account(accountId, publicKey, secretSeed, "")

            runBlocking(Dispatchers.IO) { launch { viewModel.insertAccount(account) } }
            it.findNavController().navigate(R.id.action_registerFragment_to_accountManagementFragment)
        }

        return binding.root
    }
}