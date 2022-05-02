package com.example.agrix.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import com.example.agrix.databinding.FragmentRecordsBinding
import com.example.agrix.databinding.RecordItemBinding
import com.example.agrix.dbhandler.DatabaseHandler
import com.example.agrix.model.Product


class RecordsFragment : Fragment() {

    private lateinit var binding:  FragmentRecordsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRecordsBinding.inflate(inflater, container, false)

        val db = DatabaseHandler(requireContext())

        val productsList: ArrayList<Product> = db.retrieveData()
        println(productsList)

        val productNames: ArrayList<String> = ArrayList()
        val productMeasurements:  ArrayList<String> = ArrayList()
        val productQuantities: ArrayList<Double> = ArrayList()
        val productLastEdited: ArrayList<String> = ArrayList()

        for (i in 0 until productsList.size) {
            productNames.add(productsList[i].productName)
            productMeasurements.add(productsList[i].measurement)
            productQuantities.add(productsList[i].quantity)
            productLastEdited.add(productsList[i].lastEdited)
        }


        binding.fragRecordsLv.adapter = MyCustomAdapter(requireContext(), productNames, productMeasurements, productQuantities, productLastEdited)

        if(productNames.size > 0) {
            //Gets displayed by default
            binding.noItems.isInvisible = true
        }

        return binding.root
    }

    private class MyCustomAdapter(context: Context, names: ArrayList<String>, measuringUnits: ArrayList<String>,
                                  currentQuantities: ArrayList<Double>, lastEdited: ArrayList<String>): BaseAdapter() {

        private val mContext: Context = context

        private val  productNames= names
        private val productMeasuringUnits = measuringUnits
        private val productCurrentQuantities = currentQuantities
        private val productLastEdited = lastEdited

        // responsible for how many rows are in the list
        override fun getCount(): Int {
            return productNames.count()
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getItem(position: Int): Any {
            return "TEST STRING"
        }

        // responsible for rendering out each row
        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
            val myLayoutInflater = LayoutInflater.from(mContext)
            val rowItemBinding: RecordItemBinding = RecordItemBinding.inflate(myLayoutInflater, viewGroup, false)

            rowItemBinding.fragRecordTitle.text = productNames[position]
            rowItemBinding.fragRecordMeasuringUnit.text = productMeasuringUnits[position]
            rowItemBinding.fragRecordCurrentQuantity.text = productCurrentQuantities[position].toString()
            rowItemBinding.fragRecordLastEdited.text = productLastEdited[position]

            if(position%2 == 0) {
                // Change bg color for odd items
                rowItemBinding.fragRecordBg.setBackgroundColor(Color.rgb(224, 242, 241))
            }

            return  rowItemBinding.root
        }

    }
}