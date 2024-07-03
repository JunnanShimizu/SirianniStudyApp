package com.js.zettelkasten.data

import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.serialization.Serializable

@Serializable
data class StudyCard(
    val id: Int,
    val term: String,
    val connection: String,
    val summary: String,
    //val drawing: ImageBitmap
)