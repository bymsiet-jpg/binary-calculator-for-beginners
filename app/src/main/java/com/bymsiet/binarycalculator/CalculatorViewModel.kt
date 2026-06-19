package com.bymsiet.binarycalculator

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.math.BigInteger

class CalculatorViewModel : ViewModel() {
    // Configurable bit size for prototype
    var bits: Int = 8

    var displayBinary by mutableStateOf("${"0".repeat(8)}")
        private set

    var displayDecimal by mutableStateOf("0")
        private set

    private var leftOperand: BigInteger? = null
    private var pendingOp: String? = null

    fun inputBit(bitChar: Char) {
        val trimmed = displayBinary.trimStart('0')
        val current = if (trimmed.isEmpty()) "0" else trimmed
        val next = if (current == "0") bitChar.toString() else current + bitChar
        val parsed = CalculatorEngine.parseBinary(next)
        updateDisplays(parsed)
    }

    fun setBinary(bin: String) {
        val parsed = CalculatorEngine.parseBinary(bin)
        updateDisplays(parsed)
    }

    fun clear() {
        leftOperand = null
        pendingOp = null
        val zero = BigInteger.ZERO
        updateDisplays(zero)
    }

    fun op(opStr: String) {
        val current = CalculatorEngine.parseBinary(displayBinary)
        if (leftOperand == null) {
            leftOperand = current
        } else if (pendingOp != null) {
            val result = computeOperation(leftOperand!!, current, pendingOp!!)
            leftOperand = result.first
            updateDisplays(result.first)
        }
        pendingOp = opStr
    }

    fun equalsOp() {
        val current = CalculatorEngine.parseBinary(displayBinary)
        if (leftOperand != null && pendingOp != null) {
            val result = computeOperation(leftOperand!!, current, pendingOp!!)
            leftOperand = null
            pendingOp = null
            updateDisplays(result.first)
        }
    }

    private fun computeOperation(a: BigInteger, b: BigInteger, op: String): Pair<BigInteger, Boolean> {
        return when (op) {
            "+" -> CalculatorEngine.add(a, b, bits)
            "-" -> CalculatorEngine.subtract(a, b, bits)
            "*" -> CalculatorEngine.multiply(a, b, bits)
            "/" -> CalculatorEngine.divide(a, b, bits)
            "AND" -> CalculatorEngine.and(a, b, bits)
            "OR" -> CalculatorEngine.or(a, b, bits)
            "XOR" -> CalculatorEngine.xor(a, b, bits)
            else -> Pair(a, false)
        }
    }

    private fun updateDisplays(value: BigInteger) {
        displayBinary = CalculatorEngine.toBinary(value, bits)
        displayDecimal = CalculatorEngine.toSigned(value, bits).toString()
    }
}
