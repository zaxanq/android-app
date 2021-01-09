package com.example.aplikacja

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    private var nameInput: EditText? = null;
    private var passwordInput: EditText? = null;
    private var repeatPasswordInput: EditText? = null;
    private var continueButton: Button? = null;
    private var warningItem: TextView? = null;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.first_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nameInput = requireView().findViewById(R.id.nameInput);
        passwordInput = requireView().findViewById(R.id.passwordInput)
        repeatPasswordInput = requireView().findViewById(R.id.repeatPasswordInput)
        continueButton = requireView().findViewById(R.id.continueButton)
        warningItem = requireView().findViewById(R.id.firstScreen_warning)

        continueButton?.setOnClickListener {
            if (
                this.checkIfInputLengthIsValid(
                    nameInput,
                    3,
                    R.string.warning_nameTooShort
                ) &&
                this.checkIfInputLengthIsValid(
                    passwordInput,
                    5,
                    R.string.warning_passwordTooShort
                ) &&
                this.checkIfPasswordsAreIdentical()
            ) {
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }
        }
    }

    private fun checkIfInputLengthIsValid(
        input: EditText?,
        validLength: Int,
        warningId: Int
    ): Boolean {
        return if (input?.text.toString().length >= validLength) {
            warningItem?.text = "";
            true;
        } else {
            warningItem?.text = resources.getString(warningId);
            false;
        }
    }

    private fun checkIfPasswordsAreIdentical(): Boolean {
        return if (passwordInput?.text.toString() == repeatPasswordInput?.text.toString()) {
            warningItem?.text = "";
            true;
        } else {
            warningItem?.text = resources.getString(R.string.warning_passwordsAreNotIdentical);
            false;
        }
    }
}