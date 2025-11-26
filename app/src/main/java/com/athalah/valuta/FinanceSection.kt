package com.athalah.valuta

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MonetizationOn
import androidx.compose.material.icons.rounded.StarHalf
import androidx.compose.material.icons.rounded.Wallet
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.athalah.valuta.data.Finance
import com.athalah.valuta.ui.theme.BlueStart
import com.athalah.valuta.ui.theme.GreenStart
import com.athalah.valuta.ui.theme.OrangeStart
import com.athalah.valuta.ui.theme.PurpleStart
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource

// LIST FINANCE SUPPORT MULTI LANGUAGE
val financeList = listOf(
    Finance(
        icon = Icons.Rounded.StarHalf,
        name = R.string.finance_my_business,
        background = OrangeStart
    ),
    Finance(
        icon = Icons.Rounded.Wallet,
        name = R.string.finance_my_wallet,
        background = BlueStart
    ),
    Finance(
        icon = Icons.Rounded.StarHalf,
        name = R.string.finance_analytics,
        background = PurpleStart
    ),
    Finance(
        icon = Icons.Rounded.MonetizationOn,
        name = R.string.finance_transactions,
        background = GreenStart
    ),
)

@Preview
@Composable
fun FinanceSection() {
    Column {
        Text(
            text = stringResource(R.string.BU).uppercase(),
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        LazyRow {
            items(financeList.size) {
                FinanceItem(it)
            }
        }
    }
}

@Composable
fun FinanceItem(index: Int) {

    val configuration = LocalConfiguration.current
    val isLandscape =
        configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE

    val boxSize = if (isLandscape) 90.dp else 120.dp

    val finance = financeList[index]
    val lastPadding = if (index == financeList.size - 1) 16.dp else 0.dp

    Box(modifier = Modifier.padding(start = 16.dp, end = lastPadding)) {

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(25.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .size(boxSize)
                .clickable {}
                .padding(13.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(finance.background)
                    .padding(6.dp)
            ) {
                Icon(
                    imageVector = finance.icon,
                    contentDescription = stringResource(finance.name),
                    tint = Color.White
                )
            }

            Text(
                text = stringResource(finance.name).replace("\n", " "),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp
            )
        }
    }
}
