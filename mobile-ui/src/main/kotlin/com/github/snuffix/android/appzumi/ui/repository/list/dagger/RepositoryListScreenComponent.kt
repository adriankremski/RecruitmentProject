package com.github.snuffix.android.appzumi.ui.repository.list.dagger

import com.github.snuffix.android.appzumi.ui.injection.scopes.PerActivity
import com.github.snuffix.android.appzumi.ui.repository.list.RepositoryListScreen
import dagger.Subcomponent


@PerActivity
@Subcomponent(modules = [(RepositoryListScreenModule::class)])
public interface RepositoryListScreenComponent {
    fun inject(activity: RepositoryListScreen)
}