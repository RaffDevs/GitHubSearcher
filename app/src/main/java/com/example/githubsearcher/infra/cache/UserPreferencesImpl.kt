package com.example.githubsearcher.infra.cache

import android.content.Context
import android.content.SharedPreferences

class UserPreferencesImpl(context: Context) : UserPreferences {
    private val preferences: SharedPreferences =
        context.getSharedPreferences("GitHubSearcher", Context.MODE_PRIVATE)

    override fun addUser(user: String) {
        val previousUsers = getUser()
        val users = previousUsers?.toMutableSet()

        users?.let {
            it.add(user)
            preferences.edit()
                .putStringSet("users", users)
                .apply()
        }

    }

    override fun getUser(): List<String>? {
        return preferences.getStringSet("users", setOf())?.toList()
    }
}