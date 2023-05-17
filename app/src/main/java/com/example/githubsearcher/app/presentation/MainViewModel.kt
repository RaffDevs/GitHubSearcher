package com.example.githubsearcher.app.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubsearcher.app.domain.callbacks.GithubUsecaseCallback
import com.example.githubsearcher.app.domain.model.Repository
import com.example.githubsearcher.app.domain.usecases.GithubUsecaseImpl
import java.lang.Error

sealed class RequestState {
    data class Success(val value: List<Repository>): RequestState()
    data class Error(val error: String?): RequestState()
    data class Failure(val throwable: Throwable): RequestState()
}

class MainViewModel: ViewModel() {
    private val useCases = GithubUsecaseImpl()

    private val _repositories = MutableLiveData<List<Repository>>()
    val repositories: LiveData<List<Repository>> = _repositories

    private val _requestState = MutableLiveData<RequestState>()
    val requestState: LiveData<RequestState> = _requestState

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username



    fun getRepositoriesByUser(user: String) {
        useCases.getRepositories(user, object: GithubUsecaseCallback {
            override fun onSuccess(repositories: List<Repository>) {
                _requestState.value = RequestState.Success(repositories)
                _repositories.value = repositories
                _username.value = user
            }

            override fun onError(error: Error) {
                TODO("Implementar validação de array vazio.")
               _requestState.value = RequestState.Error(error.localizedMessage)
            }

            override fun onFailure(t: Throwable) {
                _requestState.value = RequestState.Failure(t)
            }

        })
    }
}