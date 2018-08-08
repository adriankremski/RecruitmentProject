package com.github.snuffix.android.appzumi.cache.mapper

interface Mapper<Cached, Entity> {

    fun mapFromCached(cached: Cached): Entity

    fun mapToCached(entity: Entity): Cached
}