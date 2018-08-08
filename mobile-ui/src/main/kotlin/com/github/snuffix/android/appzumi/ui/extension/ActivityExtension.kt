package com.github.snuffix.android.appzumi.ui.extension

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity

fun <T : ViewModel> AppCompatActivity.viewModel(factory: ViewModelProvider.Factory, viewModelClass:Class<T>) : T = ViewModelProviders.of(this, factory).get(viewModelClass)

