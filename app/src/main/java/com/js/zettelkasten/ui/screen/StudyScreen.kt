package com.js.zettelkasten.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.js.zettelkasten.R
import com.js.zettelkasten.ui.navigation.MainNavigation

@Composable
fun StudyScreen(navController: NavHostController, viewModel: StudyCardsViewModel = viewModel()) {
    var showEditDialog by rememberSaveable { mutableStateOf(false) }

    val cards by viewModel.cards.collectAsState()
    val knownCards = viewModel.knownCards.collectAsState()

    val firstCard = cards.firstOrNull()

    Scaffold(
        topBar = { MyTopAppBar(navController, viewModel, onEditClicked = {
            showEditDialog = true
        }) },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (firstCard != null) {
                    StudyCardView(
                        term = firstCard.term,
                        connection = firstCard.connection,
                        summary = firstCard.summary,
                        modifier = Modifier.weight(1f)
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        if(knownCards.value.isEmpty()){
                            Text(
                                text = "No terms added. \n Add some with the button above!",
                                textAlign = TextAlign.Center,
                                fontSize = 20.sp
                            )
                        }else{
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    "Good Job!",
                                    textAlign = TextAlign.Center,
                                    fontSize = 24.sp
                                )
                                Text(
                                    text = "You studied everything.",
                                    textAlign = TextAlign.Center,
                                    fontSize = 20.sp
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Button(onClick = {viewModel.moveAll()}) {
                                    Text(text = "Reset")
                                }
                            }
                        }

                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { if (firstCard != null) viewModel.cycleCard(firstCard) },
                        modifier = Modifier
                            .weight(1f)
                            .height(75.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "Wrong",
                            tint = Color.White,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        onClick = { if (firstCard != null) viewModel.moveToKnownSet(firstCard) },
                        modifier = Modifier
                            .weight(1f)
                            .height(75.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Green
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "Right",
                            tint = Color.White,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(navController: NavController, viewModel: StudyCardsViewModel, onEditClicked: () -> Unit) {
    val cards = viewModel.cards.collectAsState()
    val knownCards = viewModel.knownCards.collectAsState()

    TopAppBar(
        title = {
            Text(text = stringResource(R.string.zettelkasten))
        },
        actions = {
            ElevatedButton(
                onClick = {
                    onEditClicked()
                    if (cards.value.isEmpty() && knownCards.value.isEmpty()) {
                        navController.navigate(MainNavigation.AddScreen.route)
                    } else {
                        navController.navigate(MainNavigation.SeeAllScreen.route)
                    }
                },
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Text(text = "Add/Edit/Delete", color = MaterialTheme.colorScheme.onPrimaryContainer)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    )
}

@Composable
fun StudyCardView(
    term: String,
    connection: String,
    summary: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Term",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black
                )
            )
            Text(text = term)
            Text(text = "Connection",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black
                )
            )
            Text(text = connection)
            Text(text = "Summary",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black
                )
            )
            Text(text = summary)
        }
    }
}