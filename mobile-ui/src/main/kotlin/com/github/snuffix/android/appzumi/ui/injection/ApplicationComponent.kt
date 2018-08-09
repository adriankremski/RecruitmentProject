package com.github.snuffix.android.appzumi.ui.injection

import android.app.Application
import com.github.snuffix.android.appzumi.ui.injection.module.ApplicationModule
import com.github.snuffix.android.appzumi.ui.injection.scopes.PerApplication
import com.github.snuffix.android.appzumi.ui.repository.list.dagger.RepositoryDetailsScreenComponent
import com.github.snuffix.android.appzumi.ui.repository.list.dagger.RepositoryDetailsScreenModule
import com.github.snuffix.android.appzumi.ui.repository.list.dagger.RepositoryListScreenComponent
import com.github.snuffix.android.appzumi.ui.repository.list.dagger.RepositoryListScreenModule
import dagger.BindsInstance
import dagger.Component

@PerApplication
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun plusComponent(repositoryListScreenModule: RepositoryListScreenModule): RepositoryListScreenComponent
    fun plusComponent(repositoryDetailsScreenModule: RepositoryDetailsScreenModule): RepositoryDetailsScreenComponent
}
