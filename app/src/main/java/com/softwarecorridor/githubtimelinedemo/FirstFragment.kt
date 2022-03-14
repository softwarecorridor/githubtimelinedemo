package com.softwarecorridor.githubtimelinedemo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.softwarecorridor.githubtimelinedemo.databinding.FragmentFirstBinding


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
            } else {
                Log.d(TAG, "text box is null for some reason")
            }

        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}