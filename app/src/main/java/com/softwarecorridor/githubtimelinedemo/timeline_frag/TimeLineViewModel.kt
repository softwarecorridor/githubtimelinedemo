package com.softwarecorridor.githubtimelinedemo.timeline_frag

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TimeLineViewModel : ViewModel() {
    private val repoList : MutableLiveData<List<RepoModel>> by lazy {
        MutableLiveData<List<RepoModel>>().also {
            loadRepos()
        }
    }

    fun getRepos() : LiveData<List<RepoModel>> {
        return repoList
    }

    private fun loadRepos() {
        // Volley call
    }

}