package com.example.stellarlumenywallet.fragments.newcontact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.stellarlumenywallet.R
import com.example.stellarlumenywallet.databinding.FragmentNewContactBinding
import com.example.stellarlumenywallet.db.WalletRoomDatabase
import com.example.stellarlumenywallet.db.entities.Contact
import com.example.stellarlumenywallet.db.repositories.ContactRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class NewContactFragment: Fragment() {
    private lateinit var binding: FragmentNewContactBinding
    private lateinit var viewModel: NewContactViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_contact, container, false)

        val database by lazy { WalletRoomDatabase.getDatabase(requireContext()) }
        val repository by lazy { ContactRepository(database.contactDao()) }
        val viewModelFactory = NewContactViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[NewContactViewModel::class.java]

        binding.buttonSave.setOnClickListener {
            val name = binding.nameInputLayout.editText?.text.toString()
            val accountId = binding.accountIdInputLayout.editText?.text.toString()
            val contact = Contact(name = name, accountId = accountId)

            runBlocking(Dispatchers.IO) { launch { viewModel.insertContact(contact) } }
            it.findNavController().navigate(R.id.action_newContactFragment_to_contactsFragment)
        }

        return binding.root
    }
}