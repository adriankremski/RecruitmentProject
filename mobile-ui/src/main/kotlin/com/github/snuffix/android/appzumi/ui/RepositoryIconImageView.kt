package com.github.snuffix.android.appzumi.ui

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.view.View
import com.github.snuffix.android.appzumi.presentation.model.Repository

class RepositoryIconImageView : AppCompatImageView {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun setRepository(repository: Repository) {
        var repositoryIconResource = when (repository.source) {
            "github" -> R.drawable.ic_github
            "bitbucket" -> R.drawable.ic_bitbucket
            else -> {
                null
            }
        }

        if (repositoryIconResource != null) {
            visibility = View.VISIBLE
            setImageResource(repositoryIconResource)
        } else {
            visibility = View.INVISIBLE
        }
    }
}