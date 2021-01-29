package com.example.aplikacja

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController


class AddExpenceFragment : Fragment() {
//    private val selectExpenceType = view?.findViewById<Spinner>(R.id.selectExpenceType)
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


            //val selectExpenceType = view?.findViewById<Spinner>(R.id.selectExpenceType)
            val addPriceInput = view?.findViewById<EditText>(R.id.addPriceInput)
            val addAmountInput = view?.findViewById<EditText>(R.id.addAmountInput)
            val addAdditionalInfo = view?.findViewById<EditText>(R.id.addAdditionalInfo)
            val addExpenceName = view?.findViewById<EditText>(R.id.addExpenceName)


            val fileExpenceExist = "Expence.txt"

            context?.openFileInput(fileExpenceExist).use { stream ->
                val FileExistText = stream?.bufferedReader().use {
                    it?.readText()
                }
                Log.d("TAG", "LOADED: $Vehicle")

                //Toast.makeText(requireContext(), "$FileExistText", Toast.LENGTH_SHORT).show();

                val fileName = "Expence.txt"
                var filebody = "$FileExistText;${addExpenceName?.text};${addPriceInput?.text};${addAmountInput?.text};${addAdditionalInfo?.text}"
                context?.openFileOutput(fileName, Context.MODE_PRIVATE).use { output ->
                    output?.write(filebody.toByteArray())

                    output?.close()
                }
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addExpenceName = view?.findViewById<EditText>(R.id.addExpenceName)
        val addPriceInput = view?.findViewById<EditText>(R.id.addPriceInput)
        val addAmountInput = view?.findViewById<EditText>(R.id.addAmountInput)
        val addAdditionalInfo = view?.findViewById<EditText>(R.id.addAdditionalInfo)
        val warningText = view?.findViewById<TextView>(R.id.secondScreen_warning)
        val addFirstCarTitleText = view?.findViewById<TextView>(R.id.addFirstCarTitle)

        val addAmountInputString = requireView().findViewById<EditText>(R.id.addAmountInput).text.toString()
        val addPriceInputString = requireView().findViewById<EditText>(R.id.addAmountInput).text.toString()

        val addAmountInputNotEmpty = addAmountInputString.isNotEmpty()
        val addPriceInputNotEmpty = addPriceInputString.isNotEmpty()

        loadData()
        //Toast.makeText(requireContext(), "Działa", Toast.LENGTH_SHORT).show();
        view.findViewById<Button>(R.id.addExpenceButton).setOnClickListener {
            //Toast.makeText(requireContext(), "Działa", Toast.LENGTH_SHORT).show();
           // findNavController().navigate(R.id.action_addExpenceFragment_to_EkranGlownyFragment)
         /*   if (addExpenceName.length() <= 0) {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this.context)
                builder.setTitle("Error")
                builder.setMessage("Wybierz typ kosztu!")
                builder.setIcon(R.drawable.ic_launcher_background)

                builder.setPositiveButton("OK") { dialog, which ->
                    dialog.dismiss()
                    Toast.makeText(requireContext(), "1", Toast.LENGTH_SHORT).show();
                }
            }
            else if (!addAmountInputNotEmpty) { // jezeli stan licznika pusty
                val builder: AlertDialog.Builder = AlertDialog.Builder(this.context)
                builder.setTitle("Error")
                builder.setMessage("Wpisz ilość!")
                builder.setIcon(R.drawable.ic_launcher_background)

                builder.setPositiveButton("OK") { dialog, which ->
                    dialog.dismiss()
                    Toast.makeText(requireContext(), "2", Toast.LENGTH_SHORT).show();
                }
            }
            else if (!addPriceInputNotEmpty) { // jezeli stan licznika pusty
                val builder: AlertDialog.Builder = AlertDialog.Builder(this.context)
                builder.setTitle("Error")
                builder.setMessage("Wpisz cenę!")
                builder.setIcon(R.drawable.ic_launcher_background)

                builder.setPositiveButton("OK") { dialog, which ->
                    dialog.dismiss()
                    Toast.makeText(requireContext(), "3", Toast.LENGTH_SHORT).show();
                }
            }else {*/
                //Toast.makeText(requireContext(), "RAMPAMPAM", Toast.LENGTH_SHORT).show();
                    File()

                    findNavController().navigate(R.id.action_addExpenceFragment_to_EkranGlownyFragment)
                //}
            }
        }
    }
