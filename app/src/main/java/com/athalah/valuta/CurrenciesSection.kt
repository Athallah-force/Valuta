package com.athalah.valuta

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.athalah.valuta.data.Currency
import com.athalah.valuta.ui.theme.GreenStart

// 🔥 Helper: Mapping mata uang → emoji bendera
fun getFlag(currency: String): String {
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

@Composable
fun CurrenciesSection(
    vm: CurrencyViewModel = viewModel()
) {
    var isVisible by remember { mutableStateOf(false) }
    var iconState by remember { mutableStateOf(Icons.Rounded.KeyboardArrowDown) }

    LaunchedEffect(Unit) {
        vm.loadRates()
    }

    val uiState = vm.state.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp),
        contentAlignment = Alignment.BottomCenter
    ) {

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(MaterialTheme.colorScheme.inverseOnSurface)
                .animateContentSize()
        ) {

            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.secondary)
                        .clickable {
                            isVisible = !isVisible
                            iconState = if (isVisible)
                                Icons.Rounded.KeyboardArrowUp
                            else
                                Icons.Rounded.KeyboardArrowDown
                        }
                ) {
                    Icon(
                        modifier = Modifier.size(25.dp),
                        imageVector = iconState,
                        contentDescription = "Currencies",
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                }

                Spacer(modifier = Modifier.width(20.dp))

                Text(
                    text = stringResource(R.string.BC).uppercase(),
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.secondaryContainer)
            )

            if (isVisible) {
                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                        .background(MaterialTheme.colorScheme.background)
                ) {

                    val width = maxWidth / 3

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                modifier = Modifier.width(width),
                                text = stringResource(R.string.BC).uppercase(),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )
                            Text(
                                modifier = Modifier.width(width),
                                text = stringResource(R.string.b).uppercase(),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                textAlign = TextAlign.End
                            )
                            Text(
                                modifier = Modifier.width(width),
                                text = stringResource(R.string.j).uppercase(),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                textAlign = TextAlign.End
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        when (uiState) {

                            is CurrencyUiState.Loading -> {
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }

                            is CurrencyUiState.Error -> {
                                Text(
                                    text = "Error: ${uiState.message}",
                                    color = Color.Red,
                                    modifier = Modifier.padding(12.dp)
                                )
                            }

                            is CurrencyUiState.Success -> {
                                LazyColumn {
                                    items(uiState.data.size) { index ->
                                        DynamicCurrencyItem(
                                            currency = uiState.data[index],
                                            width = width
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DynamicCurrencyItem(currency: Currency, width: Dp) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            modifier = Modifier.width(width),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = currency.flag,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = currency.name,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }

        Text(
            modifier = Modifier.width(width),
            text = String.format("%.3f", currency.buy),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            textAlign = TextAlign.End
        )

        Text(
            modifier = Modifier.width(width),
            text = String.format("%.3f", currency.sell),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            textAlign = TextAlign.End
        )
    }
}
