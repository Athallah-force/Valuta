package com.athalah.valuta

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.athalah.valuta.data.BottomNavigation
import androidx.compose.ui.platform.LocalConfiguration
import android.content.res.Configuration

object BottomNavItems{

    // Menu PORTRAIT
    val portraitItems = listOf(
        BottomNavigation("Home", Icons.Rounded.Home, "home"),
        BottomNavigation("Wallet", Icons.Rounded.Wallet, "wallet"),
        BottomNavigation("Notifications", Icons.Rounded.Notifications, "notifications"),
        BottomNavigation("Account", Icons.Rounded.AccountCircle, "account")
    )

    // Menu LANDSCAPE (tambahkan Finance & Currencies)
    val landscapeItems = listOf(
        BottomNavigation("Home", Icons.Rounded.Home, "home"),
        BottomNavigation("Finance", Icons.Rounded.PieChart, "finance"),
        BottomNavigation("Currencies", Icons.Rounded.AttachMoney, "currencies"),
        BottomNavigation("Wallet", Icons.Rounded.Wallet, "wallet"),
        BottomNavigation("Notifications", Icons.Rounded.Notifications, "notifications"),
        BottomNavigation("Account", Icons.Rounded.AccountCircle, "account")
    )
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val items = if (isLandscape) {
        BottomNavItems.landscapeItems
    } else {
        BottomNavItems.portraitItems
    }

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = false,
                onClick = { navController.navigate(item.route) },
                icon = { Icon(item.icon, item.title) },
                label = { Text(item.title) }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    val previewNav = rememberNavController()
    BottomNavigationBar(navController = previewNav)
}
