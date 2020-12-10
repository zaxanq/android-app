package com.example.aplikacja

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class HomeFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.home_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.addCarButton).setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_FirstFragment)
        }

        fun loadSpinnerResources(spinnerId: Spinner, resources: Int) {
            // load brands list
            // find and declare brand spinner
            val spinner: Spinner = spinnerId
            // get array of brands from resource (vehicleBrands.xml)
            ArrayAdapter.createFromResource(
                requireActivity(), // ?
                resources, // source
                android.R.layout.simple_spinner_item // map items to simple_spinner_item
            ).also { adapter -> // then
                // load simple_spinner_items as items of dropdown
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter // then load whole "adapter" into brand spinner
            }
        }

        loadSpinnerResources(view.findViewById(R.id.selectTypeSpinner), R.array.vehicleTypesArray)
        loadSpinnerResources(view.findViewById(R.id.selectBrandSpinner), R.array.vehicleBrandsArray)
        loadSpinnerResources(view.findViewById(R.id.selectModelSpinner), R.array.vehicleModelsArray)

    }
}

// this is supposed to alter ArrayAdapter and disable first item of resources
//class SpinnerAdapter(context: Context, resource: Int, list: ArrayList<String>)
//    : ArrayAdapter<String>(context, resource, list) {
//
//    override fun isEnabled(position: Int): Boolean {
//        // select position to disable
//        return position != 0
//    }
//}