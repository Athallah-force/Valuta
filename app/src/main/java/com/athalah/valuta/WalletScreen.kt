package com.athalah.valuta

import android.content.res.Configuration
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.athalah.valuta.data.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.Alignment
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController

@Composable
fun WalletScreen(navController: NavController) {

    val configuration = LocalConfiguration.current
    val orientation = configuration.orientation

    if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
        WalletLandscape(navController)
    } else {
        WalletPortrait(navController)
    }
}

@Composable
fun WalletPortrait(navController: NavController) {

    val transactions = sampleTransactions

    val totalIncome = transactions.filter { it.type == TransactionType.INCOME }.sumOf { it.amount }
    val totalExpense = transactions.filter { it.type == TransactionType.EXPENSE }.sumOf { it.amount }
    val balance = totalIncome - totalExpense

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("add_transaction") }
            ) {
                Icon(Icons.Rounded.Add, contentDescription = "Add")
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                shape = RoundedCornerShape(18.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(stringResource(R.string.TB).uppercase(), color = MaterialTheme.colorScheme.onPrimaryContainer)
                    Text(
                        "Rp ${balance.toInt()}",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                SummaryCard(
                    title = stringResource(R.string.I).uppercase(),
                    amount = totalIncome,
                    color = Color(0xFF4CAF50),
                    modifier = Modifier.weight(1f)
                )

                SummaryCard(
                    title = stringResource(R.string.Expense).uppercase(),
                    amount = totalExpense,
                    color = Color(0xFFF44336),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(20.dp))

            Text(
                text = stringResource(R.string.kartu8).uppercase(),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(10.dp))

            transactions.forEach { trx ->
                TransactionItem(transaction = trx)
            }
        }
    }
}

@Composable
fun WalletLandscape(navController: NavController) {

    val transactions = sampleTransactions

    val totalIncome = transactions.filter { it.type == TransactionType.INCOME }.sumOf { it.amount }
    val totalExpense = transactions.filter { it.type == TransactionType.EXPENSE }.sumOf { it.amount }
    val balance = totalIncome - totalExpense

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("add_transaction") }
            ) {
                Icon(Icons.Rounded.Add, contentDescription = "Add")
            }
        }
    ) { padding ->

        Row(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // ====================================
            //  KIRI (Balance + Income + Expense)
            // ====================================
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // Balance Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text("Total Balance", color = MaterialTheme.colorScheme.onPrimaryContainer)
                        Text(
                            "Rp ${balance.toInt()}",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }

                // Income & Expense tetap muncul
                SummaryCard(
                    title = "Income",
                    amount = totalIncome,
                    color = Color(0xFF4CAF50),
                    modifier = Modifier.fillMaxWidth()
                )

                SummaryCard(
                    title = "Expense",
                    amount = totalExpense,
                    color = Color(0xFFF44336),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // ====================================
            //  KANAN — Transactions List
            // ====================================
            Column(
                modifier = Modifier
                    .weight(1.3f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                Text(
                    text = "Transactions",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                transactions.forEach { trx ->
                    TransactionItem(transaction = trx)
                }
            }
        }
    }
}



// ===========================
// SUMMARY CARD (WORKING VERSION)
// ===========================
@Composable
fun SummaryCard(
    title: String,
    amount: Double,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontWeight = FontWeight.Bold)
            Text(
                "Rp ${amount.toInt()}",
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )
        }
    }
}


// ===========================
// TRANSACTION ITEM
// ===========================
@Composable
fun TransactionItem(transaction: Transaction) {

    val color = if (transaction.type == TransactionType.INCOME)
        Color(0xFF4CAF50) else Color(0xFFF44336)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {

        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (transaction.type == TransactionType.INCOME)
                    Icons.Rounded.ArrowDownward
                else
                    Icons.Rounded.ArrowUpward,
                contentDescription = null,
                tint = color
            )
        }

        Spacer(Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(transaction.title, fontWeight = FontWeight.SemiBold)
            Text("ID: ${transaction.id}", fontSize = 12.sp, color = Color.Gray)
        }

        Text(
            text = if (transaction.type == TransactionType.INCOME)
                "+${transaction.amount.toInt()}"
            else
                "-${transaction.amount.toInt()}",
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}
