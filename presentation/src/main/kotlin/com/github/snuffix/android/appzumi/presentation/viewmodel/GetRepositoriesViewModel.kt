package com.github.snuffix.android.appzumi.presentation.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.github.snuffix.android.appzumi.domain.model.RepositoryDomainModel
import com.github.snuffix.android.appzumi.domain.usecase.repository.GetRepositories
import com.github.snuffix.android.appzumi.presentation.data.Resource
import com.github.snuffix.android.appzumi.presentation.data.ResourceState
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

    fun fetchRepositories() {
        liveData.postValue(Resource(ResourceState.LOADING, null, null))
        return getRepositories.execute(FetchRepositoriesSubscriber())
    }

    inner class FetchRepositoriesSubscriber : DisposableSubscriber<List<RepositoryDomainModel>>() {

        override fun onComplete() {}

        override fun onNext(repositories: List<RepositoryDomainModel>) {
            liveData.postValue(Resource(ResourceState.SUCCESS,
                    repositories.map { repositoryMapper.mapToModel(it) }, null))
        }

        override fun onError(exception: Throwable) {
            liveData.postValue(Resource(ResourceState.ERROR, null, exception.message))
        }
    }
}