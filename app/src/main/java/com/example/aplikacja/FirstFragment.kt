package com.example.aplikacja

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import java.io.File

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    //private val setYourPasswordTitleText = view?.findViewById<TextView>(R.id.setYourPasswordTitle)

    private var nameInput: EditText? = null;
    private var passwordInput: EditText? = null;
    private var repeatPasswordInput: EditText? = null;
    private var continueButton: Button? = null;
    private var warningItem: TextView? = null;

    private lateinit var savedName: String;
    private var savedName2 = ""

    private val nameDataFile = "Name.txt"
    private val passwordDataFile = "Password.txt"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.first_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nameInput = requireView().findViewById(R.id.nameInput)
        passwordInput = requireView().findViewById(R.id.passwordInput)
        repeatPasswordInput = requireView().findViewById(R.id.repeatPasswordInput)
        continueButton = requireView().findViewById(R.id.continueButton)
        warningItem = requireView().findViewById(R.id.firstScreen_warning)

        // POBIERZ DANE Z PREFERENCES
        loadData()
        if(savedName != "null" && savedName != ""){
            Snackbar.make(view, "'" + savedName + "'", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            findNavController().navigate(R.id.action_First_to_EkranGlowny)
        }

        continueButton!!.setOnClickListener {
            if (
                this.checkIfInputLengthIsValid(
                    nameInput,
                    3,
                    R.string.warning_nameTooShort,
                    R.string.warning_nameIsRequired
                ) &&
                this.checkIfInputLengthIsValid(
                    passwordInput,
                    5,
                    R.string.warning_passwordTooShort,
                    R.string.warning_passwordIsRequired
                ) &&
                this.checkIfPasswordsAreIdentical()
            ) {
                saveData()
                findNavController().navigate(R.id.action_First_to_Second)
            }
        }
    }

    private fun saveData(){
        val insertedText = nameInput?.text.toString();

        val sharedPreferences = requireActivity().getSharedPreferences("sheredPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.apply{
            putString("NAME_KEY", insertedText)
        }?.apply()

    }

    private fun loadData() {
        val sharedPreferences = requireActivity().getSharedPreferences("sheredPrefs", Context.MODE_PRIVATE)
        savedName = sharedPreferences.getString("NAME_KEY", null).toString()
    }

    private fun saveDataToFiles() {
        File(nameDataFile).writeText(nameInput!!.text.toString().trim())
        File(passwordDataFile).writeText(passwordInput!!.text.toString().trim())
    }

    private fun checkIfInputLengthIsValid(
        input: EditText?,
        validLength: Int,
        invalidLengthWarningId: Int,
        zeroLengthWarningId: Int
    ): Boolean {
        val textLength = input!!.text.toString().trim().length;
        return if (textLength >= validLength) {
            warningItem!!.text = "";
            true;
        } else if (textLength == 0) {
            warningItem!!.text = resources.getString(zeroLengthWarningId);
            false;
        } else {
            warningItem!!.text = resources.getString(invalidLengthWarningId);
            false;
        }
    }

    private fun checkIfPasswordsAreIdentical(): Boolean {
        return if (passwordInput!!.text.toString() == repeatPasswordInput!!.text.toString()) {
            warningItem!!.text = "";
            true;
        } else {
            warningItem!!.text = resources.getString(R.string.warning_passwordsAreNotIdentical);
            false;
        }
    }
}