package com.example.aplikacja

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class HomeFragment : Fragment() {

    private val textViewNameText = view?.findViewById<TextView>(R.id.textViewName)

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.home_screen, container, false)


        loadData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        loadData()
    }

    private fun loadData(){
        val shearedPreferences = requireActivity().getSharedPreferences("sheredPrefs", Context.MODE_PRIVATE)
        val savedName = shearedPreferences?.getString("NAME_KEY", null)


        //textViewNameText?.text = "savedName"
        //Toast.makeText(requireContext(), savedName, Toast.LENGTH_SHORT).show()
    }
}