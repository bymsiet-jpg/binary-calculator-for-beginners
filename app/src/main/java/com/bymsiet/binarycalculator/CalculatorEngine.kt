package com.bymsiet.binarycalculator

import java.math.BigInteger

object CalculatorEngine {
    // Default to 8-bit mask for prototype; callers can change bits
    fun mask(bits: Int): BigInteger = if (bits <= 0) BigInteger.ZERO
    else BigInteger.ONE.shiftLeft(bits).subtract(BigInteger.ONE)

    fun parseBinary(bin: String): BigInteger = try {
        BigInteger(bin.filter { it == '0' || it == '1' }, 2)
    } catch (e: Exception) {
        BigInteger.ZERO
    }

    fun toBinary(value: BigInteger, bits: Int): String {
        if (bits <= 0) return value.toString(2)
        val m = mask(bits)
        val wrapped = value.and(m)
        return wrapped.toString(2).padStart(bits, '0')
    }

    fun toSigned(value: BigInteger, bits: Int): BigInteger {
        if (bits <= 0) return value
        val signBit = BigInteger.ONE.shiftLeft(bits - 1)
        return if (value.and(signBit) != BigInteger.ZERO) value.subtract(BigInteger.ONE.shiftLeft(bits)) else value
    }

    fun add(a: BigInteger, b: BigInteger, bits: Int): Pair<BigInteger, Boolean> {
        val res = a.add(b)
        return wrapAndDetect(res, bits)
    }

    fun subtract(a: BigInteger, b: BigInteger, bits: Int): Pair<BigInteger, Boolean> {
        val res = a.subtract(b)
        return wrapAndDetect(res, bits)
    }

    fun multiply(a: BigInteger, b: BigInteger, bits: Int): Pair<BigInteger, Boolean> {
        val res = a.multiply(b)
        return wrapAndDetect(res, bits)
    }

    fun divide(a: BigInteger, b: BigInteger, bits: Int): Pair<BigInteger, Boolean> {
        if (b == BigInteger.ZERO) return Pair(BigInteger.ZERO, false)
        val res = a.divide(b)
        return wrapAndDetect(res, bits)
    }

    fun and(a: BigInteger, b: BigInteger, bits: Int): Pair<BigInteger, Boolean> {
        val res = a.and(b)
        return wrapAndDetect(res, bits)
    }

    fun or(a: BigInteger, b: BigInteger, bits: Int): Pair<BigInteger, Boolean> {
        val res = a.or(b)
        return wrapAndDetect(res, bits)
    }

    fun xor(a: BigInteger, b: BigInteger, bits: Int): Pair<BigInteger, Boolean> {
        val res = a.xor(b)
        return wrapAndDetect(res, bits)
    }

    fun not(a: BigInteger, bits: Int): Pair<BigInteger, Boolean> {
        val m = mask(bits)
        val res = a.xor(m)
        return wrapAndDetect(res, bits)
    }

    fun shiftLeft(a: BigInteger, places: Int, bits: Int): Pair<BigInteger, Boolean> {
        val res = a.shiftLeft(places)
        return wrapAndDetect(res, bits)
    }

    fun shiftRightLogical(a: BigInteger, places: Int, bits: Int): Pair<BigInteger, Boolean> {
        // logical: fill with zeros
        val res = a.shiftRight(places)
        return wrapAndDetect(res, bits)
    }

    private fun wrapAndDetect(res: BigInteger, bits: Int): Pair<BigInteger, Boolean> {
        if (bits <= 0) return Pair(res, false)
        val m = mask(bits)
        val wrapped = res.and(m)
        val overflow = res.and(m).compareTo(res) != 0 || res.bitLength() > bits
        return Pair(wrapped, overflow)
    }
}
