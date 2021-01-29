package com.example.aplikacja
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class EditCarFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.editcar_screen, container, false)

        loadData()
    }

    private fun loadData() {
        val sharedPreferences =
            requireActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val savedName = sharedPreferences?.getString("NAME_KEY", null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectTypeSpinner = view.findViewById<Spinner>(R.id.selectTypeSpinner)
        val selectBrandSpinner = view.findViewById<Spinner>(R.id.selectBrandSpinner)
        val selectModelSpinner = view.findViewById<Spinner>(R.id.selectModelSpinner)
        val addLicensePlateInput = view.findViewById<EditText>(R.id.addPriceInput)
        val addMeterStatusInput = view.findViewById<EditText>(R.id.addAmountInput)


        val dane = arrayOf(0,0,0,"","")
        val fileNameLoad = "Vehicles.txt"

        context?.openFileInput(fileNameLoad).use { stream ->
            val Expence = stream?.bufferedReader().use {
                it?.readText()
            }
            Log.d("TAG", "LOADED: $Expence")

            val values = "$Expence" // Read List from somewhere
            val lstValues: List<String> = values.split(";").map { it -> it.trim() }
            var i: Int = 0
            lstValues.forEach { it ->
                Log.i("Values", "value=$it")
                if (i <= 2) {
                    dane[i] = znajdz(it, i, dane)
                } else if (i >= 3) {
                    dane[i] = it
                }
                i++
            }
            loadTypeSpinner(requireView().findViewById(R.id.selectTypeSpinner), R.array.vehicleTypesArray)
            selectTypeSpinner.setSelection(dane[0] as Int, false)
            loadBrandSpinner(requireView().findViewById(R.id.selectBrandSpinner))
            selectBrandSpinner.setSelection(dane[1] as Int, false)
            loadModelSpinner(requireView().findViewById(R.id.selectModelSpinner))
            selectModelSpinner.setSelection(dane[2] as Int, false)
        }


        view.findViewById<Button>(R.id.addExpenceButton).setOnClickListener {
            if (spinnersValid() && textFieldsValid()) {
                val addMeterStatusInput = view.findViewById<EditText>(R.id.addAmountInput)
                val selectTypeSpinner = view.findViewById<Spinner>(R.id.selectTypeSpinner)
                val selectBrandSpinner = view.findViewById<Spinner>(R.id.selectBrandSpinner)
                val selectModelSpinner = view.findViewById<Spinner>(R.id.selectModelSpinner)
                val addLicensePlateInput = view.findViewById<EditText>(R.id.addPriceInput)

                //Indywidualny TXT
                val fileName = "Vehicles.txt"
                var filebody = "${selectTypeSpinner.selectedItem};${selectBrandSpinner.selectedItem};${selectModelSpinner.selectedItem};${addLicensePlateInput.text};${addMeterStatusInput.text}"

                context?.openFileOutput(fileName, Context.MODE_PRIVATE).use { output ->
                    output?.write(filebody.toByteArray())

                    output?.close()
                }
            }

            findNavController().navigate(R.id.action_editCarFragment_to_EkranGlowny)
        }

        selectTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            // nadpisz metodę onItemSelected
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                // po wybraniu opcji załaduj spinner Marki
                loadBrandSpinner(selectBrandSpinner)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        selectBrandSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            // nadpisz metodę onItemSelected
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                // po wybraniu opcji załaduj spinner Marki
                loadModelSpinner(selectModelSpinner)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        addLicensePlateInput.setText(dane[3] as String);
        addMeterStatusInput.setText(dane[4] as String);
    }

    private fun  znajdz(cos: String, gdzie: Int, dane: Array<Any>): Int {
        dane.map {item -> Log.i("dane", item.toString()) };
        if (gdzie == 0) { // typ
            if (cos == resources.getString(R.string.type_car)) {
                return 1;
            } else if (cos == resources.getString(R.string.type_motorcycle)) {
                return 2;
            } else {
                return 3;
            }
        } else if (gdzie == 1) { // marka
            if (dane[gdzie-1] == 1) { // typ = samochód
                if (cos == resources.getString(R.string.brand_audi)) {
                    return 1;
                } else if (cos == resources.getString(R.string.brand_mazda)) {
                    return 2;
                } else if (cos == resources.getString(R.string.brand_mercedes)) {
                    return 3;
                } else if (cos == resources.getString(R.string.brand_toyota)) {
                    return 4;
                } else {
                    return 5;
                }
            } else if (dane[gdzie-1] == 2) { // typ = motocykl
                if (cos == resources.getString(R.string.brand_bmw)) {
                    return 1;
                } else if (cos == resources.getString(R.string.brand_harley)) {
                    return 2;
                } else if (cos == resources.getString(R.string.brand_honda)) {
                    return 3;
                } else if (cos == resources.getString(R.string.brand_kawasaki)) {
                    return 4;
                } else {
                    return 5;
                }
            } else { // truck
                if (cos == resources.getString(R.string.brand_daf)) {
                    return 1;
                } else if (cos == resources.getString(R.string.brand_man)) {
                    return 2;
                } else if (cos == resources.getString(R.string.brand_scania)) {
                    return 3;
                } else if (cos == resources.getString(R.string.brand_iveco)) {
                    return 4;
                } else {
                    return 5;
                }
            }
        } else { // model
            if (arrayOf("A4","787B","W11","Supra","S60","M 1000 RR","Superflow 1200T","Fireblade","Ninja 1000","GSX-1300R Hayabusa","XF105","F2000","P320","Eurocargo","FH12").contains(cos)) {
                return 1;
            } else if (arrayOf("RS6","RX7","300SL","Camry","S90","R 1250 RT","Roadster","CBR650R","ZZR600","GV1400 Cavalcade","LF","TGA","R500","Stralis","FH16").contains(cos)) {
                return 2;
            } else if (arrayOf("Q8","RX8","Project One","Yaris","OV4","F 900 R","Iron 1200","GL1800 Gold Wing","KX450F","GSR750","CF","TGM","R730","Daily","FM12").contains(cos)) {
                return 3;
            } else if (arrayOf("R8","MX5","SLR","Hilux","XC90","K 1600 GT","Street Rod","CB500F","KLR600","GSX-S1000","XF","TGX","G400","Trakker","FL6").contains(cos)) {
                return 4;
            } else {
                return 5;
            }
        }
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
        }
    }

    private fun loadBrandSpinner(spinner: Spinner) {
        val type = requireView().findViewById<Spinner>(R.id.selectTypeSpinner).selectedItem
        var data: Int
        data = when {
            // cars
            type === resources.getString(R.string.type_car) -> { R.array.vehicleBrandsArray_Car }
            type === resources.getString(R.string.type_motorcycle) -> { R.array.vehicleBrandsArray_Motorcycle }
            type === resources.getString(R.string.type_truck) -> { R.array.vehicleBrandsArray_Truck }
            else -> { R.array.empty } // opcja "Wybierz rodzaj" => wyczyśc spinner Marki
        }
        // załaduj spinner, tak jak zwykle
        ArrayAdapter.createFromResource(
                requireActivity(),
                data,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    private fun loadModelSpinner(spinner: Spinner) {
        val brand = requireView().findViewById<Spinner>(R.id.selectBrandSpinner).selectedItem
        var data: Int
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
                && spinnerOptionValid(requireView().findViewById(R.id.selectBrandSpinner))
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
