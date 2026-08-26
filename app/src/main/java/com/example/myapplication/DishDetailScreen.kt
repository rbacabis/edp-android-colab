package com.example.myapplication

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.activity.compose.BackHandler
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun DishDetailScreen(
    dishId: Int,
    viewModel: DishViewModel,
    onBack: () -> Unit
) {
    // This handles the physical/system back button on the device
    BackHandler {
        onBack()
    }

    // GIVEN: find the dish this screen is about.
    val dishes by viewModel.dishes.collectAsStateWithLifecycle()
    val dish = dishes.find { it.id == dishId }

    if (dish == null) {
        Text("Dish not found")
        return
    }

    // GIVEN: local UI state.
    var newStep by remember { mutableStateOf("") }
    var stepBeingEdited by remember { mutableStateOf<Recipe?>(null) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        TextButton(onClick = onBack) { Text("< Back") }
        Text(dish.name, style = MaterialTheme.typography.headlineMedium)
        Text("Recipe steps", style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(12.dp))

        // TODO 9 (6 pts) -- CREATE
        // Build a Row containing:
        // * an OutlinedTextField bound to newStep (value = ..., onValueChange = ...)
        //   with label { Text("New step") } and Modifier.weight(1f)
        // * a Button whose onClick calls viewModel.addRecipe(dishId, newStep)
        //   and then sets newStep = ""
        // Copy the "CREATE (given)" Row from DishListScreen and rename things.
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = newStep,
                onValueChange = { newStep = it },
                label = { Text("New step") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(8.dp))
            Button(onClick = {
                viewModel.addRecipe(dishId, newStep)
                newStep = ""
            }) { Text("Add") }
        }

        Spacer(Modifier.height(16.dp))

        // TODO 10 (10 pts) -- READ + DELETE
        // Build a LazyColumn that lists dish.recipes.
        // Use: itemsIndexed(items = dish.recipes, key = { _, r -> r.id }) { index, recipe -> ... }
        // Each row must show:
        // * the step number and text, e.g. Text("${index + 1}. ${recipe.text}")
        //   inside Modifier.weight(1f)
        // * a TextButton "Edit" -> stepBeingEdited = recipe
        // * a TextButton "Delete" -> viewModel.deleteRecipe(dishId, recipe.id)
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            itemsIndexed(items = dish.recipes, key = { _, r -> r.id }) { index, recipe ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "${index + 1}. ${recipe.text}",
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    TextButton(onClick = { stepBeingEdited = recipe }) { Text("Edit") }
                    TextButton(onClick = { viewModel.deleteRecipe(dishId, recipe.id) }) {
                        Text("Delete")
                    }
                }
            }
        }

        // TODO 11 (4 pts) -- UPDATE
        // Copy the dialog block from DishListScreen. When the user taps Save,
        // call viewModel.updateRecipe(dishId, <the edited step's id>, newText)
        // and then set stepBeingEdited = null.
        val editing = stepBeingEdited
        if (editing != null) {
            EditDialog(
                title = "Edit step",
                initialText = editing.text,
                onConfirm = { newText ->
                    viewModel.updateRecipe(dishId, editing.id, newText)
                    stepBeingEdited = null
                },
                onDismiss = { stepBeingEdited = null }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DishDetailScreenPreview() {
    MaterialTheme {
        DishDetailScreen(
            dishId = 1,
            viewModel = DishViewModel(),
            onBack = {}
        )
    }
}
