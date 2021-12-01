package com.example.stellarlumenywallet.adapters.contacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stellarlumenywallet.databinding.ItemContactBinding
import com.example.stellarlumenywallet.db.entities.Contact

class ContactsAdapter: RecyclerView.Adapter<ContactsViewHolder>() {
    var contacts = listOf<Contact>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemContactBinding.inflate(layoutInflater, parent, false)
        return ContactsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.bindView(contacts[position])
    }

    override fun getItemCount(): Int = contacts.size
}