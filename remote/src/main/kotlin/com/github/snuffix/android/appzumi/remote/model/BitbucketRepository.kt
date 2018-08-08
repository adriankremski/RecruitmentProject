package com.github.snuffix.android.appzumi.remote.model


class BitbucketRepository(val owner: BitbucketRepositoryOwner,
                          val name: String,
                          val description: String?) {
}

class BitbucketRepositoryOwner(val username: String,
                               val links: HashMap<String, Link>?)

class Link(val href: String)
