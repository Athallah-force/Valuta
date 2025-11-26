package com.athalah.valuta.data


object BuySellCalculator {

    private const val MARGIN = 0.02 // 2%

    fun calculate(mid: Double): Pair<Double, Double> {
        val buy = mid * (1 - MARGIN)
        val sell = mid * (1 + MARGIN)

        return Pair(buy.toDouble(), sell.toDouble())
    }
}
