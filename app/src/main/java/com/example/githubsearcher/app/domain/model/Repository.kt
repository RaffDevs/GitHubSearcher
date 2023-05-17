package com.example.githubsearcher.app.domain.model

import com.example.githubsearcher.app.repository.remote.entities.RepositoryEntity

data class Repository(
    val name: String,
    val url: String
)
