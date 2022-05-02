package com.example.agrix.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.agrix.databinding.FragmentCalculatorBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.math.roundToLong

class CalculatorFragment : Fragment() {

    // Fragment Binding object
    private lateinit var binding: FragmentCalculatorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCalculatorBinding.inflate(inflater, container, false)

        // Input view bindings
        val firstInput = binding.fragCalculatorEtFirstNum
        val secondInput = binding.fragCalculatorEtSecondNum
        var result: Double

        // Calculate
        binding.fragCalculatorBtnResult.setOnClickListener {

            if(firstInput.text.isNullOrEmpty()|| secondInput.text.isNullOrEmpty()) {

                Snackbar.make(binding.root, "Please fill in all fields", Snackbar.LENGTH_LONG).show()

            } else {

                val firstNum: Double = firstInput.text.toString().toDouble()
                val secondNum: Double = secondInput.text.toString().toDouble()

                // Get result according to the radio button selected
                result = when {
                    binding.fragCalculatorRadioPlus.isChecked -> {
                        firstNum + secondNum
                    }
                    binding.fragCalculatorRadioSubtract.isChecked -> {
                        firstNum - secondNum
                    }
                    binding.fragCalculatorRadioMultiply.isChecked -> {
                        firstNum * secondNum
                    }
                    else -> {
                        firstNum / secondNum
                    }
                }

                //  Display result
                binding.fragCalculatorTvResultNumber.text = String.format("%.5f", result)

                // Empty inputs
                firstInput.setText("")
                secondInput.setText("")
            }

        }

        return binding.root
    }
}