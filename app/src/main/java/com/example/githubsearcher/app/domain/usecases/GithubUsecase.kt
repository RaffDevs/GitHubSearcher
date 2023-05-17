package com.example.githubsearcher.app.domain.usecases

import com.example.githubsearcher.app.domain.callbacks.GithubUsecaseCallback
import com.example.githubsearcher.app.domain.model.Repository
import java.lang.Error

interface GithubUsecase {
    fun getRepositories(
        user: String,
        callback: GithubUsecaseCallback
    )
}