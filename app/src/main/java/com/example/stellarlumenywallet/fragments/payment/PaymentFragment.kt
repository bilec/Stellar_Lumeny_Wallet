package com.example.stellarlumenywallet.fragments.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.stellarlumenywallet.R
import com.example.stellarlumenywallet.databinding.FragmentPaymentBinding
import com.example.stellarlumenywallet.db.WalletRoomDatabase
import com.example.stellarlumenywallet.db.repositories.AccountRepository
import com.example.stellarlumenywallet.db.repositories.ContactRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class PaymentFragment: Fragment() {
    private lateinit var binding: FragmentPaymentBinding
    private lateinit var viewModel: PaymentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_payment, container, false)

        val database by lazy { WalletRoomDatabase.getDatabase(requireContext()) }
        val contactRepository by lazy { ContactRepository(database.contactDao()) }
        val accountRepository by lazy { AccountRepository(database.accountDao()) }
        val viewModelFactory = PaymentViewModelFactory(contactRepository, accountRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[PaymentViewModel::class.java]

        val adapter = ArrayAdapter(requireContext(), R.layout.item_account, emptyList<String>())
        binding.autoCompleteView.setAdapter(adapter)

        binding.autoCompleteView.setOnItemClickListener { adapterView, view, i, l ->
            GlobalScope.launch(Dispatchers.IO) {
                val contact = viewModel.contacts.value?.get(i)
                if (contact != null) {
                    viewModel.selectedContactAccountId = contact.accountId
                }
            }
        }

        binding.buttonConfirm.setOnClickListener {
            val amount = binding.amountInputLayout.editText?.text.toString()
            val note = binding.noteInputLayout.editText?.text.toString()

            binding.amountInputLayout.editText?.setText("")
            binding.noteInputLayout.editText?.setText("")
        }

        viewModel.contacts.observe(this) { contacts ->
            binding.autoCompleteView.setAdapter(ArrayAdapter(requireContext(), R.layout.item_account, contacts.map { it.name }))
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }
}