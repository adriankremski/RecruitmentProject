package com.github.snuffix.android.appzumi.remote

import io.reactivex.Flowable
import com.github.snuffix.android.appzumi.remote.model.GithubRepository
import retrofit2.http.GET

interface GithubApiService {
    @GET("repositories")
    fun getRepositories(): Flowable<List<GithubRepository>>
}