package com.softwarecorridor.githubtimelinedemo.timeline_frag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.softwarecorridor.githubtimelinedemo.network.VolleySingleton


class TimeLineViewModelFactory(private val volley: VolleySingleton, private val url: String) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TimeLineViewModel(volley, url) as T
    }
}