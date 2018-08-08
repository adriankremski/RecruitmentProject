package com.github.snuffix.android.appzumi.domain.usecase.repository

import com.github.snuffix.android.appzumi.domain.executor.PostExecutionThread
import com.github.snuffix.android.appzumi.domain.model.RepositoryDomainModel
import com.github.snuffix.android.appzumi.domain.repository.ReposDataRepository
import com.github.snuffix.android.appzumi.domain.usecase.FlowableUseCase
import io.reactivex.Flowable
import javax.inject.Inject

open class GetRepositories @Inject constructor(private val reposDataRepository: ReposDataRepository,
                                               postExecutionThread: PostExecutionThread) :
        FlowableUseCase<List<RepositoryDomainModel>, Void?>(postExecutionThread) {

    public override fun buildUseCaseObservable(params: Void?): Flowable<List<RepositoryDomainModel>> {
        return reposDataRepository.getRepositories()
    }
}