package com.github.snuffix.android.appzumi.ui.repository.list.dagger

import com.github.snuffix.android.appzumi.domain.usecase.GetRepositories
import com.github.snuffix.android.appzumi.domain.usecase.GetRepositoryById
import com.github.snuffix.android.appzumi.presentation.mapper.RepositoryMapper
import com.github.snuffix.android.appzumi.presentation.viewmodel.RepositoriesViewModelFactory
import dagger.Module
import dagger.Provides


@Module
open class RepositoryListScreenModule {

    @Provides
    fun modelFactory(getRepositories: GetRepositories, getRepositoryById: GetRepositoryById, repositoryMapper: RepositoryMapper) =
            RepositoriesViewModelFactory(getRepositoryById = getRepositoryById, getRepositories = getRepositories, repositoryMapper = repositoryMapper)
}