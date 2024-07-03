package com.js.zettelkasten.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.js.zettelkasten.ui.navigation.MainNavigation

@Composable
fun SeeAllScreen(navController: NavController, viewModel: StudyCardsViewModel = viewModel()) {
    val cards = viewModel.cards.collectAsState()

    Scaffold (
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(MainNavigation.AddScreen.route) }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Card")
            }
        }
    ){ paddingValues ->
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total terms: ${cards.value.size}",
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    style = TextStyle(fontSize = 20.sp)
                )
                Button(
                    onClick = { navController.navigate(MainNavigation.StudyScreen.route) },
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(text = "Study")
                }
            }
            
            if (cards.value.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No terms added. \n Add some with the button below!",
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    )
                }
            } else {
                LazyColumn(contentPadding = paddingValues) {
                    items(cards.value) { card ->
                        StudyCardView(
                            term = card.term,
                            connection = card.connection,
                            summary = card.summary,
                            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun StudyCardSimple(){
    // For future UI improvements
}