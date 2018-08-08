package com.github.snuffix.android.appzumi.remote

import com.github.snuffix.android.appzumi.remote.model.BitbucketRepository
import io.reactivex.Flowable
import retrofit2.http.GET

interface BitbucketApiService {
    @GET("repositories?fields=values.name,values.owner,values.description")
    fun getRepositories(): Flowable<BitbucketRepositoryResponse>

    class BitbucketRepositoryResponse {
        lateinit var values: List<BitbucketRepository>
    }
}