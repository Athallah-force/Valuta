package com.athalah.valuta

import android.app.Activity
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.athalah.valuta.language.LanguageDataStore
import com.athalah.valuta.language.LocaleHelper

@Composable
fun AccountScreen() {

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        AccountLandscape()
    } else {
        AccountPortrait()
    }
}

@Composable
fun AccountPortrait() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(start = 15.dp, end = 15.dp, top = 15.dp)
            .navigationBarsPadding()
    ) {

        ProfileHeader()
        Spacer(modifier = Modifier.height(24.dp))

        SummaryCard()
        Spacer(modifier = Modifier.height(30.dp))

        AccountMenuList()
    }
}

@Composable
fun AccountLandscape() {

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(start = 15.dp, end = 15.dp, top = 0.dp)
            .navigationBarsPadding()
    ) {

        Column(
            modifier = Modifier.weight(1f)
        ) {
            ProfileHeader()
            Spacer(modifier = Modifier.height(20.dp))
            SummaryCard()
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            AccountMenuList()
        }
    }
}

@Composable
fun ProfileHeader() {

    Row(verticalAlignment = Alignment.CenterVertically) {

        Box(
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.AccountCircle,
                contentDescription = stringResource(id = R.string.profil),
                modifier = Modifier.size(60.dp),
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = "John Doe",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "john@example.com",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )
            Text(
                text = stringResource(id = R.string.AV),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun SummaryCard() {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                text = stringResource(id = R.string.AS),
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )

            Spacer(modifier = Modifier.height(16.dp))

            SummaryItem(stringResource(id = R.string.TB), "$ 447.87")
            SummaryItem(stringResource(id = R.string.I), "$ 1200.00")
            SummaryItem(stringResource(id = R.string.Expense), "$ 753.00")
        }
    }
}

@Composable
fun AccountMenuList() {

    var showLanguageSheet by remember { mutableStateOf(false) }

    AccountMenuItem(stringResource(id = R.string.EP), Icons.Rounded.Edit) {}
    AccountMenuItem(stringResource(id = R.string.SS), Icons.Rounded.Lock) {}
    AccountMenuItem(stringResource(id = R.string.NS), Icons.Rounded.Notifications) {}
    AccountMenuItem(
        title = stringResource(id = R.string.language),
        icon = Icons.Rounded.Language
    ) {
        showLanguageSheet = true
    }
    AccountMenuItem(stringResource(id = R.string.AP), Icons.Rounded.Info) {}

    AccountMenuItem(
        title = stringResource(id = R.string.LG),
        icon = Icons.Rounded.Logout,
        isLogout = true
    ) {}

    // BottomSheet pemilihan bahasa
    if (showLanguageSheet) {
        LanguageBottomSheet(onDismiss = { showLanguageSheet = false })
    }
}

@Composable
fun SummaryItem(title: String, value: String) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )

        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun AccountMenuItem(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isLogout: Boolean = false,
    onClick: () -> Unit
) {

    val textColor = if (isLogout) Color.Red else MaterialTheme.colorScheme.onBackground

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(vertical = 14.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(imageVector = icon, contentDescription = title, tint = textColor)

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            fontSize = 17.sp,
            fontWeight = if (isLogout) FontWeight.Bold else FontWeight.Normal,
            color = textColor
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageBottomSheet(onDismiss: () -> Unit) {

    val context = LocalContext.current
    val activity = context as Activity

    ModalBottomSheet(onDismissRequest = onDismiss) {

        Column(modifier = Modifier.padding(20.dp)) {

            Text(
                text = stringResource(id = R.string.select_language),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(id = R.string.indonesian),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        LanguageDataStore.saveLanguage(context, "id")
                        LocaleHelper.updateLocale(context, "id")
                        activity.recreate()

                        onDismiss()
                    }
                    .padding(12.dp)
            )

            Text(
                text = stringResource(id = R.string.english),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        LanguageDataStore.saveLanguage(context, "en")
                        LocaleHelper.updateLocale(context, "en")
                        activity.recreate()

                        onDismiss()
                    }
                    .padding(12.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}
