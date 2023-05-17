package com.example.githubsearcher.infra.http

import com.example.githubsearcher.infra.api.GithubApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Client {
   private lateinit var githubApi: GithubApi
   private val baseURL = "https://api.github.com/"

    fun getApi(): GithubApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        githubApi = retrofit.create(GithubApi::class.java)

        return githubApi
    }
}