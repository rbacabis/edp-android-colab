package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                GroceryListApp()
            }
        }
    }
}

@Composable
fun GroceryListApp() {

    var newItem by remember { mutableStateOf("") }

    val groceries = remember { mutableStateListOf<String>() }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "My Grocery List", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))


        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = newItem,
                onValueChange = { newItem = it },
                label = { Text("Enter an item") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {

                if (newItem.isNotBlank()) {
                    groceries.add(newItem.trim())
                    newItem = ""
                }
            }) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        Text(text = "Total items: ${groceries.size}", fontSize = 16.sp)

        Spacer(modifier = Modifier.height(16.dp))


        if (groceries.isNotEmpty()) {
            Button(onClick = { groceries.clear() }) {
                Text("Clear All")
            }
            Spacer(modifier = Modifier.height(8.dp))
        }


        LazyColumn {
            items(groceries) { item ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = item, fontSize = 18.sp)
                    IconButton(onClick = { groceries.remove(item) }) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GroceryListWithItemsPreview() {
    MyApplicationTheme {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text(text = "My Grocery List", fontSize = 24.sp)
            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = "",
                    onValueChange = { },
                    label = { Text("Enter an item") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { }) {
                    Text("Add")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Total items: 3", fontSize = 16.sp)

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { }) {
                Text("Clear All")
            }
            Spacer(modifier = Modifier.height(8.dp))

            val mockItems = listOf("Eggs", "Bread", "Milk")
            LazyColumn {
                items(mockItems) { item ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = item, fontSize = 18.sp)
                        IconButton(onClick = { }) {
                            Icon(Icons.Filled.Delete, contentDescription = "Delete")
                        }
                    }
                }
            }
        }
    }
}
