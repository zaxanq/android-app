package com.example.aplikacja

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import java.io.FileWriter


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class EditCarFragment : Fragment() {
    private val selectTypeSpinner = view?.findViewById<Spinner>(R.id.selectTypeSpinner)
    private val selectBrandSpinner = view?.findViewById<Spinner>(R.id.selectBrandSpinner)
    private val selectModelSpinner = view?.findViewById<Spinner>(R.id.selectModelSpinner)
    private val addLicensePlateInput = view?.findViewById<EditText>(R.id.addLicensePlateInput)
    private val addMeterStatusInput = view?.findViewById<EditText>(R.id.addMeterStatusInput)
    private val warningText = view?.findViewById<TextView>(R.id.secondScreen_warning)
    private val addFirstCarTitleText = view?.findViewById<TextView>(R.id.addFirstCarTitle)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.editcar_screen, container, false)


        loadData()

    }

    private fun loadData() {
        val shearedPreferences =
            requireActivity().getSharedPreferences("sheredPrefs", Context.MODE_PRIVATE)
        val savedName = shearedPreferences?.getString("NAME_KEY", null)

        //addFirstCarTitleText?.text = savedName
        //Toast.makeText(requireContext(), savedName, Toast.LENGTH_SHORT).show()
    }

    private fun CreateFile(str: String) {

        var fo = FileWriter("test.txt")
        fo.write("test")
        fo.close()
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
                CreateFile(addLicensePlateInputText)
                /*( val fileName = "data.txt"

                 var file = File(fileName)

                 // create a new file
                 file.writeText("test")*/


                //val fileName = "myfile.txt"
                //val myfile = File(fileName)

                //myfile.printWriter().use { out ->

                //    out.println("First line")
                //    out.println("Second line")
                //}
                //File.createNewFile()
                //File("Cars.txt").writeText(selectTypeSpinnerText + ';' + selectBrandSpinnerText + ';' + selectModelSpinnerText + ';' + addLicensePlateInputText + ';' + addMeterStatusInputText)
                //File("$addLicensePlateInputText.txt").writeText(selectTypeSpinnerText + ';' + selectBrandSpinnerText + ';' + selectModelSpinnerText + ';' + addLicensePlateInputText + ';' + addMeterStatusInputText)
                findNavController().navigate(R.id.action_Second_to_EkranGlowny)
            }
        }
    }
}