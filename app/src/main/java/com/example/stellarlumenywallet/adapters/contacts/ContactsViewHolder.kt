package com.example.stellarlumenywallet.adapters.contacts

import androidx.recyclerview.widget.RecyclerView
import com.example.stellarlumenywallet.databinding.ItemContactBinding
import com.example.stellarlumenywallet.db.entities.Contact

class ContactsViewHolder(private val binding: ItemContactBinding): RecyclerView.ViewHolder(binding.root) {
    fun bindView(contact: Contact) {
        binding.name.text = contact.name
        binding.accountId.text = contact.accountId
    }
}