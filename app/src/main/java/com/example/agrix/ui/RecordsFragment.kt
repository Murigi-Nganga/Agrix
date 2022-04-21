package com.example.agrix.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.agrix.databinding.FragmentRecordsBinding


class RecordsFragment : Fragment() {

    private lateinit var binding:  FragmentRecordsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRecordsBinding.inflate(inflater, container, false)

        return binding.root
    }
}