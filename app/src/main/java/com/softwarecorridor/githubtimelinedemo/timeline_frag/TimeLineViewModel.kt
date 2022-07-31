package com.softwarecorridor.githubtimelinedemo.timeline_frag

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.softwarecorridor.githubtimelinedemo.network.VolleySingleton
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.StandardCharsets


private const val TAG = "TimeLineViewModel"

class TimeLineViewModel(private val volley: VolleySingleton, private val reposUrl: String) :
    ViewModel() {
    private val repoList: MutableLiveData<List<RepoModel>> by lazy {
        MutableLiveData<List<RepoModel>>().also {
            loadRepos()
        }
    }

    fun getRepos(): LiveData<List<RepoModel>> {
        return repoList
    }

    private fun loadRepos() {

        val stringRequest = StringRequest(
            Request.Method.GET, reposUrl,
            { response ->
                repoList.value = parseVolleyResponse(response)
            }) {
            parseVolleyError(it)
        }
        volley.addToRequestQueue(stringRequest)

    }

    private fun parseVolleyResponse(response: String): ArrayList<RepoModel> {
        val list = ArrayList<RepoModel>()

        val data = JSONArray(response)
        for (i in 0 until data.length()) {
            val repoData = data.getJSONObject(i)
            list.add(
                RepoModel(
                    repoData.getString("name"),
                    repoData.getString("description"),
                    repoData.getString("created_at"),
                    repoData.getString("updated_at")
                )
            )
        }

        return list
    }

    private fun parseVolleyError(error: VolleyError) {
        try {
            val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
            val data = JSONObject(responseBody)
            val message = data.getString("message")
            Log.d(TAG, message)
        } catch (e: JSONException) {
            Log.e(TAG, "parseVolleyError", e)
        }
    }

}