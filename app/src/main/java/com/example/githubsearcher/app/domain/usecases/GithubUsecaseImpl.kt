package com.example.githubsearcher.app.domain.usecases

import com.example.githubsearcher.app.domain.callbacks.GithubUsecaseCallback
import com.example.githubsearcher.app.domain.model.Repository
import com.example.githubsearcher.app.domain.model.helpers.RepositoryHelper
import com.example.githubsearcher.app.repository.remote.GithubRepositoryImpl
import com.example.githubsearcher.app.repository.remote.entities.RepositoryEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubUsecaseImpl: GithubUsecase {
    private val repository = GithubRepositoryImpl()

    override fun getRepositories(user: String, callback: GithubUsecaseCallback) {
        repository.getRepositoriesByUser(user).enqueue(object: Callback<List<RepositoryEntity>> {
            override fun onResponse(
                call: Call<List<RepositoryEntity>>,
                response: Response<List<RepositoryEntity>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { listRepositoryEntity ->
                        val repositories = listRepositoryEntity.map {
                            RepositoryHelper.transformEntityToRepository(it)
                        }
                        callback.onSuccess(repositories)
                    }

                } else {
                    response.errorBody()?.let {
                        callback.onError(Error(it.string()))
                    }
                }
            }

            override fun onFailure(call: Call<List<RepositoryEntity>>, t: Throwable) {
                callback.onFailure(t)
            }

        })
    }


}