package com.softwarecorridor.githubtimelinedemo.query_frag

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.softwarecorridor.githubtimelinedemo.R
import com.softwarecorridor.githubtimelinedemo.databinding.FragmentFirstBinding
import com.softwarecorridor.githubtimelinedemo.network.VolleySingleton
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.StandardCharsets


private const val TAG = "FirstFragment"

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val model : QueryFragViewModel by viewModels()
        _binding  = DataBindingUtil.inflate(inflater, R.layout.fragment_first, container, false)
        binding.viewmodel = model
        binding.etUserInput.addTextChangedListener { text ->
            _binding?.tvUserWarning?.visibility = View.INVISIBLE
        }

        binding.etUserInput.setOnEditorActionListener { tv: TextView?, actionId: Int, _ ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_GO -> {
                    if (tv != null) {
                        val userInput = tv.text
                        performUserQuery(userInput.toString())
                    } else {
                        Log.d(TAG, "text box is null for some reason")
                    }
                    true
                }
                else -> false
            }
        }

        binding.buttonFirst.setOnClickListener { view: View? ->

            val textBox: EditText? = this.activity?.findViewById(R.id.et_user_input)
            if (textBox != null) {
                val userInput = textBox.text
                performUserQuery(userInput.toString())
            } else {
                Log.d(TAG, "text box is null for some reason")
            }
        }

        return binding.root
    }

    private fun performUserQuery(userInput: String) {
        _binding?.progressBar?.visibility = View.VISIBLE
        Log.d(TAG, "string: $userInput")
        val prefix = "https://api.github.com/users/"
        val stringRequest = StringRequest(
            Request.Method.GET, "$prefix$userInput",
            { response ->
                _binding?.progressBar?.visibility = View.GONE
                val responses = parseVolleyResponse(response)
                responses.putString("name", userInput)
                view?.findNavController()?.navigate(
                    R.id.action_FirstFragment_to_graphFragment,
                    responses
                )
            }) {
            parseVolleyError(it)
        }
        VolleySingleton.getInstance(requireContext().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun parseVolleyResponse(response: String): Bundle {
        val data = JSONObject(response)
        val bundle = Bundle()
        bundle.putString("avatar_url", data.getString("avatar_url"))
        bundle.putString("repos_url", data.getString("repos_url"))
        val email = data.getString("email")
        if (email != "null") {
            bundle.putString("email", email)
        }
        val location = data.getString("location")
        if (location != "null") {
            bundle.putString("location", location)
        }
        return bundle
    }

    private fun parseVolleyError(error: VolleyError) {
        try {
            val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
            val data = JSONObject(responseBody)
            val message = data.getString("message")
            Log.d(TAG, message)
            _binding?.progressBar?.visibility = View.GONE
            _binding?.tvUserWarning?.visibility = View.VISIBLE
        } catch (e: JSONException) {
            Log.e(TAG, "parseVolleyError", e)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}