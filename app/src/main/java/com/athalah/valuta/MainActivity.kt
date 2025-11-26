package com.athalah.valuta

import android.os.Bundle
import android.content.res.Configuration
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.athalah.valuta.ui.theme.BankningAppUITheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import androidx.compose.runtime.LaunchedEffect
import com.athalah.valuta.language.LocaleHelper
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.athalah.valuta.language.LanguageDataStore
import androidx.compose.runtime.collectAsState
import com.athalah.valuta.language.LanguageSettingsScreen
import android.content.Context
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.flow.first
import androidx.navigation.NavController

class MainActivity : ComponentActivity() {
    override fun attachBaseContext(newBase: Context) {
        val lang = runBlocking {
            LanguageDataStore.getLanguage(newBase).first()
        }
        val context = LocaleHelper.wrap(newBase, lang)
        super.attachBaseContext(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val lang = LanguageDataStore.getLanguage(this).collectAsState(initial = "en")

            LaunchedEffect(lang.value) {
                LocaleHelper.updateLocale(this@MainActivity, lang.value)
            }


            CompositionLocalProvider(
                LocalLayoutDirection provides LayoutDirection.Ltr
            ) {
                BankningAppUITheme {

                    SetBarColor(color = MaterialTheme.colorScheme.background)

                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        HomeScreen()
                    }
                }
            }

        }


    }

    @Composable
    private fun SetBarColor(color: Color) {
        val systemUiController = rememberSystemUiController()
        SideEffect {
            systemUiController.setSystemBarsColor(color)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {

    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(padding)
        ) {

            composable("home") {
                val config = LocalConfiguration.current
                val isLandscape = config.orientation == Configuration.ORIENTATION_LANDSCAPE

                if (isLandscape) {
                    LandscapeHomeContent(navController)
                } else {
                    PortraitHomeContent(navController)
                }
            }

            // Screens lain
            composable("account") { AccountScreen() }
            composable("notifications") { NotificationScreen() }
            composable("wallet") { WalletScreen(navController) }

            // Tambahan screen khusus landscape
            composable("finance") { FinanceScreen(navController) }
            composable("currencies") { CurrenciesScreen(navController) }

            composable("settings_language") {
                LanguageSettingsScreen()
            }

            composable("news") { NewsScreen(navController) }

            composable("newsDetail/{url}") { backStackEntry ->
                val url = backStackEntry.arguments?.getString("url") ?: ""
                NewsDetailScreen(url)
            }



        }
    }
}


/* -------------------- HOME CONTENT -------------------- */

@Composable
fun PortraitHomeContent(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        WalletSection(navController)
        CardsSection()
        Spacer(modifier = Modifier.height(16.dp))
        FinanceSection()
        CurrenciesSection()
    }
}


@Composable
fun LandscapeHomeContent(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        WalletSection(navController)
        CardsSection()
        Spacer(modifier = Modifier.height(8.dp))

        // Finance & Currencies dipindah ke bottom navigation (screen terpisah)
        Text(
            text = "Finance & Currencies moved to menu in landscape mode",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}



