package com.athalah.valuta.data

data class Transaction(
    val id: String = "",
    val title: String = "",
    val amount: Double = 0.0,
    val type: TransactionType = TransactionType.EXPENSE,
    val date: Long = System.currentTimeMillis()
)

enum class TransactionType {
    INCOME,
    EXPENSE
}
