package com.athalah.valuta.data

fun getFlagForCurrency(code: String): String {
    return when (code.uppercase()) {
        "USD" -> "🇺🇸"
        "EUR" -> "🇪🇺"
        "JPY" -> "🇯🇵"
        "GBP" -> "🇬🇧"
        "AUD" -> "🇦🇺"
        "CAD" -> "🇨🇦"
        "CHF" -> "🇨🇭"
        "CNY" -> "🇨🇳"
        "SEK" -> "🇸🇪"
        "NZD" -> "🇳🇿"
        "IDR" -> "🇮🇩"
        else -> "🏳️"
    }
}
