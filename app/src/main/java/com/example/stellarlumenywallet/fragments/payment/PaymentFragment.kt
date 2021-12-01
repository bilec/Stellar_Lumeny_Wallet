package com.example.stellarlumenywallet.fragments.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.stellarlumenywallet.R
import com.example.stellarlumenywallet.api.StellarApi
import com.example.stellarlumenywallet.databinding.FragmentPaymentBinding
import com.example.stellarlumenywallet.db.WalletRoomDatabase
import com.example.stellarlumenywallet.db.repositories.AccountRepository
import com.example.stellarlumenywallet.db.repositories.ActiveAccountRepository
import com.example.stellarlumenywallet.db.repositories.ContactRepository

class PaymentFragment: Fragment() {
    private lateinit var binding: FragmentPaymentBinding
    private lateinit var viewModel: PaymentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_payment, container, false)

        val database by lazy { WalletRoomDatabase.getDatabase(requireContext()) }
        val contactRepository by lazy { ContactRepository(database.contactDao()) }
        val accountRepository by lazy { AccountRepository(database.accountDao()) }
        val activeAccountRepository by lazy { ActiveAccountRepository(database.activeAccount()) }
        val viewModelFactory = PaymentViewModelFactory(contactRepository, accountRepository, activeAccountRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[PaymentViewModel::class.java]

        binding.buttonConfirm.setOnClickListener {
            val amount = binding.amountInputLayout.editText?.text.toString()
            val note = binding.noteInputLayout.editText?.text.toString()

            // StellarApi.send()
//            viewModel.accountsWithBalances.observe(this) {
//                it.first().balances.forEach { it.assetType }
//            }


            binding.amountInputLayout.editText?.setText("")
            binding.noteInputLayout.editText?.setText("")
        }

        return binding.root
    }
}