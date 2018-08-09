package com.github.snuffix.android.appzumi.presentation.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.github.snuffix.android.appzumi.domain.usecase.GetRepositories
import com.github.snuffix.android.appzumi.presentation.mapper.RepositoryMapper

open class RepositoriesViewModelFactory(
        private val getRepositories: GetRepositories,
        private val repositoryMapper: RepositoryMapper) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(GetRepositoriesViewModel::class.java) -> GetRepositoriesViewModel(getRepositories, repositoryMapper) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}