package com.softwarecorridor.githubtimelinedemo.timeline_frag

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.softwarecorridor.githubtimelinedemo.R
import com.softwarecorridor.githubtimelinedemo.databinding.FragmentGraphBinding
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.StandardCharsets


private const val TAG = "GraphFragment"

// TODO: https://medium.com/google-developers/introduction-to-motionlayout-part-iii-47cd64d51a5
class GraphFragment : Fragment() {
    private var _binding: FragmentGraphBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var mAdapter: TimeLineAdapter
    private val mDataList = ArrayList<RepoModel>()
    private lateinit var mLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val name = arguments?.getString("name")
        val avatarUrl = arguments?.getString("avatar_url")
        val reposUrl = arguments?.getString("repos_url")

        if (reposUrl != null) {
            val queue = Volley.newRequestQueue(context)
            val prefix = "https://api.github.com/users/"
            val stringRequest = StringRequest(
                Request.Method.GET, reposUrl,
                { response ->
                    val repos = parseVolleyResponse(response)
                    if (mAdapter != null) {
                        val sortedRepo = repos.sortedWith(compareBy({ it.createTime }));
                        mAdapter.updateRepoList(sortedRepo)
                    }
                }) {
                parseVolleyError(it)
            }
            queue.add(stringRequest)
        }


        //TODO: display name and icon at the top
        _binding = FragmentGraphBinding.inflate(inflater, container, false)

        _binding?.appbarLayout?.findViewById<ImageButton>(R.id.backButton)
            ?.setOnClickListener { findNavController().navigate(R.id.action_GraphFragment_to_FirstFragment) }


        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    // Handle the back button event
                    findNavController().navigate(R.id.action_GraphFragment_to_FirstFragment)
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this.viewLifecycleOwner, callback)

        initRecyclerListView(_binding?.recyclerView)

        return binding.root
    }

    private fun initRecyclerListView(recyclerView: RecyclerView?) {
        if (recyclerView != null) {
            mLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            recyclerView.layoutManager = mLayoutManager
            mAdapter = TimeLineAdapter()
            recyclerView.adapter = mAdapter
        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}