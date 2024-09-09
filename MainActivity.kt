package com.example.contactapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    internal lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    internal lateinit var contactAdapter: ContactAdapter
    internal var contacts = mutableListOf<Contact>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferencesHelper = SharedPreferencesHelper(this)
        contacts = sharedPreferencesHelper.getContacts().toMutableList()

        val listView: ListView = findViewById(R.id.listView)
        contactAdapter = ContactAdapter(this, contacts)
        listView.adapter = contactAdapter

        val addButton: Button = findViewById(R.id.addButton)
        addButton.setOnClickListener {
            showContactDialog(null)
        }
    }

    internal fun showContactDialog(contact: Contact?) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_contact, null)
        val nameEditText: EditText = dialogView.findViewById(R.id.nameEditText)
        val phoneEditText: EditText = dialogView.findViewById(R.id.phoneEditText)

        if (contact != null) {
            nameEditText.setText(contact.name)
            phoneEditText.setText(contact.phone)
        }

        AlertDialog.Builder(this)
            .setTitle(if (contact == null) "Add Contact" else "Edit Contact")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val name = nameEditText.text.toString()
                val phone = phoneEditText.text.toString()
                if (contact == null) {
                    val newContact = Contact(System.currentTimeMillis().toString(), name, phone)
                    contacts.add(newContact)
                } else {
                    contact.name = name
                    contact.phone = phone
                }
                sharedPreferencesHelper.saveContacts(contacts)
                contactAdapter.notifyDataSetChanged()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
