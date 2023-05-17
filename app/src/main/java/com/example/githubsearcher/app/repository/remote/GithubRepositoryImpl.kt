package com.example.githubsearcher.app.repository.remote

import com.example.githubsearcher.infra.http.Client
import com.example.githubsearcher.repository.remote.entities.GithubRepository
import com.example.githubsearcher.app.repository.remote.entities.RepositoryEntity
import retrofit2.Call

class GithubRepositoryImpl: GithubRepository {
    private val api = Client.getApi()

    override fun getRepositoriesByUser(user: String): Call<List<RepositoryEntity>> {
        return api.getAllRepositoriesByUser(user)
    }
}