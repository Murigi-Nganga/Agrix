package com.example.agrix.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.agrix.R
import com.example.agrix.databinding.FragmentUpdateBinding
import com.example.agrix.dbhandler.DatabaseHandler
import com.example.agrix.model.Product
import com.google.android.material.snackbar.Snackbar
import java.util.*
import kotlin.collections.ArrayList

class UpdateFragment : Fragment() {

    private lateinit var binding: FragmentUpdateBinding
    private lateinit var db: DatabaseHandler
    private lateinit var productNames: ArrayList<String>

    override fun onResume() {
        super.onResume()

        // Ensure field is empty to prevent db update error in case element is deleted
        binding.dropdownMenuUpdate.setText("")

        // Add implementation in onResume to ensure item list is maintained
        val productsList: ArrayList<Product> = db.retrieveData()

        productNames = ArrayList()

        for (i in 0 until productsList.size) {
            productNames.add(productsList[i].productName)
        }

        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, productNames)   //context, item layout, item array
        binding.dropdownMenuUpdate.setAdapter(arrayAdapter)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentUpdateBinding.inflate(inflater, container, false)

        db = DatabaseHandler(requireContext())

//        binding.dropdownMenuUpdate.setOnItemClickListener()

        val c = Calendar.getInstance()
        val currentYear = c.get(Calendar.YEAR)

        // Add one because months are returned as indexes (starting from 0)
        val correctMonth: Int= c.get(Calendar.MONTH) + 1

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

        binding.dropdownMenuUpdate.onItemClickListener = AdapterView.OnItemClickListener {
                _, _, _ ,_->
            run {
                val product:Product = db.retrieveData(binding.dropdownMenuUpdate.text.toString())

                if(product.productName.isNotEmpty()) {
                    val currentQuantity = product.quantity.toString() + " " + product.measurement
                    binding.fragUpdateQuantityNumber.text = currentQuantity
                }

            }
        }

        binding.fragUpdateBtn.setOnClickListener {
            if(productNames.size == 0) {

                Snackbar.make(binding.root, "There are no products to update!", Snackbar.LENGTH_LONG).show()

            } else {
                val productName = binding.dropdownMenuUpdate.text.toString()
                val newQuantity = binding.fragUpdateEtQuantity.text.toString().toDouble()
                if (binding.dropdownMenuUpdate.text.isEmpty() || binding.fragUpdateEtQuantity.text.isEmpty()) {

                    Snackbar.make(binding.root, "Please fill in all fields!", Snackbar.LENGTH_LONG).show()

                } else {
                    val result: Int = db.updateData(productName, newQuantity, currentDateTime)

                    val snackMessage = if(result == -1) {
                        "Product updating failed!"
                    } else {
                        "Product updated successfully!"
                    }

                    Snackbar.make(binding.root, snackMessage, Snackbar.LENGTH_LONG).show()
                    binding.dropdownMenuUpdate.setText("")
                    binding.fragUpdateEtQuantity.setText("")
                    binding.fragUpdateQuantityNumber.text = "0.0"
                }
            }

        }

        return binding.root
    }
}

//SQLite db error
//android.database.sqlite.SQLiteException: no such column: Oranges (code 1): , while compiling: SELECT * FROM products WHERE ProductName = Oranges