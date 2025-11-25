package com.athalah.valuta

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.athalah.valuta.data.Transaction
import com.athalah.valuta.data.TransactionType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    onSave: (Transaction) -> Unit,
    onCancel: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var type by remember { mutableStateOf(TransactionType.EXPENSE) }
    var note by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "Add Transaction",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        // ---------------------
        // TITLE INPUT
        // ---------------------
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(14.dp))

        // ---------------------
        // AMOUNT INPUT
        // ---------------------
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount (Rp)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(14.dp))

        // ---------------------
        // TYPE SELECTOR
        // ---------------------
        Text("Type", fontWeight = FontWeight.SemiBold)

        Row(modifier = Modifier.fillMaxWidth()) {

            FilterChip(
                selected = type == TransactionType.INCOME,
                onClick = { type = TransactionType.INCOME },
                label = { Text("Income") },
                modifier = Modifier.padding(end = 8.dp)
            )

            FilterChip(
                selected = type == TransactionType.EXPENSE,
                onClick = { type = TransactionType.EXPENSE },
                label = { Text("Expense") }
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // ---------------------
        // NOTE INPUT (OPTIONAL)
        // ---------------------
        OutlinedTextField(
            value = note,
            onValueChange = { note = it },
            label = { Text("Note (optional)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(25.dp))

        // ---------------------
        // SAVE BUTTON
        // ---------------------
        Button(
            onClick = {
                if (title.isNotEmpty() && amount.isNotEmpty()) {
                    val newTransaction = Transaction(
                        id = System.currentTimeMillis().toString(),
                        title = title,
                        amount = amount.toDouble(),
                        type = type,
                    )

                    onSave(newTransaction)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.Rounded.Check, contentDescription = null)
            Spacer(modifier = Modifier.width(6.dp))
            Text("Save")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ---------------------
        // CANCEL BUTTON
        // ---------------------
        TextButton(
            onClick = onCancel,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Rounded.Close, contentDescription = null)
            Spacer(modifier = Modifier.width(6.dp))
            Text("Cancel")
        }
    }
}
