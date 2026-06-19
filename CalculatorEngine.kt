package com.example.binarycalc.domain

import java.math.BigInteger

enum class WordSize { BITS_8, BITS_16, BITS_32, BITS_64, CUSTOM }
data class CalcConfig(val bits: Int = 32, val signed: Boolean = false)

object CalculatorEngine {
  private fun mask(bits: Int): BigInteger =
    if (bits >= 128) BigInteger.ONE.shiftLeft(bits).subtract(BigInteger.ONE)
    else BigInteger.valueOf(1).shiftLeft(bits).subtract(BigInteger.ONE)

  fun fromBinary(bin: String): BigInteger = BigInteger(bin, 2)
  fun toBinary(value: BigInteger, bits: Int): String {
    val m = mask(bits)
    val wrapped = value.and(m)
    val s = wrapped.toString(2)
    return s.padStart(bits, '0')
  }

  fun toSigned(value: BigInteger, bits: Int): BigInteger {
    if (bits <= 0) return value
    val signBit = BigInteger.ONE.shiftLeft(bits - 1)
    return if (value.and(signBit) != BigInteger.ZERO) value.subtract(BigInteger.ONE.shiftLeft(bits)) else value
  }

  fun add(a: BigInteger, b: BigInteger, bits: Int): Pair<BigInteger, Boolean> {
    val res = a.add(b)
    val m = mask(bits)
    val wrapped = res.and(m)
    val overflow = res != wrapped && bits < res.bitLength()
    return Pair(wrapped, overflow)
  }

  // Implement sub/mul/div/bitwise with similar pattern...
}