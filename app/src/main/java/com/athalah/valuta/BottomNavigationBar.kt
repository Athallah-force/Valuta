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
import androidx.compose.ui.res.stringResource

object BottomNavItems {

    // MENU PORTRAIT
    val portraitItems = listOf(
        BottomNavigation(R.string.nav_home, Icons.Rounded.Home, "home"),
        BottomNavigation(R.string.nav_wallet, Icons.Rounded.Wallet, "wallet"),
        BottomNavigation(R.string.nav_notifications, Icons.Rounded.Notifications, "notifications"),
        BottomNavigation(R.string.nav_account, Icons.Rounded.AccountCircle, "account")
    )

    // MENU LANDSCAPE
    val landscapeItems = listOf(
        BottomNavigation(R.string.nav_home, Icons.Rounded.Home, "home"),
        BottomNavigation(R.string.nav_finance, Icons.Rounded.PieChart, "finance"),
        BottomNavigation(R.string.nav_currencies, Icons.Rounded.AttachMoney, "currencies"),
        BottomNavigation(R.string.nav_wallet, Icons.Rounded.Wallet, "wallet"),
        BottomNavigation(R.string.nav_notifications, Icons.Rounded.Notifications, "notifications"),
        BottomNavigation(R.string.nav_account, Icons.Rounded.AccountCircle, "account")
    )
}

@Composable
fun BottomNavigationBar(navController: NavController) {

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val items = if (isLandscape) BottomNavItems.landscapeItems else BottomNavItems.portraitItems

    NavigationBar {
        items.forEach { item ->

            val title = stringResource(id = item.title)

            NavigationBarItem(
                selected = false,
                onClick = { navController.navigate(item.route) },
                icon = { Icon(item.icon, contentDescription = title) },
                label = { Text(title) }
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
