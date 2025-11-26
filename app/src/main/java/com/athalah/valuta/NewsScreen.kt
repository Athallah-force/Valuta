package com.athalah.valuta

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import androidx.navigation.NavController
import com.athalah.valuta.data.news.NewsArticle
import com.athalah.valuta.data.news.NewsUiState
import java.net.URLEncoder
import java.net.URLDecoder
import androidx.compose.ui.res.stringResource


@Composable
fun NewsScreen(navController: NavController, viewModel: NewsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

    val state = viewModel.state.collectAsState()
    var searchQuery by remember { mutableStateOf("") }


    when (val s = state.value) {
        is NewsUiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is NewsUiState.Error -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: ${s.message}")
            }
        }

        is NewsUiState.Success -> {

            val filteredNews = s.data.filter { article ->
                article.title.contains(searchQuery, ignoreCase = true) ||
                        (article.description?.contains(searchQuery, ignoreCase = true) ?: false)
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                item {
                    Text(
                        text = stringResource(R.string.berita),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                // 🔍 Search Bar
                item {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        label = { Text("Search news...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        singleLine = true
                    )
                }

                // 🔎 Filtered News
                items(filteredNews) { article ->
                    NewsItem(article, navController)
                }
            }
        }
    }
}



@Composable
fun NewsItem(article: NewsArticle, navController: NavController) {

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable {
                val encodedUrl = URLEncoder.encode(article.url, "UTF-8")
                navController.navigate("newsDetail/$encodedUrl")
            },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {

        // ---------- Portrait ----------
        if (!isLandscape) {
            Column(modifier = Modifier.padding(12.dp)) {

                if (article.image != null) {
                    Image(
                        painter = rememberAsyncImagePainter(article.image),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .padding(bottom = 8.dp)
                    )
                }

                Text(
                    text = article.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = article.description ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    text = article.source,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }

        // ---------- Landscape ----------
        else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                // Gambar lebih kecil di landscape
                if (article.image != null) {
                    Image(
                        painter = rememberAsyncImagePainter(article.image),
                        contentDescription = null,
                        modifier = Modifier
                            .width(180.dp)
                            .height(120.dp)
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = article.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    )

                    Spacer(Modifier.height(4.dp))

                    Text(
                        text = article.description ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 4,
                    )

                    Spacer(Modifier.height(6.dp))

                    Text(
                        text = article.source,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}


@Composable
fun NewsDetailScreen(url: String) {

    val context = LocalContext.current
    val decodedUrl = URLDecoder.decode(url, "UTF-8")

    Column(modifier = Modifier.padding(16.dp)) {

        Text("Full Article", style = MaterialTheme.typography.titleLarge)

        Spacer(Modifier.height(12.dp))

        Button(onClick = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(decodedUrl))
            context.startActivity(intent)
        }) {
            Text("Open Original Source")
        }
    }
}
