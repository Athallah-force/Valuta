package com.athalah.valuta.data

data class NotificationItem(
    val id: String,
    val title: String,
    val message: String,
    val time: String,
    val type: NotificationType
)

enum class NotificationType {
    TRANSACTION,
    PROMO,
    SYSTEM
}
