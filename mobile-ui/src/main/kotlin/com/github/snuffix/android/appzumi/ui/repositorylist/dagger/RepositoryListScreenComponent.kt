package com.github.snuffix.android.appzumi.ui.repositorylist.dagger

import com.github.snuffix.android.appzumi.ui.injection.scopes.PerActivity
import com.github.snuffix.android.appzumi.ui.repositorylist.RepositoryListScreen
import dagger.Subcomponent


@PerActivity
@Subcomponent(modules = [(RepositoryListScreenModule::class)])
public interface RepositoryListScreenComponent {
    fun inject(activity: RepositoryListScreen)
}