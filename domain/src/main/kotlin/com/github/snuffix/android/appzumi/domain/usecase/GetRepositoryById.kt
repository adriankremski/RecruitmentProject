package com.github.snuffix.android.appzumi.domain.usecase

import com.github.snuffix.android.appzumi.domain.executor.PostExecutionThread
import com.github.snuffix.android.appzumi.domain.model.RepositoryDomainModel
import com.github.snuffix.android.appzumi.domain.repository.ReposDataRepository
import io.reactivex.Single
import javax.inject.Inject

open class GetRepositoryById @Inject constructor(private val reposDataRepository: ReposDataRepository,
                                                 postExecutionThread: PostExecutionThread) :
        SingleUseCase<RepositoryDomainModel, String?>(postExecutionThread) {

    public override fun buildUseCaseObservable(repositoryId: String?): Single<RepositoryDomainModel> {
        return reposDataRepository.getRepositoryById(repositoryId!!)
    }
}