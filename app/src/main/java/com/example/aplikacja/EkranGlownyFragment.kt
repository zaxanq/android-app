package com.example.aplikacja

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import java.util.UUID


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

    private lateinit var listAdapter: CardViewListAdapter
    private val recyclerView: RecyclerView
        get() = requireView().findViewById(R.id.recyclerView)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fileNameVehicle = "Vehicles.txt"

        context?.openFileInput(fileNameVehicle).use { stream ->
            val Vehicle = stream?.bufferedReader().use {
                it?.readText()
            }
            Log.d("TAG", "LOADED: $Vehicle")

            //Toast.makeText(requireContext(), "$Vehicle", Toast.LENGTH_SHORT).show();
        }

        val fileNameLoad = "Vehicles.txt"

        context?.openFileInput(fileNameLoad).use { stream ->
            val Expence = stream?.bufferedReader().use {
                it?.readText()
            }
            Log.d("TAG", "LOADED: $Expence")

            //Toast.makeText(requireContext(), "$Expence", Toast.LENGTH_SHORT).show();

            val values = "$Expence" // Read List from somewhere
            val lstValues: List<String> = values.split(";").map { it -> it.trim() }
            lstValues.forEach { it ->
                Log.i("Values", "value=$it")
//                Toast.makeText(requireContext(), "value=$it", Toast.LENGTH_SHORT).show();
            }

        }



        //=========================

        val fileNameExpence = "Expence.txt"

        context?.openFileInput(fileNameExpence).use { stream ->
            val Expence = stream?.bufferedReader().use {
                it?.readText()
            }
            Log.d("TAG", "LOADED: $Expence")

            //Toast.makeText(requireContext(), "$Expence", Toast.LENGTH_SHORT).show();

            val values = "$Expence" // Read List from somewhere
            val lstValues: List<String> = values.split(";").map { it -> it.trim() }
            lstValues.forEach { it ->
                Log.i("Values", "value=$it")
//                Toast.makeText(requireContext(), "value=$it", Toast.LENGTH_SHORT).show();
            }

        }

        listAdapter = CardViewListAdapter()

        recyclerView.adapter = listAdapter

        listAdapter.submitList(List(10) {
            CardViewItem(UUID.randomUUID(), "No. $it")
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        recyclerView.adapter = null
    }
}