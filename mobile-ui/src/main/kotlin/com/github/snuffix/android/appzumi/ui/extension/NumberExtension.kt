package com.github.snuffix.android.appzumi.ui.extension

import android.content.Context

fun Int.dp2px(context: Context) : Int {
    val scale = context.resources.displayMetrics.density
    return (this * scale + 0.5f).toInt()
}
