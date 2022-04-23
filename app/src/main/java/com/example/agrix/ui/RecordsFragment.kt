package com.example.agrix.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.fragment.app.Fragment
import com.example.agrix.R
import com.example.agrix.databinding.FragmentRecordsBinding
import com.example.agrix.databinding.RecordItemBinding


class RecordsFragment : Fragment() {

    private lateinit var binding:  FragmentRecordsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRecordsBinding.inflate(inflater, container, false)

//        val items = resources.getStringArray(R.array.spinner_array)
//        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.record_item, items)   //context, item layout, item array
//        binding.fragRecordsLv.adapter = arrayAdapter

//        val layoutInflater = LayoutInflater.from(requireContext())
//        val recordRow = layoutInflater.inflate(R.layout.record_item, container, false)
//        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.record_item, items)

        binding.fragRecordsLv.adapter = MyCustomAdapter(requireContext())

        return binding.root
    }

    private class MyCustomAdapter(context: Context): BaseAdapter() {

        private val mContext: Context = context

        private val  titles = mContext.resources.getStringArray(R.array.spinner_array)
        private val measuringUnits = mContext.resources.getStringArray(R.array.measuring_units)
        private val currentQuantities = mContext.resources.getStringArray(R.array.current_quantities)
        private val lastEdited = mContext.resources.getStringArray(R.array.last_edited)

        // responsible for how many rows in my list
        override fun getCount(): Int {
            return titles.count()
        }

        // you can also ignore this
        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        // you can ignore this for now
        override fun getItem(position: Int): Any {
            return "TEST STRING"
        }

        // responsible for rendering out each row
        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
//            val cardView: CardView = CardView(mContext)
            val myLayoutInflater = LayoutInflater.from(mContext)
            val rowItemBinding: RecordItemBinding = RecordItemBinding.inflate(myLayoutInflater, viewGroup, false)

            rowItemBinding.fragRecordTitle.text = titles[position]
            rowItemBinding.fragRecordMeasuringUnit.text = measuringUnits[position]
            rowItemBinding.fragRecordCurrentQuantity.text = currentQuantities[position]
            rowItemBinding.fragRecordLastEdited.text = lastEdited[position]

            return  rowItemBinding.root
        }

    }
}