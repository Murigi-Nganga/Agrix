package com.example.agrix.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.agrix.R
import com.example.agrix.databinding.FragmentDeleteBinding
import com.example.agrix.dbhandler.DatabaseHandler
import com.example.agrix.model.Product
import com.google.android.material.snackbar.Snackbar


class DeleteFragment : Fragment() {

    private lateinit var  binding: FragmentDeleteBinding
    private lateinit var db: DatabaseHandler
    private lateinit var productNames: ArrayList<String>

    override fun onResume() {
        super.onResume()

        // Add implementation in onResume to ensure item list is maintained
        val productsList: ArrayList<Product> = db.retrieveData()

        productNames = ArrayList()

        for (i in 0 until productsList.size) {
            productNames.add(productsList[i].productName)
        }

        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, productNames)   //context, item layout, item array
        binding.dropdownMenuDelete.setAdapter(arrayAdapter)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

         binding = FragmentDeleteBinding.inflate(inflater, container, false)
        db = DatabaseHandler(requireContext())

        binding.fragDeleteBtn.setOnClickListener {
            if(productNames.size == 0) {
                Snackbar.make(binding.root, "There are no products to delete", Snackbar.LENGTH_LONG).show()
            } else {
                val productToDelete = binding.dropdownMenuDelete.text.toString()
                if (binding.dropdownMenuDelete.text.isEmpty()) {

                    Snackbar.make(binding.root, "Please select an item to delete!", Snackbar.LENGTH_LONG).show()

                } else {

                    val result = db.deleteData(productToDelete)

                    val snackMessage = if(result == -1) {
                        "Product deletion failed!"
                    } else {
                        "Product deleted successfully!"
                    }

                    Snackbar.make(binding.root, snackMessage, Snackbar.LENGTH_LONG).show()
                    binding.dropdownMenuDelete.setText("")

                }
            }

        }

        return binding.root
    }
}