package com.github.snuffix.android.appzumi.ui.adapters

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.github.snuffix.android.appzumi.presentation.model.Repository
import com.github.snuffix.android.appzumi.ui.R
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class RepositoryAdapterDelegate(private val requestManager: RequestManager) : AdapterDelegate<List<Repository>>() {

    override fun isForViewType(items: List<Repository>, position: Int): Boolean {
        return items[position] is Repository
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return RepositoryRowHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_repository, parent, false))
    }

    override fun onBindViewHolder(items: List<Repository>, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
        var repository = items[position]
        var repositoryRowHolder = holder as RepositoryRowHolder

        repositoryRowHolder.repository = repository
        requestManager.clear(repositoryRowHolder.userAvatarImageView)

        repository.avatarUrl?.let {
            var avatarPlaceholder = ContextCompat.getDrawable(holder.itemView.context, R.drawable.ic_person_grey_24dp)
            var avatarTransformation = bitmapTransform(RoundedCornersTransformation(128, 0)).placeholder(avatarPlaceholder)

            requestManager.load(repository.avatarUrl).apply(avatarTransformation).into(repositoryRowHolder.userAvatarImageView)
        }
    }

    class RepositoryRowHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.username)
        lateinit var usernameLabel: TextView

        @BindView(R.id.user_avatar)
        lateinit var userAvatarImageView: ImageView

        @BindView(R.id.repository_name)
        lateinit var repositoryNameLabel: TextView

        @BindView(R.id.repository_icon)
        lateinit var repositoryImageView: ImageView

        var repository: Repository? = null
            set(value) {
                field = value
                usernameLabel.text = field?.userName
                repositoryNameLabel.text = field?.repositoryName

                var repositoryIconResource = when (field?.source) {
                    "github" -> R.drawable.ic_github
                    "bitbucket" -> R.drawable.ic_bitbucket
                    else -> {
                        null
                    }
                }

                if (repositoryIconResource != null) {
                    repositoryImageView.visibility = View.VISIBLE
                    repositoryImageView.setImageResource(repositoryIconResource)
                } else {
                    repositoryImageView.visibility = View.GONE
                }
            }

        init {
            ButterKnife.bind(this, itemView)
        }
    }
}