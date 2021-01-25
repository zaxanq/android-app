package com.example.aplikacja

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import android.widget.Button
import android.widget.EditText
import java.io.*


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private val selectTypeSpinner = view?.findViewById<Spinner>(R.id.selectTypeSpinner)
    private val selectBrandSpinner = view?.findViewById<Spinner>(R.id.selectExpenceType)
    private val selectModelSpinner = view?.findViewById<Spinner>(R.id.selectModelSpinner)
    private val addLicensePlateInput = view?.findViewById<EditText>(R.id.addPriceInput)
    private val addMeterStatusInput = view?.findViewById<EditText>(R.id.addAmountInput)


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.second_screen, container, false)

        loadData()
    }

    private fun loadData(){
        val shearedPreferences = requireActivity().getSharedPreferences("sheredPrefs", Context.MODE_PRIVATE)
        val savedName = shearedPreferences?.getString("NAME_KEY", null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.addCarButton).setOnClickListener {
            if (spinnersValid() && textFieldsValid()) {

                val addMeterStatusInput = view.findViewById<EditText>(R.id.addAmountInput)
                val selectTypeSpinner = view?.findViewById<Spinner>(R.id.selectTypeSpinner)
                val selectBrandSpinner = view?.findViewById<Spinner>(R.id.selectExpenceType)
                val selectModelSpinner = view?.findViewById<Spinner>(R.id.selectModelSpinner)
                val addLicensePlateInput = view?.findViewById<EditText>(R.id.addPriceInput)

                //Indywidualny TXT
                val fileName = "Vehicles.txt"
                var filebody = "${selectTypeSpinner.selectedItem};${selectBrandSpinner.selectedItem};${selectModelSpinner.selectedItem};${addLicensePlateInput.text};${addMeterStatusInput.text}&"

                context?.openFileOutput(fileName, Context.MODE_PRIVATE).use { output ->
                    output?.write(filebody.toByteArray())

                     output?.close()
                }
            }



            findNavController().navigate(R.id.action_Second_to_EkranGlowny)
        }
        // ładuj tylko spinner Rodzaju
        loadTypeSpinner(requireView().findViewById(R.id.selectTypeSpinner), R.array.vehicleTypesArray)
    }

    private fun loadTypeSpinner(spinner: Spinner, data: Int) {
        ArrayAdapter.createFromResource(
                requireActivity(),
                data, // dane z xml'a
                android.R.layout.simple_spinner_item // mapuj itemy na simple_spinner_item
        ).also { adapter ->
            // załaduj simple_spinner_item'y jako itemy dropdowna
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter // then załaduj cały "adapter"(?) do spinnera
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                // nadpisz metodę onItemSelected
                override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                    // po wybraniu opcji załaduj spinner Marki
                    loadBrandSpinner(requireView().findViewById(R.id.selectExpenceType))
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
        }
    }

    private fun loadBrandSpinner(spinner: Spinner) {
        val type = requireView().findViewById<Spinner>(R.id.selectTypeSpinner).selectedItem
        val typeId = requireView().findViewById<Spinner>(R.id.selectTypeSpinner).selectedItemId
        var data: Int
        data = when {
            // cars
            type === resources.getString(R.string.type_car) -> { R.array.vehicleBrandsArray_Car }
            type === resources.getString(R.string.type_motorcycle) -> { R.array.vehicleBrandsArray_Motorcycle }
            type === resources.getString(R.string.type_truck) -> { R.array.vehicleBrandsArray_Truck }
            else -> { R.array.empty } // opcja "Wybierz rodzaj" => wyczyśc spinner Marki
        }
        if (typeId == 0L) { // opcja "Wybierz rodzaj"
            // wyczyść spinner Modelu
            loadModelSpinner(requireView().findViewById(R.id.selectModelSpinner), true)
        }
        // załaduj spinner, tak jak zwykle
        ArrayAdapter.createFromResource(
                requireActivity(),
                data,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                    // po wybraniu marki załaduj spinner Modelu
                    loadModelSpinner(requireView().findViewById(R.id.selectModelSpinner))
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
        }
    }

    private fun loadModelSpinner(spinner: Spinner, clear: Boolean = false) {
        val brand = requireView().findViewById<Spinner>(R.id.selectExpenceType).selectedItem
        var data: Int
        if (!clear) { // jeżeli to nie jest czyszczenie spinnera, wybierz listę modeli
            data = when {
                // cars
                brand === resources.getString(R.string.brand_audi) -> { R.array.vehicleModelsArray_Audi }
                brand === resources.getString(R.string.brand_mazda) -> { R.array.vehicleModelsArray_Mazda }
                brand === resources.getString(R.string.brand_mercedes) -> { R.array.vehicleModelsArray_Mercedes }
                brand === resources.getString(R.string.brand_toyota) -> { R.array.vehicleModelsArray_Toyota }
                brand === resources.getString(R.string.brand_volvo) -> { R.array.vehicleModelsArray_Volvo }
                // motorcycles
                brand === resources.getString(R.string.brand_bmw) -> { R.array.vehicleModelsArray_BMW }
                brand === resources.getString(R.string.brand_harley) -> { R.array.vehicleModelsArray_HarleyDavidson }
                brand === resources.getString(R.string.brand_honda) -> { R.array.vehicleModelsArray_Honda }
                brand === resources.getString(R.string.brand_kawasaki) -> { R.array.vehicleModelsArray_Kawasaki }
                brand === resources.getString(R.string.brand_suzuki) -> { R.array.vehicleModelsArray_Suzuki }
                // trucks
                brand === resources.getString(R.string.brand_daf) -> { R.array.vehicleModelsArray_DAF }
                brand === resources.getString(R.string.brand_man) -> { R.array.vehicleModelsArray_MAN }
                brand === resources.getString(R.string.brand_scania) -> { R.array.vehicleModelsArray_Scania }
                brand === resources.getString(R.string.brand_iveco) -> { R.array.vehicleModelsArray_Iveco }
                brand === resources.getString(R.string.brand_volvoTruck) -> { R.array.vehicleModelsArray_VolvoTruck }
                else -> { R.array.empty } // opcja "Wybierz markę" => brak modeli do wybrania
            }
        } else {
            // czyszczenie spinnera Modeli
            data = R.array.empty
        }

        ArrayAdapter.createFromResource(
                requireActivity(),
                data,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    private fun spinnersValid(): Boolean {
        // sprawdza czy spinnery mają podane wartości i ewentualnie pokazuje error
        return spinnerOptionValid(requireView().findViewById(R.id.selectTypeSpinner))
                && spinnerOptionValid(requireView().findViewById(R.id.selectExpenceType))
                && spinnerOptionValid(requireView().findViewById(R.id.selectModelSpinner));
    }

    private fun spinnerOptionValid(spinner: Spinner?): Boolean {
        // sprawdza czy jeden konkretny spinner ma ustawioną wartość inną niż "Wybierz ..."
        return if (spinner?.selectedItemId == 0L) {
            requireView().findViewById<TextView>(R.id.secondScreen_warning)
                .text = resources.getString(R.string.warning_selectTypeBrandAndModel)
            false
        } else {
            true
        }
    }

    private fun textFieldsValid(): Boolean {
        // sprawdza czy pola są uzupełnione i czy stan licznika to liczba
        val warningText = requireView().findViewById<TextView>(R.id.secondScreen_warning)
        val addLicensePlateInputNotEmpty = requireView().findViewById<EditText>(R.id.addPriceInput).text.toString().isNotEmpty()
        val addMeterStatusInputText = requireView().findViewById<EditText>(R.id.addAmountInput).text.toString()
        val addMeterStatusInputNotEmpty = addMeterStatusInputText.isNotEmpty()

        return if (!addLicensePlateInputNotEmpty) { // jezeli rejestracja pusta
            warningText.text = resources.getString(R.string.warning_licensePlateRequired)
            false;
        } else if (!addMeterStatusInputNotEmpty) { // jezeli stan licznika pusty
            warningText.text = resources.getString(R.string.warning_meterStatusRequired)
            false;
        } else {
            // spróbuj skonwertować wpisaną wartość na floata
            val meterValue = try { addMeterStatusInputText.toFloat() } catch (error: NumberFormatException) { null }
            // jeżeli się nie udało (wpisane litery) lub wartość jest <= 0: pokaż błąd
            if (meterValue == null || meterValue <= 0F) {
                warningText.text = resources.getString(R.string.warning_invalidMeterStatus)
                false;
            } else {
                true;
            }
        }
    }
}
