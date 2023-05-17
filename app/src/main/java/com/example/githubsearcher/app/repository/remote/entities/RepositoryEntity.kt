package com.example.githubsearcher.app.repository.remote.entities

import com.google.gson.annotations.SerializedName

data class RepositoryEntity(
    val name: String,

    @SerializedName("html_url")
    val htmlUrl: String
)
