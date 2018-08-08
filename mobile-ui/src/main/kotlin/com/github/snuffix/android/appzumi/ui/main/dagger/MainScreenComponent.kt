package com.github.snuffix.android.appzumi.ui.main.dagger

import com.github.snuffix.android.appzumi.ui.injection.scopes.PerActivity
import com.github.snuffix.android.appzumi.ui.main.MainActivity
import dagger.Subcomponent


@PerActivity
@Subcomponent(modules = [(MainScreenModule::class)])
public interface MainScreenComponent {
    fun inject(activity: MainActivity)
}