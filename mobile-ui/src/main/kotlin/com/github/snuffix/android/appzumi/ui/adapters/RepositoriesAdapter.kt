package com.github.snuffix.android.appzumi.ui.adapters

import com.bumptech.glide.RequestManager
import com.github.snuffix.android.appzumi.presentation.model.Repository
import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter

class RepositoriesAdapter(repositories: List<Repository>, requestManager: RequestManager) : ListDelegationAdapter<List<Repository>>() {

    init {
        delegatesManager = AdapterDelegatesManager()
        delegatesManager.addDelegate(RepositoryAdapterDelegate(requestManager))
        setItems(repositories)
    }
}