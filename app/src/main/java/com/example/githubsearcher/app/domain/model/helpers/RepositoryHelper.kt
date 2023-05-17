package com.example.githubsearcher.app.domain.model.helpers

import com.example.githubsearcher.app.domain.model.Repository
import com.example.githubsearcher.app.repository.remote.entities.RepositoryEntity

object RepositoryHelper {
    fun transformEntityToRepository(repositoryEntity: RepositoryEntity): Repository {
        return Repository(name = repositoryEntity.name, url = repositoryEntity.htmlUrl)
    }
}