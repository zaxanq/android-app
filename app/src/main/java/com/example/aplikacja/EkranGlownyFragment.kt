package com.example.aplikacja

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import java.lang.Math.round
import java.util.*
import kotlin.math.floor


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
//            Log.d("TAG", "LOADED: $Vehicle")

            //Toast.makeText(requireContext(), "$Vehicle", Toast.LENGTH_SHORT).show();
        }

        val fileNameLoad = "Vehicles.txt"

        context?.openFileInput(fileNameLoad).use { stream ->
            val Expence = stream?.bufferedReader().use {
                it?.readText()
            }
//            Log.d("TAG", "LOADED: $Expence")

            //Toast.makeText(requireContext(), "$Expence", Toast.LENGTH_SHORT).show();

            val values = "$Expence" // Read List from somewhere
            val lstValues: List<String> = values.split(";").map { it -> it.trim() }
            lstValues.forEach { it ->
//                Log.i("Values", "value=$it")
//                Toast.makeText(requireContext(), "value=$it", Toast.LENGTH_SHORT).show();
            }

        }



        //=========================

        val fileNameExpence = "Expence.txt"

        context?.openFileInput(fileNameExpence).use { stream ->
            val Expence = stream?.bufferedReader().use {
                it?.readText()
            }
//            Log.d("TAG", "LOADED: $Expence")

            //Toast.makeText(requireContext(), "$Expence", Toast.LENGTH_SHORT).show();

            val values = "$Expence" // Read List from somewhere
            val lstValues: List<String> = values.split(";").map { it.trim() }
            // zbieranie wydatkow majacych w nazwie "tankowanie" do statystyk tankowania
            var sumaCenPaliwa = 0F
            var sumaPaliwa = 0F
            var ostatniaCenaPaliwa = 0F

            // zbieranie wydatkow do wypisywania
            val wydatki: MutableList<String> = mutableListOf()
            var i = 1
            var j = 0
            var skip = false
            var tankowanie = false
            lstValues.forEach {
                if (i == 1) {
                    if (it !== "") {
                        wydatki.add("$it")
                        tankowanie = it.contains("tankowanie")
                    } else {
                        skip = true
                    }
                    i++
                } else {
                    if (!skip) {
                        wydatki.add("$it")

                        if (i == 2 && tankowanie) {
                            ostatniaCenaPaliwa = round(it.toFloat() * 100) / 100F
                        }
                        else if (i == 3 && tankowanie) {
                            sumaCenPaliwa += ostatniaCenaPaliwa * it.toFloat()
                            sumaPaliwa += it.toFloat()
                        }
                    }
                    i++
                    if (i == 5) {
                        i = 1
                        j++
                        skip = false
                        tankowanie = false
                    }
                }
            }

            if ((round((sumaCenPaliwa / sumaPaliwa) * 100) / 100F) > 0) {
                view.findViewById<TextView>(R.id.avgPriceValue).text = (round((sumaCenPaliwa / sumaPaliwa) * 100) / 100F).toString()
            }

            if (ostatniaCenaPaliwa > 0) {
                view.findViewById<TextView>(R.id.lastPriceValue).text =
                    ostatniaCenaPaliwa.toString()
            }



            listAdapter = CardViewListAdapter()

            recyclerView.adapter = listAdapter

            val reversed = wydatki.subList(0, (floor(wydatki.size / 4F) * 4).toInt()).asReversed()

            listAdapter.submitList(List(floor(wydatki.size / 4F).toInt()) {
                CardViewItem(UUID.randomUUID(), reversed)
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerView.adapter = null
    }
}