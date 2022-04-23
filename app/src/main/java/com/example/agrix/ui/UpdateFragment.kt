package com.example.agrix.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.agrix.R
import com.example.agrix.databinding.FragmentUpdateBinding

class UpdateFragment : Fragment() {

    private lateinit var binding: FragmentUpdateBinding

    override fun onResume() {
        super.onResume()

        // Add implementation in onResume to ensure item list is maintained
        val items = resources.getStringArray(R.array.spinner_array)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, items)   //context, item layout, item array
        binding.dropdownMenuUpdate.setAdapter(arrayAdapter)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentUpdateBinding.inflate(inflater, container, false)

        return binding.root

    }
}