package com.example.githubsearcher.infra.api

import com.example.githubsearcher.app.repository.remote.entities.RepositoryEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {
    @GET("users/{user}/repos")
    fun getAllRepositoriesByUser(
        @Path("user") user: String
    ): Call<List<RepositoryEntity>>
}