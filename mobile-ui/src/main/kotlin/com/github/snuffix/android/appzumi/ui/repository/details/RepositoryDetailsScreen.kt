package com.github.snuffix.android.appzumi.ui.repository.details

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.bumptech.glide.request.target.Target
import com.github.snuffix.android.appzumi.presentation.data.Resource
import com.github.snuffix.android.appzumi.presentation.data.ResourceState
import com.github.snuffix.android.appzumi.presentation.model.Repository
import com.github.snuffix.android.appzumi.presentation.viewmodel.GetRepositoryByIdViewModel
import com.github.snuffix.android.appzumi.presentation.viewmodel.RepositoriesViewModelFactory
import com.github.snuffix.android.appzumi.ui.R
import com.github.snuffix.android.appzumi.ui.RepositoryIconImageView
import com.github.snuffix.android.appzumi.ui.TheApplication
import com.github.snuffix.android.appzumi.ui.extension.viewModel
import com.github.snuffix.android.appzumi.ui.repository.list.dagger.RepositoryDetailsScreenModule
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import javax.inject.Inject

private const val REPOSITORY_ID_KEY: String = "REPOSITORY_ID_KEY"

fun Context.repositoryDetailsIntent(repositoryId: String): Intent {
    return Intent(this, RepositoryDetailsScreen::class.java).apply {
        putExtra(REPOSITORY_ID_KEY, repositoryId)
    }
}

class RepositoryDetailsScreen : AppCompatActivity() {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.user_avatar)
    lateinit var userAvatarImageView: ImageView

    @BindView(R.id.username)
    lateinit var usernameLabel: TextView

    @BindView(R.id.repository_name)
    lateinit var repositoryNameLabel: TextView

    @BindView(R.id.repository_icon)
    lateinit var repositoryIconImageView: RepositoryIconImageView

    @BindView(R.id.repository_description)
    lateinit var repositoryDescriptionLabel: TextView

    @Inject
    lateinit var repositoriesViewModelFactory: RepositoriesViewModelFactory

    private lateinit var getRepositoryByIdViewModel: GetRepositoryByIdViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TheApplication[this].appComponent.plusComponent(RepositoryDetailsScreenModule()).inject(this)
        setContentView(R.layout.activity_repository_details)
        ButterKnife.bind(this)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(true);
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        var repositoryId = intent.getStringExtra(REPOSITORY_ID_KEY)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            userAvatarImageView.transitionName = repositoryId
        }

        getRepositoryByIdViewModel = viewModel(repositoriesViewModelFactory, GetRepositoryByIdViewModel::class.java)
        getRepositoryByIdViewModel.result().observe(this,
                Observer<Resource<Repository>> { resource ->
                    resource?.let {
                        handleDataState(resource)
                    }
                })

        getRepositoryByIdViewModel.getRepositoryById(repositoryId)
    }

    private fun handleDataState(resource: Resource<Repository>) {
        when (resource.status) {
            ResourceState.SUCCESS -> {
                resource?.data?.let {
                    usernameLabel.text = "by ${it.userName}"
                    repositoryNameLabel.text = it.repositoryName
                    repositoryDescriptionLabel.text = it.description
                    repositoryIconImageView.setRepository(it)

                    it.avatarUrl?.let {
                        Glide.with(baseContext)
                                .load(it)
                                .apply(bitmapTransform(RoundedCornersTransformation(170, 0)))
                                .listener(object : RequestListener<Drawable> {
                                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                                        supportStartPostponedEnterTransition();
                                        return false
                                    }

                                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                                        supportStartPostponedEnterTransition();
                                        return false
                                    }

                                }).into(userAvatarImageView)
                    }
                }
            }
        }
    }
}