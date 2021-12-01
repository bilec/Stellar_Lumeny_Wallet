package com.example.stellarlumenywallet.fragments.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.stellarlumenywallet.R
import com.example.stellarlumenywallet.adapters.contacts.ContactsAdapter
import com.example.stellarlumenywallet.databinding.FragmentContactsBinding
import com.example.stellarlumenywallet.db.WalletRoomDatabase
import com.example.stellarlumenywallet.db.repositories.ContactRepository

class ContactsFragment: Fragment() {
    private lateinit var binding: FragmentContactsBinding
    private lateinit var viewModel: ContactsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contacts, container, false)

        val database by lazy { WalletRoomDatabase.getDatabase(requireContext()) }
        val repository by lazy { ContactRepository(database.contactDao()) }
        val viewModelFactory = ContactsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[ContactsViewModel::class.java]

        val adapter = ContactsAdapter()
        binding.recyclerView.adapter = adapter
        binding.floatingActionButton.setOnClickListener { it.findNavController().navigate(R.id.action_contactsFragment_to_newContactFragment) }

        viewModel.contacts.observe(this) {
            adapter.contacts = it
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }
}