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


class AddExpenceFragment : Fragment() {
    private val selectExpenceType = view?.findViewById<Spinner>(R.id.selectExpenceType)
    private val addPriceInput = view?.findViewById<EditText>(R.id.addPriceInput)
    private val addAmountInput = view?.findViewById<EditText>(R.id.addAmountInput)
    private val addAdditionalInfo = view?.findViewById<EditText>(R.id.addAdditionalInfo)
    private val warningText = view?.findViewById<TextView>(R.id.secondScreen_warning)
    private val addFirstCarTitleText = view?.findViewById<TextView>(R.id.addFirstCarTitle)


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.addexpence_screen, container, false)

        loadData()
    }

    private fun loadData() {
        val shearedPreferences = requireActivity().getSharedPreferences("sheredPrefs", Context.MODE_PRIVATE)
        val savedName = shearedPreferences?.getString("NAME_KEY", null)
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


            val selectExpenceType = view?.findViewById<Spinner>(R.id.selectExpenceType)
            val addPriceInput = view?.findViewById<EditText>(R.id.addPriceInput)
            val addAmountInput = view?.findViewById<EditText>(R.id.addAmountInput)
            val addAdditionalInfo = view?.findViewById<EditText>(R.id.addAdditionalInfo)

            val fileNameCarTag = "Expence.txt"
            var file = File(fileNameCarTag)
            var fileExists = file.exists()

            if (fileExists) {
                val fileExpenceExist = "Expence.txt"

                context?.openFileInput(fileExpenceExist).use { stream ->
                    val FileExist = stream?.bufferedReader().use {
                        it?.readText()
                    }
                    Log.d("TAG", "LOADED: $Vehicle")

                    val fileName = "Expence.txt"
                    var filebody = "{$FileExist}&${addPriceInput?.text};${selectExpenceType?.selectedItem};${addPriceInput?.text};${addAmountInput?.text};${addAdditionalInfo?.text}&"
                    context?.openFileOutput(fileName, Context.MODE_PRIVATE).use { output ->
                        output?.write(filebody.toByteArray())

                        output?.close()
                    }
                }
            } else {
                //Indywidualny TXT
                val fileName = "Expence.txt"
                var filebody = "${selectExpenceType?.selectedItem};${addPriceInput?.text};${addAmountInput?.text};${addAdditionalInfo?.text}&"
                context?.openFileOutput(fileName, Context.MODE_PRIVATE).use { output ->
                    output?.write(filebody.toByteArray())

                    output?.close()

                }
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectExpenceType = view?.findViewById<Spinner>(R.id.selectExpenceType)
        val addPriceInput = view?.findViewById<EditText>(R.id.addPriceInput)
        val addAmountInput = view?.findViewById<EditText>(R.id.addAmountInput)
        val addAdditionalInfo = view?.findViewById<EditText>(R.id.addAdditionalInfo)
        val warningText = view?.findViewById<TextView>(R.id.secondScreen_warning)
        val addFirstCarTitleText = view?.findViewById<TextView>(R.id.addFirstCarTitle)

        loadData()

        view.findViewById<Button>(R.id.addCarButton).setOnClickListener {
            if (selectExpenceType.trim().length <= 0) {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this.context)
                builder.setTitle("Error")
                builder.setMessage("Wybierz typ pojazdu!")
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
