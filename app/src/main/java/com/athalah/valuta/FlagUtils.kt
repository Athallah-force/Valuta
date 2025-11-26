package com.athalah.valuta.data


fun getFlagForCurrency(currency: String): String {
    return when (currency) {
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
        else -> "🌍"
    }
}
