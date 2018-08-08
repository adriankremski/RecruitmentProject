package com.github.snuffix.android.appzumi.remote

object BitbucketServiceFactory : ServiceFactory<BitbucketApiService>() {
    override fun apiService(): Class<BitbucketApiService> = BitbucketApiService::class.java
    override fun baseUrl(): String = "https://api.bitbucket.org/2.0/"
}