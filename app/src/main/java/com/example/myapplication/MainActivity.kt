package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                
                NavHost(navController = navController, startDestination = Home) {
                    composable<Home> {
                        HomeScreen(onShowGreeting = { typedName ->
                            navController.navigate(Greeting(userName = typedName))
                        })
                    }
                    composable<Greeting> { backStackEntry ->
                        val greeting: Greeting = backStackEntry.toRoute()
                        GreetingScreen(userName = greeting.userName)
                    }
                }
            }
        }
    }
}
