package com.js.zettelkasten.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.js.zettelkasten.data.StudyCard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import com.js.zettelkasten.ui.navigation.MainNavigation


@Composable
fun AddScreen (navController: NavController, viewModel: StudyCardsViewModel = viewModel()) {
    val term = remember { mutableStateOf("") }
    val connection = remember { mutableStateOf("") }
    val summary = remember { mutableStateOf("") }

    Column {
        OutlinedTextField(
            value = term.value,
            onValueChange = { term.value = it },
            label = { Text("Term") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 4.dp, start = 12.dp, end = 12.dp)

        )

        OutlinedTextField(
            value = connection.value,
            onValueChange = { connection.value = it },
            label = { Text("Connection") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 4.dp, start = 12.dp, end = 12.dp)
        )

        OutlinedTextField(
            value = summary.value,
            onValueChange = { summary.value = it },
            label = { Text("Summary") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 4.dp, start = 12.dp, end = 12.dp)
                .heightIn(min = 100.dp)

        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Drawing:", modifier = Modifier.padding(start = 24.dp))
            Spacer(modifier = Modifier.weight(1f))
        }

        DrawingCanvas()

        Row(modifier = Modifier.padding(8.dp)) {
            Button(
                onClick = { navController.navigate(MainNavigation.SeeAllScreen.route)},
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text(text = "Cancel")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = {
                    viewModel.addCard(StudyCard(0, term.value, connection.value, summary.value))
                    navController.navigate(MainNavigation.SeeAllScreen.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
            ) {
                Text("Add Card")
            }
        }
    }
}

@Composable
fun DrawingCanvas() {
    val path = remember { mutableStateOf(Path()) }
    val canvasSize = remember { mutableStateOf(IntSize.Zero) }

    Canvas(modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(1f)
        .padding(8.dp)
        .pointerInput(Unit) {
            detectDragGestures(
                onDragStart = { startOffset ->
                    path.value.moveTo(startOffset.x, startOffset.y)
                },
                onDrag = { change, dragAmount ->
                    if (change.position.x in 0f..canvasSize.value.width.toFloat() &&
                        change.position.y in 0f..canvasSize.value.height.toFloat()
                    ) {
                        path.value.lineTo(change.position.x, change.position.y)
                        path.value = Path().apply { addPath(path.value) }
                        change.consume()
                    }
                }
            )
        }
        .border(2.dp, Color.Black)
        .onSizeChanged { newSize -> canvasSize.value = newSize }
    ) {
        drawPath(
            path = path.value,
            color = Color.Black,
            style = androidx.compose.ui.graphics.drawscope.Stroke(
                width = 4.dp.toPx(),
                cap = androidx.compose.ui.graphics.StrokeCap.Round
            )
        )
    }
}