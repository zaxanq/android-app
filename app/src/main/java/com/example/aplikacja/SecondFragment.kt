package com.example.aplikacja

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import java.io.File


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {
    private val selectTypeSpinner = view?.findViewById<Spinner>(R.id.selectTypeSpinner)
    private val selectBrandSpinner = view?.findViewById<Spinner>(R.id.selectBrandSpinner)
    private val selectModelSpinner = view?.findViewById<Spinner>(R.id.selectModelSpinner)
    private val addLicensePlateInput = view?.findViewById<EditText>(R.id.addLicensePlateInput)
    private val addMeterStatusInput = view?.findViewById<EditText>(R.id.addMeterStatusInput)
    private val warningText = view?.findViewById<TextView>(R.id.secondScreen_warning)


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.second_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectTypeSpinnerText: String = selectTypeSpinner?.selectedItem.toString()
        val selectBrandSpinnerText: String = selectBrandSpinner?.selectedItem.toString()
        val selectModelSpinnerText: String = selectModelSpinner?.selectedItem.toString()
        val addLicensePlateInputText: String = addLicensePlateInput?.text.toString().trim()
        val addMeterStatusInputText: String = addMeterStatusInput?.text.toString().trim()

        view.findViewById<Button>(R.id.addCarButton).setOnClickListener {
            if (selectTypeSpinnerText.trim().length <= 0) {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this.context)
                builder.setTitle("Error")
                builder.setMessage("Wybierz typ pojazdu!")
                builder.setIcon(R.drawable.ic_launcher_background)

                builder.setPositiveButton("OK") { dialog, which ->
                    dialog.dismiss()
                }
            }


            else if(selectBrandSpinnerText.trim().length<=1) {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this.context)
                builder.setTitle("Error")
                builder.setMessage("Wybierz markę pojazdu!")
                builder.setIcon(R.drawable.ic_launcher_background)

                builder.setPositiveButton("OK") { dialog, which ->
                    dialog.dismiss()
                }
            }

            else if(selectModelSpinnerText.trim().length<=1) {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this.context)
                builder.setTitle("Error")
                builder.setMessage("Wybierz model pojazdu!")
                builder.setIcon(R.drawable.ic_launcher_background)

                builder.setPositiveButton("OK") { dialog, which ->
                    dialog.dismiss()
                }
            }
            else{
                File("Cars.txt").writeText(selectTypeSpinnerText + ';' + selectBrandSpinnerText + ';' + selectModelSpinnerText + ';' + addLicensePlateInputText + ';' + addMeterStatusInputText)
                findNavController().navigate(R.id.action_SecondFragment_to_HomeFragment)
            }
        }

        fun loadSpinnerResources(spinner: Spinner, resources: Int) {
            // load brands list
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