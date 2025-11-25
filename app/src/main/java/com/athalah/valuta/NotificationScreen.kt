package com.athalah.valuta

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.athalah.valuta.data.NotificationItem
import com.athalah.valuta.data.NotificationType
import com.athalah.valuta.data.sampleNotifications

@Composable
fun NotificationScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = stringResource(R.string.N).uppercase(),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        sampleNotifications.forEach { notif ->
            NotificationCard(notif)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun NotificationCard(item: NotificationItem) {

    val icon = when (item.type) {
        NotificationType.TRANSACTION -> Icons.Rounded.AttachMoney
        NotificationType.PROMO -> Icons.Rounded.LocalOffer
        NotificationType.SYSTEM -> Icons.Rounded.Warning
    }

    val color = when (item.type) {
        NotificationType.TRANSACTION -> Color(0xFF4CAF50)
        NotificationType.PROMO -> Color(0xFFFFC107)
        NotificationType.SYSTEM -> Color(0xFFF44336)
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(45.dp)
                .background(color.copy(alpha = 0.2f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = color
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(item.title, style = MaterialTheme.typography.titleMedium)
            Text(item.message, color = Color.Gray)
        }

        Text(
            text = item.time,
            color = Color.Gray,
            style = MaterialTheme.typography.bodySmall
        )
    }
}
