package com.github.snuffix.android.appzumi.remote.mapper

interface EntityMapper<in M, out E> {
    fun mapFromRemote(type: M): E
}