package com.github.snuffix.android.appzumi.remote

object GithubApiServiceFactory : ServiceFactory<GithubApiService>() {
    override fun apiService(): Class<GithubApiService> = GithubApiService::class.java
    override fun baseUrl(): String = "https://api.github.com/"
}