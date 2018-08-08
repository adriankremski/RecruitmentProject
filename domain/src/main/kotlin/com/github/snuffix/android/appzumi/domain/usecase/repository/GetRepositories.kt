package com.github.snuffix.android.appzumi.domain.usecase.repository

import com.github.snuffix.android.appzumi.domain.executor.PostExecutionThread
import com.github.snuffix.android.appzumi.domain.model.RepositoryDomainModel
import com.github.snuffix.android.appzumi.domain.repository.ReposDataRepository
import com.github.snuffix.android.appzumi.domain.usecase.FlowableUseCase
import io.reactivex.Flowable
import javax.inject.Inject

open class GetRepositories @Inject constructor(private val reposDataRepository: ReposDataRepository,
                                               postExecutionThread: PostExecutionThread) :
        FlowableUseCase<List<RepositoryDomainModel>, Boolean?>(postExecutionThread) {

    public override fun buildUseCaseObservable(forceRefresh: Boolean?): Flowable<List<RepositoryDomainModel>> {
        return reposDataRepository.getRepositories(forceRefresh!!)
    }
}