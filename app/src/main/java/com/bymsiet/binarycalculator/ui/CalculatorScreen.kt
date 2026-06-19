package com.bymsiet.binarycalculator.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bymsiet.binarycalculator.CalculatorViewModel

@Composable
fun CalculatorScreen(viewModel: CalculatorViewModel) {
    Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Displays
            Text(text = "Binary:", fontSize = 14.sp)
            Text(text = viewModel.displayBinary, fontFamily = FontFamily.Monospace, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Decimal: ${viewModel.displayDecimal}", fontSize = 16.sp)

            Spacer(modifier = Modifier.height(16.dp))

            // Bit input row (simple: show current bits as tappable)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                val bin = viewModel.displayBinary
                for (c in bin) {
                    Box(modifier = Modifier
                        .padding(2.dp)
                        .size(28.dp)
                        .background(Color(0xFFE0E0E0))
                        .clickable { /* toggle bit not implemented in prototype */ },
                        contentAlignment = Alignment.Center) {
                        Text(text = c.toString(), fontFamily = FontFamily.Monospace)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Keypad
            Column {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { viewModel.inputBit('0') }) { Text("0") }
                    Button(onClick = { viewModel.inputBit('1') }) { Text("1") }
                    Button(onClick = { viewModel.clear() }) { Text("C") }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { viewModel.op("+") }) { Text("+") }
                    Button(onClick = { viewModel.op("-") }) { Text("-") }
                    Button(onClick = { viewModel.op("*") }) { Text("*") }
                    Button(onClick = { viewModel.op("/") }) { Text("/") }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { viewModel.op("AND") }) { Text("AND") }
                    Button(onClick = { viewModel.op("OR") }) { Text("OR") }
                    Button(onClick = { viewModel.op("XOR") }) { Text("XOR") }
                    Button(onClick = { viewModel.op("NOT") }) { Text("NOT") }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { viewModel.equalsOp() }) { Text("=") }
                }
            }
        }
    }
}
