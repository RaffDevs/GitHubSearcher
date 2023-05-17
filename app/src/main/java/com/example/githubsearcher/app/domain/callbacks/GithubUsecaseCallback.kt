package com.example.githubsearcher.app.domain.callbacks

import com.example.githubsearcher.app.domain.model.Repository
import java.lang.Error

interface GithubUsecaseCallback {
    fun onSuccess(repositories: List<Repository>)
    fun onError(error: Error)
    fun onFailure(t: Throwable)
}