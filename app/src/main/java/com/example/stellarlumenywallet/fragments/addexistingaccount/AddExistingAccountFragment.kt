package com.example.stellarlumenywallet.fragments.addexistingaccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.stellarlumenywallet.R
import com.example.stellarlumenywallet.api.StellarApi
import com.example.stellarlumenywallet.databinding.FragmentAddExistingAccountBinding
import com.example.stellarlumenywallet.db.WalletRoomDatabase
import com.example.stellarlumenywallet.db.entities.Account
import com.example.stellarlumenywallet.db.repositories.AccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AddExistingAccountFragment: Fragment() {
    private lateinit var binding: FragmentAddExistingAccountBinding
    private lateinit var viewModel: AddExistingAccountViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_existing_account, container, false)

        val database by lazy { WalletRoomDatabase.getDatabase(requireContext()) }
        val repository by lazy { AccountRepository(database.accountDao()) }
        val viewModelFactory = AddExistingAccountViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[AddExistingAccountViewModel::class.java]

        binding.buttonSave.setOnClickListener {
            //val secretSeed = binding.secretSeedInputLayout.editText?.text.toString()
            val accountId = "GBW6TMLL3QMR4CDPW6RVVHPBLNUYWKMLBRKQEQU2CHWXAE7CFGFDKMBE"
            val stellarAccount = StellarApi.getAccount(accountId)
            val secretSeed = stellarAccount.keyPair.secretSeed.toString()
            val publicKey = stellarAccount.keyPair.publicKey.toString()

            val account = Account(accountId, publicKey, secretSeed)

            runBlocking(Dispatchers.IO) { launch { viewModel.insertAccount(account) } }
            it.findNavController().navigate(R.id.action_addExistingAccountFragment_to_accountManagementFragment)
        }

        return binding.root
    }
}