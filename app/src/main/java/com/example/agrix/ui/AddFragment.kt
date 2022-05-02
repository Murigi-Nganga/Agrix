package com.example.agrix.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.agrix.R
import com.example.agrix.databinding.FragmentAddBinding
import com.example.agrix.dbhandler.DatabaseHandler
import com.example.agrix.model.Product
import com.google.android.material.snackbar.Snackbar
import java.util.*


class AddFragment : Fragment() {

    // Fragment Binding object
    private lateinit var binding: FragmentAddBinding

    override fun onResume() {
        super.onResume()

        // Add implementation in onResume to ensure item list is maintained
        val items = resources.getStringArray(R.array.measuring_units)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, items)   //context, item layout, item array
        binding.dropdownMenuAdd.setAdapter(arrayAdapter)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAddBinding.inflate(inflater, container, false)
        val db = DatabaseHandler(requireContext())

        binding.fragAddBtn.setOnClickListener {
            val productInput= binding.fragAddEtItem.text
            val quantityInput = binding.fragAddEtQuantity.text
            val measurementInput = binding.dropdownMenuAdd.text

            if (productInput.toString().trim().isEmpty() || quantityInput.isNullOrEmpty() || measurementInput.isEmpty()) {

                    Snackbar.make(binding.root, "Please fill in all fields", Snackbar.LENGTH_LONG)
                        .show()

            } else {
                val productName = binding.fragAddEtItem.text.toString().trim()
                val productNameCapitalized = productName[0].uppercaseChar() + productName.substring(1)
                val measurement = binding.dropdownMenuAdd.text.toString()
                val quantity = quantityInput.toString().toDouble()

                val c = Calendar.getInstance()
                val currentYear = c.get(Calendar.YEAR)

                // Add one because months are returned as indexes (starting from 0)
                val correctMonth: Int= c.get(Calendar.MONTH) + 1

                // Add a 0 before month number if less than 10
                val currentMonth: String  = if(correctMonth in 1..9) {
                    "0${correctMonth }"
                } else {
                    "$correctMonth"
                }

                val currentDay: String  = if(c.get(Calendar.DAY_OF_MONTH) < 10) {
                    "0${c.get(Calendar.DAY_OF_MONTH) }"
                } else {
                    "${c.get(Calendar.DAY_OF_MONTH) }"
                }

                val currentHour = c.get(Calendar.HOUR)
                // Pad a 0 before if the minute digit is less than 10
                val currentMinute: String  = if(c.get(Calendar.MINUTE) < 10) {
                    "0${c.get(Calendar.MINUTE) }"
                } else {
                    "${c.get(Calendar.MINUTE) }"
                }

                val amOrPm: String = when (c.get(Calendar.AM_PM)) {
                    0 -> "AM"
                    else -> "PM"
                }

                val currentDateTime =  "$currentDay-$currentMonth-$currentYear $currentHour:$currentMinute $amOrPm"

                val productToInsert = Product(productNameCapitalized, measurement, quantity, currentDateTime)

                val result = db.insertData(productToInsert)

                val snackMessage = if(result == -1) {
                    "Product already exists!"
                } else {
                    "Product added successfully!"
                }

                Snackbar.make(binding.root, snackMessage, Snackbar.LENGTH_LONG).show()
                binding.fragAddEtItem.setText("")
                binding.fragAddEtQuantity.setText("")
                binding.dropdownMenuAdd.setText("")
            }
        }

        return binding.root
    }
}