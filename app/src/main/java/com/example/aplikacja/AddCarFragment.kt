package com.example.aplikacja

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
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
class AddCarFragment : Fragment() {
    private val selectTypeSpinner = view?.findViewById<Spinner>(R.id.selectTypeSpinner)
    private val selectBrandSpinner = view?.findViewById<Spinner>(R.id.selectExpenceType)
    private val selectModelSpinner = view?.findViewById<Spinner>(R.id.selectModelSpinner)
    private val addLicensePlateInput = view?.findViewById<EditText>(R.id.addPriceInput)
    private val addMeterStatusInput = view?.findViewById<EditText>(R.id.addAmountInput)
    private val warningText = view?.findViewById<TextView>(R.id.secondScreen_warning)
    private val addFirstCarTitleText = view?.findViewById<TextView>(R.id.addFirstCarTitle)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.addcar_screen, container, false)

        loadData()
    }

    private fun loadData() {
        val sharedPreferences = requireActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val savedName = sharedPreferences?.getString("NAME_KEY", null)
    }

    //fun File(args: Array<String>) {
    fun File() {

        val fileNameVehicle = "Vehicles.txt"

        context?.openFileInput(fileNameVehicle).use { stream ->
            val Vehicle = stream?.bufferedReader().use {
                it?.readText()
            }
            Log.d("TAG", "LOADED: $Vehicle")

            val addLicensePlateInput = view?.findViewById<EditText>(R.id.addPriceInput)
            val CarTag = "{$addLicensePlateInput.text}"


            val fileNameCarTag = "{$CarTag}.txt"
            var file = File(fileNameCarTag)
            var fileExists = file.exists()

            val selectTypeSpinner = view?.findViewById<Spinner>(R.id.selectTypeSpinner)
            val selectBrandSpinner = view?.findViewById<Spinner>(R.id.selectExpenceType)
            val selectModelSpinner = view?.findViewById<Spinner>(R.id.selectModelSpinner)
            val addMeterStatusInput = view?.findViewById<EditText>(R.id.addAmountInput)

            if (fileExists) {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this.context)
                builder.setTitle("Error")
                builder.setMessage("Pojazd o tej rejestracji już istnieje!")
                builder.setIcon(R.drawable.ic_launcher_background)

                builder.setPositiveButton("OK") { dialog, which ->
                    dialog.dismiss()
                }


            } else {
                //Indywidualny TXT
                val fileName = "{$addLicensePlateInput}.txt"
                var filebody = "${selectTypeSpinner?.selectedItem};${selectBrandSpinner?.selectedItem};${selectModelSpinner?.selectedItem};${addLicensePlateInput?.text};${addMeterStatusInput?.text}&"
                context?.openFileOutput(fileName, Context.MODE_PRIVATE).use { output ->
                    output?.write(filebody.toByteArray())

                    output?.close()


                    //ogólny TXT
                    val fileNameGeneral = "Vehicles.txt"
                    var filebodyGeneral = "{$Vehicle}&${addLicensePlateInput?.text}&"
                    context?.openFileOutput(fileNameGeneral, Context.MODE_PRIVATE).use { output ->
                        output?.write(filebodyGeneral.toByteArray())

                        output?.close()
                    }
                }
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectTypeSpinnerText: String = selectTypeSpinner?.selectedItem.toString()
        val selectBrandSpinnerText: String = selectBrandSpinner?.selectedItem.toString()
        val selectModelSpinnerText: String = selectModelSpinner?.selectedItem.toString()
        val addLicensePlateInputText: String = addLicensePlateInput?.text.toString().trim()
        val addMeterStatusInputText: String = addMeterStatusInput?.text.toString().trim()

        loadData()

        view.findViewById<Button>(R.id.addCarButton).setOnClickListener {
            if (selectTypeSpinnerText.trim().length <= 0) {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this.context)
                builder.setTitle("Error")
                builder.setMessage("Wybierz typ pojazdu!")
                builder.setIcon(R.drawable.ic_launcher_background)

                builder.setPositiveButton("OK") { dialog, which ->
                    dialog.dismiss()
                }
            } else if (selectBrandSpinnerText.trim().length <= 1) {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this.context)
                builder.setTitle("Error")
                builder.setMessage("Wybierz markę pojazdu!")
                builder.setIcon(R.drawable.ic_launcher_background)

                builder.setPositiveButton("OK") { dialog, which ->
                    dialog.dismiss()
                }
            } else if (selectModelSpinnerText.trim().length <= 1) {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this.context)
                builder.setTitle("Error")
                builder.setMessage("Wybierz model pojazdu!")
                builder.setIcon(R.drawable.ic_launcher_background)

                builder.setPositiveButton("OK") { dialog, which ->
                    dialog.dismiss()
                }
            } else {
                File()
                findNavController().navigate(R.id.action_addCarFragment_to_EkranGlownyFragment)
            }
        }
    }
}