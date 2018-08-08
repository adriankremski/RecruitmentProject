package com.github.snuffix.android.appzumi.cache

import android.content.Context
import android.content.SharedPreferences

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class CachePreferences @Inject constructor(context: Context) {

    companion object {
        private const val PREF_PACKAGE_NAME = "com.github.snuffix.android.appzumi.cache.preferences"
        private const val PREF_KEY_LAST_CACHE = "last_cache"
    }

    private val preferences: SharedPreferences

    init {
        preferences = context.getSharedPreferences(PREF_PACKAGE_NAME, Context.MODE_PRIVATE)
    }

    var lastCacheTime: Long
        get() = preferences.getLong(PREF_KEY_LAST_CACHE, 0)
        set(lastCache) = preferences.edit().putLong(PREF_KEY_LAST_CACHE, lastCache).apply()
}
