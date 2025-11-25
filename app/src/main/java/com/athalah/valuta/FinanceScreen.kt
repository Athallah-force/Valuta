package com.athalah.valuta

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.athalah.valuta.financeList
import com.athalah.valuta.FinanceItem
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavController
import androidx.compose.runtime.LaunchedEffect
import android.content.res.Configuration
import androidx.compose.ui.res.stringResource

@Composable
fun FinanceScreen(navController: NavController) {

    val config = LocalConfiguration.current
    val orientation = config.orientation

    LaunchedEffect(orientation) {
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            navController.navigate("home") {
                popUpTo("finance") { inclusive = true }
            }
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {

        Text(
            text = stringResource(R.string.BU).uppercase(),
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // 2 kolom
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp),
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp),
        ) {
            itemsIndexed(financeList) { index, _ ->
                FinanceItemLandscap(index ) // kotak responsif
            }
        }
    }
}

@Composable
fun FinanceItemLandscap(index: Int) {
    val finance = financeList[index]
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(85.dp) // ukuran card yang kecil & proporsional
            .clip(RoundedCornerShape(22.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .clickable {}
            .padding(horizontal = 12.dp, vertical = 10.dp),// Lebih panjang karena text di samping icon
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {

        // ICON BOX
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(finance.background)
                .padding(8.dp)
        ) {
            Icon(
                imageVector = finance.icon,
                contentDescription = finance.name,
                tint = Color.White
            )
        }

        // SPACING
        androidx.compose.foundation.layout.Spacer(modifier = Modifier.padding(8.dp))

        // TEXT
        Text(
            text = finance.name.replace("\n", " "), // Biar jadi satu baris rapi
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
    }
}
