package com.example.contactapp

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPreferencesHelper(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("contacts_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveContacts(contacts: List<Contact>) {
        val json = gson.toJson(contacts)
        sharedPreferences.edit().putString("contacts", json).apply()
    }

    fun getContacts(): List<Contact> {
        val json = sharedPreferences.getString("contacts", null) ?: return emptyList()
        val type = object : TypeToken<List<Contact>>() {}.type
        return gson.fromJson(json, type)
    }
}
