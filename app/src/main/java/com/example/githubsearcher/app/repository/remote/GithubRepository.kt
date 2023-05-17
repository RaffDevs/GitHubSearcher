package com.example.githubsearcher.repository.remote.entities

import com.example.githubsearcher.app.repository.remote.entities.RepositoryEntity
import retrofit2.Call

interface GithubRepository {
    fun getRepositoriesByUser(user: String): Call<List<RepositoryEntity>>

}