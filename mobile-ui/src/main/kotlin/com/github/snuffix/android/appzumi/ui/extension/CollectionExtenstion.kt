package com.github.snuffix.android.appzumi.ui.extension

import android.content.Context
import com.github.snuffix.android.appzumi.presentation.model.Repository

fun List<Repository>.sortedByName() : List<Repository> = this.sortedBy { it.repositoryName.toLowerCase() }
