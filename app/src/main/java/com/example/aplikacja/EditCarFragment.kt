package com.example.aplikacja

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import java.io.FileWriter


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
        val sharedPreferences = requireActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val savedName = sharedPreferences?.getString("NAME_KEY", null)
    }

    private fun CreateFile(str: String) {
        var fo = FileWriter("test.txt")
        fo.write("test")
        fo.close()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}