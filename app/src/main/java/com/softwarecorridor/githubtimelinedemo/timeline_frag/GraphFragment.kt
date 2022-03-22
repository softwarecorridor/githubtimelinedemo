package com.softwarecorridor.githubtimelinedemo.timeline_frag

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.VolleyError
import com.softwarecorridor.githubtimelinedemo.databinding.FragmentGraphBinding
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.StandardCharsets

private const val TAG = "GraphFragment"

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

        //TODO: display name and icon at the top
        _binding = FragmentGraphBinding.inflate(inflater, container, false)

        setUpTest()
        initRecyclerListView(_binding?.recyclerView)

        return binding.root
    }

    private fun initRecyclerListView(recyclerView: RecyclerView?) {
        if (recyclerView != null) {
            mLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            recyclerView.layoutManager = mLayoutManager
            mAdapter = TimeLineAdapter(mDataList)
            recyclerView.adapter = mAdapter
        }
    }

    private fun setUpTest() {
        mDataList.add(RepoModel("Test 1", "Just test 1"))
        mDataList.add(RepoModel("Test 2", "Just test 2"))
        mDataList.add(RepoModel("Test 3", "Just test 3"))
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