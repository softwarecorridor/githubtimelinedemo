package com.softwarecorridor.githubtimelinedemo.query_frag

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QueryFragViewModel : ViewModel() {
    val performingQuery : MutableLiveData<Boolean> = MutableLiveData(false)

    fun togglePerformingQuery() {
        performingQuery.value = !(performingQuery.value ?: false)
    }
}