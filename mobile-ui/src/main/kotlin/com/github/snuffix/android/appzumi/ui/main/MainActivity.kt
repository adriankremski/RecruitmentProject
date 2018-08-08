package com.github.snuffix.android.appzumi.ui.main

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.github.snuffix.android.appzumi.presentation.data.Resource
import com.github.snuffix.android.appzumi.presentation.data.ResourceState
import com.github.snuffix.android.appzumi.presentation.model.Repository
import com.github.snuffix.android.appzumi.presentation.viewmodel.GetRepositoriesViewModel
import com.github.snuffix.android.appzumi.presentation.viewmodel.RepositoriesViewModelFactory
import com.github.snuffix.android.appzumi.ui.R
import com.github.snuffix.android.appzumi.ui.TheApplication
import com.github.snuffix.android.appzumi.ui.extension.viewModel
import com.github.snuffix.android.appzumi.ui.main.dagger.MainScreenModule
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var repositoriesViewModelFactory: RepositoriesViewModelFactory

    private lateinit var getRepositoriesViewModel: GetRepositoriesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TheApplication[this].appComponent.plusComponent(MainScreenModule()).inject(this)

        getRepositoriesViewModel = viewModel(repositoriesViewModelFactory, GetRepositoriesViewModel::class.java)

        getRepositoriesViewModel.repositories().observe(this,
                Observer<Resource<List<Repository>>> { resource ->
                    resource?.let {
                        when (resource.status) {
                            ResourceState.SUCCESS -> {
                                Toast.makeText(this, "Fetched repositories " + resource.data!!.size, Toast.LENGTH_SHORT).show()
                            }
                            ResourceState.ERROR -> {
                                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                            }
                            ResourceState.LOADING -> {
                                Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
    }

    override fun onStart() {
        super.onStart()
        getRepositoriesViewModel.fetchRepositories()
    }
}