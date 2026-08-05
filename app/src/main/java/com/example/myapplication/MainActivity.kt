package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ReactiveScreen()
                }
            }
        }
    }
}

@Composable
fun ReactiveScreen() {
    // Part A: 'count' is STATE: remember keeps it across recompositions
    var count by remember { mutableIntStateOf(0) }
    
    // Part B & C: 'name' state that survives rotation using rememberSaveable
    var name by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Part B: Greeting that reacts as you type
        Text(
            text = if (name.isBlank()) "Hello, stranger!" else "Hello, $name!",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = name,
            onValueChange = { name = it }, // WRITE updates the state
            label = { Text("Enter your name") }
        )
        Spacer(Modifier.height(32.dp))

        // Part D: Hoisted Counter
        CounterControls(
            count = count,
            onIncrement = { count++ },
            onDecrement = { count-- },
            onReset = { count = 0 }
        )
    }
}

// Part D: BONUS: Stateless counter component (State Hoisting)
@Composable
fun CounterControls(
    count: Int,               // value flows DOWN
    onIncrement: () -> Unit,  // events flow UP
    onDecrement: () -> Unit,
    onReset: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Count: $count", fontSize = 24.sp)
        Spacer(Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = onDecrement) { Text("-") }
            Button(onClick = onReset) { Text("Reset") }
            Button(onClick = onIncrement) { Text("+") }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReactiveScreenPreview() {
    MyApplicationTheme {
        ReactiveScreen()
    }
}
