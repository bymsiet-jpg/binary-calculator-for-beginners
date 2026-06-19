package com.bymsiet.binarycalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.bymsiet.binarycalculator.ui.CalculatorScreen

class MainActivity : ComponentActivity() {
    private val vm: CalculatorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorScreen(viewModel = vm)
        }
    }
}
