package com.github.snuffix.android.appzumi.presentation.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.github.snuffix.android.appzumi.domain.model.RepositoryDomainModel
import com.github.snuffix.android.appzumi.domain.usecase.GetRepositoryById
import com.github.snuffix.android.appzumi.presentation.data.Resource
import com.github.snuffix.android.appzumi.presentation.mapper.RepositoryMapper
import com.github.snuffix.android.appzumi.presentation.model.Repository
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

open class GetRepositoryByIdViewModel @Inject internal constructor(
        private val getRepositoryById: GetRepositoryById,
        private val repositoryMapper: RepositoryMapper) : ViewModel() {

    private val liveData = MutableLiveData<Resource<Repository>>()

    override fun onCleared() {
        getRepositoryById.dispose()
        super.onCleared()
    }

    fun result(): LiveData<Resource<Repository>> = liveData

    fun getRepositoryById(repositoryId: String) {
        liveData.postValue(Resource.loading())
        return getRepositoryById.execute(FetchRepositoryObserver(), repositoryId)
    }

    inner class FetchRepositoryObserver : DisposableSingleObserver<RepositoryDomainModel>() {
        override fun onSuccess(repository: RepositoryDomainModel) {
            liveData.postValue(Resource.success(repositoryMapper.mapToModel(repository)))
        }

        override fun onError(exception: Throwable) {
            liveData.postValue(Resource.networkError())
        }
    }
}