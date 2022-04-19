package com.example.agrix

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.agrix.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Binding object
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Using View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}