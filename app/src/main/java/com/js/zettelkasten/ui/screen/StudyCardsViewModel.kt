package com.js.zettelkasten.ui.screen

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.js.zettelkasten.data.StudyCard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class StudyCardsViewModel : ViewModel() {
    private val _cards = MutableStateFlow<List<StudyCard>>(emptyList())
    val cards: StateFlow<List<StudyCard>> = _cards

    private val _knownCards = MutableStateFlow<List<StudyCard>>(emptyList())
    val knownCards: StateFlow<List<StudyCard>> = _knownCards

    /*init {
        loadCards()
    }

    private fun loadCards() {
        // Here you would load your data, perhaps from a repository
        _cards.value = listOf(
            StudyCard(1, "Term1", "Connection1", "Summary1"),
            StudyCard(2, "Term2", "Connection2", "Summary2"),
            // Add more cards

            // In the future, will allow accounts so card data will sync.
        )
    }*/

    fun addCard(card: StudyCard) {
        _cards.value += card
    }

    fun moveToKnownSet(card: StudyCard){
        _cards.value = _cards.value.filter { it != card }
        _knownCards.value += card
    }

    fun cycleCard(card: StudyCard){
        _cards.value = _cards.value.filter { it != card }
        _cards.value += card
    }

    fun moveAll(){
        _cards.value += _knownCards.value
        _knownCards.value = emptyList()
    }

    // Add methods to modify cards if necessary
}