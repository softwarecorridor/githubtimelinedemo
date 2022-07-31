package com.softwarecorridor.githubtimelinedemo.timeline_frag

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.util.LruCache
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.NetworkImageView
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.softwarecorridor.githubtimelinedemo.R
import com.softwarecorridor.githubtimelinedemo.databinding.FragmentGraphBinding
import com.softwarecorridor.githubtimelinedemo.network.VolleySingleton
import org.json.JSONArray
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
        _binding = FragmentGraphBinding.inflate(inflater, container, false)


        val name = arguments?.getString("name")
        val email = arguments?.getString("email")
        val location = arguments?.getString("location")
        val avatarUrl = arguments?.getString("avatar_url")
        val reposUrl = arguments?.getString("repos_url")
        val volley = VolleySingleton.getInstance(requireContext().applicationContext)


        //TODO: display name and icon at the top


        _binding?.appbarLayout?.findViewById<TextView>(R.id.nameTextView)
            ?.text = name

        if (location != null) {
            _binding?.appbarLayout?.findViewById<TextView>(R.id.locationTextView)?.text = location
        } else {
            _binding?.appbarLayout?.findViewById<TextView>(R.id.locationTextView)?.visibility = View.GONE
        }

        if (email != null) {
            _binding?.appbarLayout?.findViewById<TextView>(R.id.emailTextView)?.text = email
        } else {
            _binding?.appbarLayout?.findViewById<TextView>(R.id.emailTextView)?.visibility = View.GONE
        }


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


        if (reposUrl != null) {
            // TODO: handle null better
            val timelineViewModel : TimeLineViewModel by viewModels { TimeLineViewModelFactory(volley, reposUrl) }
            timelineViewModel.getRepos().observe(this, Observer<List<RepoModel>>{
                    list -> mAdapter.updateRepoList(list)
            })
        }

        if (avatarUrl != null) {
            val imageView = _binding?.appbarLayout?.findViewById<NetworkImageView>(R.id.imageView)
            volley.imageLoader.get(avatarUrl, ImageLoader.getImageListener(imageView,
                R.drawable.ic_launcher_background, android.R.drawable
                    .ic_dialog_alert));
            imageView?.setImageUrl(avatarUrl, volley.imageLoader);
        }


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







    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}