package com.example.aplikacja

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class EkranGlownyFragment : Fragment() {
    private val refuelingTitleText = view?.findViewById<TextView>(R.id.refuelingTitle)

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.ekran_glowny, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fileNameVehicle = "Vehicles.txt"

        context?.openFileInput(fileNameVehicle).use { stream ->
            val Vehicle = stream?.bufferedReader().use {
                it?.readText()
            }
            Log.d("TAG", "LOADED: $Vehicle")

            Toast.makeText(requireContext(), Vehicle, Toast.LENGTH_SHORT).show();
        }

        val menuButton: ImageButton = requireView().findViewById(R.id.menuButton)

        menuButton.setOnClickListener {
            Toast.makeText(requireContext(), "test", Toast.LENGTH_SHORT).show();
        }
    }
}