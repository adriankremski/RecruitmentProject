package com.github.snuffix.android.appzumi.presentation.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.github.snuffix.android.appzumi.domain.model.RepositoryDomainModel
import com.github.snuffix.android.appzumi.domain.usecase.GetRepositories
import com.github.snuffix.android.appzumi.presentation.data.Resource
import com.github.snuffix.android.appzumi.presentation.mapper.RepositoryMapper
import com.github.snuffix.android.appzumi.presentation.model.Repository
import io.reactivex.subscribers.DisposableSubscriber
import javax.inject.Inject

open class GetRepositoriesViewModel @Inject internal constructor(
        private val getRepositories: GetRepositories,
        private val repositoryMapper: RepositoryMapper) : ViewModel() {

    private val liveData = MutableLiveData<Resource<List<Repository>>>()

    override fun onCleared() {
        getRepositories.dispose()
        super.onCleared()
    }

    fun repositories(): LiveData<Resource<List<Repository>>> = liveData

    fun fetchRepositories(forceRefresh: Boolean) {
        liveData.postValue(Resource.loading())
        return getRepositories.execute(FetchRepositoriesSubscriber(), forceRefresh)
    }

    inner class FetchRepositoriesSubscriber : DisposableSubscriber<List<RepositoryDomainModel>>() {

        override fun onComplete() {}

        override fun onNext(repositories: List<RepositoryDomainModel>) {
            liveData.postValue(Resource.success(repositories.map { repositoryMapper.mapToModel(it) }))
        }

        override fun onError(exception: Throwable) {
            liveData.postValue(Resource.networkError())
        }
    }
}