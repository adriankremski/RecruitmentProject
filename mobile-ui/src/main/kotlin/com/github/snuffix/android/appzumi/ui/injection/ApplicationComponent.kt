package com.github.snuffix.android.appzumi.ui.injection

import android.app.Application
import com.github.snuffix.android.appzumi.ui.TheApplication
import com.github.snuffix.android.appzumi.ui.injection.module.ApplicationModule
import com.github.snuffix.android.appzumi.ui.injection.scopes.PerApplication
import com.github.snuffix.android.appzumi.ui.main.dagger.MainScreenComponent
import com.github.snuffix.android.appzumi.ui.main.dagger.MainScreenModule
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

    fun plusComponent(mainScreenModule: MainScreenModule): MainScreenComponent
}
