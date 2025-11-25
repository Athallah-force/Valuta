package com.athalah.valuta

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.athalah.valuta.CurrencyUiState
import androidx.compose.foundation.background
import com.athalah.valuta.data.Currency
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.platform.LocalConfiguration
import android.content.res.Configuration
import androidx.compose.ui.res.stringResource


@Composable
fun CurrenciesScreen(
    navController: NavController,
    vm: CurrencyViewModel = viewModel()
) {

    val config = LocalConfiguration.current
    val orientation = config.orientation

    LaunchedEffect(orientation) {
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            navController.navigate("home") {
                popUpTo("currencies") { inclusive = true }
            }
        }
    }


    LaunchedEffect(Unit) {
        vm.loadRates()
    }

    val uiState = vm.state.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = stringResource(R.string.BC).uppercase(),
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Header tabel
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            val weight1 = .40f
            val weight2 = .30f
            val weight3 = .30f

            Text(
                text = stringResource(R.string.BC).uppercase(),
                modifier = Modifier.weight(weight1),
                fontWeight = FontWeight.SemiBold,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize
            )

            Text(
                text = stringResource(R.string.b).uppercase(),
                modifier = Modifier.weight(weight2),
                textAlign = TextAlign.End,
                fontWeight = FontWeight.SemiBold,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize
            )

            Text(
                text = stringResource(R.string.j).uppercase(),
                modifier = Modifier.weight(weight3),
                textAlign = TextAlign.End,
                fontWeight = FontWeight.SemiBold,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize
            )
        }

        // Isi tabel
        when (uiState) {

            is CurrencyUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is CurrencyUiState.Error -> {
                Text(
                    text = "Error: ${uiState.message}",
                    color = MaterialTheme.colorScheme.error
                )
            }

            is CurrencyUiState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.data.size) { index ->

                        CurrenciesLandscapeItem(
                            data = uiState.data[index]
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CurrenciesLandscapeItem(data: Currency) {

    val weight1 = .40f
    val weight2 = .30f
    val weight3 = .30f

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Flag + currency name
        Row(
            modifier = Modifier.weight(weight1),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = data.flag,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = data.name,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Buy
        Text(
            text = String.format("%.3f", data.buy),
            modifier = Modifier.weight(weight2),
            textAlign = TextAlign.End,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        // Sell
        Text(
            text = String.format("%.3f", data.sell),
            modifier = Modifier.weight(weight3),
            textAlign = TextAlign.End,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
