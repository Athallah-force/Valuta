package com.athalah.valuta.language

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.athalah.valuta.language.LanguageDataStore
import com.athalah.valuta.language.LocaleHelper
import com.athalah.valuta.R
import android.app.Activity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun LanguageSettingsScreen(
    context: Context = LocalContext.current
) {
    Column(Modifier.padding(20.dp)) {

        Text(
            text = stringResource(R.string.select_language),
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(Modifier.height(20.dp))

        LanguageOption("en", stringResource(R.string.english), context)
        LanguageOption("in", stringResource(R.string.indonesian), context)
    }
}

@Composable
fun LanguageOption(
    code: String,
    label: String,
    context: Context
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                val activity = context as Activity

                CoroutineScope(Dispatchers.Main).launch {
                    LanguageDataStore.saveLanguage(context, code)
                    LocaleHelper.updateLocale(context, code)
                    activity.recreate()
                }

            }

            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, fontSize = 18.sp)
    }
}
