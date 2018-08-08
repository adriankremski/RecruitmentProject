package com.github.snuffix.android.appzumi.ui.repositorylist.dagger

import com.github.snuffix.android.appzumi.domain.usecase.repository.GetRepositories
import com.github.snuffix.android.appzumi.presentation.mapper.RepositoryMapper
import com.github.snuffix.android.appzumi.presentation.viewmodel.RepositoriesViewModelFactory
import dagger.Module
import dagger.Provides


@Module
open class RepositoryListScreenModule {

    @Provides
    fun modelFactory(getRepositories: GetRepositories, repositoryMapper: RepositoryMapper):
            RepositoriesViewModelFactory = RepositoriesViewModelFactory(getRepositories = getRepositories, repositoryMapper = repositoryMapper)
}