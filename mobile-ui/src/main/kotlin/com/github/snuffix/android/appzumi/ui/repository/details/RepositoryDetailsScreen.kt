package com.github.snuffix.android.appzumi.ui.repository.details

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import butterknife.BindView
import butterknife.ButterKnife
import com.github.snuffix.android.appzumi.presentation.data.Resource
import com.github.snuffix.android.appzumi.presentation.data.ResourceState
import com.github.snuffix.android.appzumi.presentation.model.Repository
import com.github.snuffix.android.appzumi.presentation.viewmodel.GetRepositoryByIdViewModel
import com.github.snuffix.android.appzumi.presentation.viewmodel.RepositoriesViewModelFactory
import com.github.snuffix.android.appzumi.ui.R
import com.github.snuffix.android.appzumi.ui.TheApplication
import com.github.snuffix.android.appzumi.ui.extension.viewModel
import com.github.snuffix.android.appzumi.ui.repository.list.dagger.RepositoryDetailsScreenModule
import com.github.snuffix.android.appzumi.ui.repository.list.dagger.RepositoryListScreenModule
import javax.inject.Inject

private const val REPOSITORY_ID_KEY: String = "REPOSITORY_ID_KEY"

fun Context.repositoryDetailsIntent(repositoryId: String): Intent {
    return Intent(this, RepositoryDetailsScreen::class.java).apply {
        putExtra(REPOSITORY_ID_KEY, repositoryId)
    }
}

class RepositoryDetailsScreen : AppCompatActivity() {

    @Inject
    lateinit var repositoriesViewModelFactory: RepositoriesViewModelFactory

    private lateinit var getRepositoryByIdViewModel: GetRepositoryByIdViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TheApplication[this].appComponent.plusComponent(RepositoryDetailsScreenModule()).inject(this)
        setContentView(R.layout.activity_repository_details)
        ButterKnife.bind(this)

//        setSupportActionBar(toolbar)
//        supportActionBar!!.setDisplayShowTitleEnabled(true);

        getRepositoryByIdViewModel = viewModel(repositoriesViewModelFactory, GetRepositoryByIdViewModel::class.java)
        getRepositoryByIdViewModel.result().observe(this,
                Observer<Resource<Repository>> { resource ->
                    resource?.let {
                        handleDataState(resource)
                    }
                })

        getRepositoryByIdViewModel.getRepositoryById(intent.getStringExtra(REPOSITORY_ID_KEY))
    }

    private fun handleDataState(resource: Resource<Repository>) {
        when (resource.status) {
            ResourceState.SUCCESS -> {
            }
        }
    }
}