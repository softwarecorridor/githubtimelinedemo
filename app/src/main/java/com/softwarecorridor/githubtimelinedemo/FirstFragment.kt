package com.softwarecorridor.githubtimelinedemo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.softwarecorridor.githubtimelinedemo.databinding.FragmentFirstBinding
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

        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        binding.buttonFirst.setOnClickListener { view: View? ->

            val textBox: EditText? = this.activity?.findViewById(R.id.et_user_input)
            if (textBox != null) {
                val userInput = textBox.text
                Log.d(TAG, "string: $userInput")
                val queue = Volley.newRequestQueue(context)
                val prefix = "https://api.github.com/users/"
                val stringRequest = StringRequest(
                    Request.Method.GET, "$prefix$userInput",
                    { response -> // Display the first 500 characters of the response string.
                        Log.d(TAG, "Response is: " + response.substring(0, 100))
                    }) {
                    parseVolleyError(it)
                }
                queue.add(stringRequest)
            } else {
                Log.d(TAG, "text box is null for some reason")
            }
        }

        return binding.root
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