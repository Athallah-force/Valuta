package com.athalah.valuta.data

val sampleTransactions = listOf(
    Transaction(
        id = "1",
        title = "Gaji Bulanan",
        amount = 5000000.0,
        type = TransactionType.INCOME
    ),
    Transaction(
        id = "2",
        title = "Makan Siang",
        amount = 35000.0,
        type = TransactionType.EXPENSE
    ),
    Transaction(
        id = "3",
        title = "Beli Pulsa",
        amount = 50000.0,
        type = TransactionType.EXPENSE
    ),
)
