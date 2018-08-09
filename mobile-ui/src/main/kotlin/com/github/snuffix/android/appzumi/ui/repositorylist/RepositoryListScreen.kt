package com.github.snuffix.android.appzumi.ui.repositorylist

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.animation.AnimationUtils
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.github.snuffix.android.appzumi.presentation.data.Resource
import com.github.snuffix.android.appzumi.presentation.data.ResourceState
import com.github.snuffix.android.appzumi.presentation.model.Repository
import com.github.snuffix.android.appzumi.presentation.viewmodel.GetRepositoriesViewModel
import com.github.snuffix.android.appzumi.presentation.viewmodel.RepositoriesViewModelFactory
import com.github.snuffix.android.appzumi.ui.R
import com.github.snuffix.android.appzumi.ui.TheApplication
import com.github.snuffix.android.appzumi.ui.adapters.RepositoriesAdapter
import com.github.snuffix.android.appzumi.ui.adapters.decoration.VerticalSpaceItemDecoration
import com.github.snuffix.android.appzumi.ui.extension.viewModel
import com.github.snuffix.android.appzumi.ui.repositorylist.dagger.RepositoryListScreenModule
import javax.inject.Inject

class RepositoryListScreen : AppCompatActivity() {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.repositories_recycler)
    lateinit var repositoriesRecycler: RecyclerView

    @BindView(R.id.repositories_refresh)
    lateinit var repositoriesRefresh: SwipeRefreshLayout

    @BindView(R.id.error_screen)
    lateinit var errorScreenView: View

    @Inject
    lateinit var repositoriesViewModelFactory: RepositoriesViewModelFactory

    private lateinit var getRepositoriesViewModel: GetRepositoriesViewModel
    private lateinit var adapter: RepositoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TheApplication[this].appComponent.plusComponent(RepositoryListScreenModule()).inject(this)
        setContentView(R.layout.activity_repository_list)
        ButterKnife.bind(this)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(true);
        toolbar.title = getString(R.string.screen_title_repository_list)
        title = getString(R.string.screen_title_repository_list)

        repositoriesRecycler.layoutManager = LinearLayoutManager(this)
        adapter = RepositoriesAdapter(emptyList(), Glide.with(this))
        repositoriesRecycler.adapter = adapter
        repositoriesRecycler.addItemDecoration(VerticalSpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.spacing_baseline)))

        getRepositoriesViewModel = viewModel(repositoriesViewModelFactory, GetRepositoriesViewModel::class.java)

        repositoriesRefresh.setOnRefreshListener { fetchRepositories(true) }
        errorScreenView.setOnClickListener { fetchRepositories(true) }

        getRepositoriesViewModel.repositories().observe(this,
                Observer<Resource<List<Repository>>> { resource ->
                    resource?.let {
                        handleDataState(resource)
                    }
                })

        fetchRepositories(false)
    }

    private fun fetchRepositories(forceRefresh: Boolean) {
        getRepositoriesViewModel.fetchRepositories(forceRefresh)
    }

    private fun handleDataState(resource: Resource<List<Repository>>) {
        when (resource.status) {
            ResourceState.SUCCESS -> {
                showRepositories(resource.data!!)
            }
            ResourceState.ERROR -> {
                showError()
            }
            ResourceState.NETWORK_ERROR -> {
                showNetworkError()
            }
            ResourceState.LOADING -> {
                showLoading()
            }
        }
    }

    private fun showLoading() {
        repositoriesRefresh.isEnabled = true
        repositoriesRefresh.isRefreshing = true
        repositoriesRefresh.visibility = View.VISIBLE
        errorScreenView.visibility = View.GONE
    }

    private fun showRepositories(repositories: List<Repository>) {
        repositoriesRefresh.isRefreshing = false
        repositoriesRecycler.visibility = View.VISIBLE
        errorScreenView.visibility = View.GONE

        if (repositoriesRecycler.layoutAnimation == null) {
            var controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_fall_down);
            repositoriesRecycler.layoutAnimation = controller;
        }

        adapter.items = repositories
        adapter.notifyDataSetChanged()
        repositoriesRecycler.scheduleLayoutAnimation();
    }

    private fun showError() {
        repositoriesRefresh.isEnabled = false
        repositoriesRefresh.isRefreshing = false
        repositoriesRefresh.visibility = View.GONE
        repositoriesRecycler.visibility = View.GONE
        errorScreenView.visibility = View.VISIBLE
    }

    private fun showNetworkError() {
        showError()
    }
}