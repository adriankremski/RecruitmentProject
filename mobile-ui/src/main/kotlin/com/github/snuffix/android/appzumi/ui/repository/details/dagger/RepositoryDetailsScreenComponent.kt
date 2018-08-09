package com.github.snuffix.android.appzumi.ui.repository.list.dagger

import com.github.snuffix.android.appzumi.ui.injection.scopes.PerActivity
import com.github.snuffix.android.appzumi.ui.repository.details.RepositoryDetailsScreen
import dagger.Subcomponent


@PerActivity
@Subcomponent(modules = [(RepositoryDetailsScreenModule::class)])
public interface RepositoryDetailsScreenComponent {
    fun inject(activity: RepositoryDetailsScreen)
}