package com.example.githubsearcher.infra.cache

interface UserPreferences {
    fun addUser(user: String)
    fun getUser(): List<String>?
}