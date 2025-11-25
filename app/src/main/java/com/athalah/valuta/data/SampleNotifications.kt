package com.athalah.valuta.data

val sampleNotifications = listOf(
    NotificationItem(
        id = "1",
        title = "Top Up Successful",
        message = "You added Rp 50.000 to your wallet.",
        time = "2 minutes ago",
        type = NotificationType.TRANSACTION
    ),
    NotificationItem(
        id = "2",
        title = "Special Cashback!",
        message = "Get 20% cashback for food purchases.",
        time = "1 hour ago",
        type = NotificationType.PROMO
    ),
    NotificationItem(
        id = "3",
        title = "Security Alert",
        message = "New login detected from another device.",
        time = "Yesterday",
        type = NotificationType.SYSTEM
    )
)
