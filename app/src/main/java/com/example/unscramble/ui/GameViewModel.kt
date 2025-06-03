package com.example.unscramble.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.unscramble.data.EasyWords
import com.example.unscramble.data.HardWords
import com.example.unscramble.data.MAX_NO_OF_WORDS
import com.example.unscramble.data.MediumWords
import com.example.unscramble.data.SCORE_INCREASE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    var userGuess by mutableStateOf("")
        private set

    private var usedWords: MutableSet<String> = mutableSetOf()
    private lateinit var currentWord: String

    init {
        resetGame()
    }

    fun setDifficulty(newDifficulty: Int) {
        _uiState.update { currentState ->
            currentState.copy(difficulty = newDifficulty)
        }
        resetGame()
    }

    fun resetGame() {
        usedWords.clear()
        val currentDifficulty = _uiState.value.difficulty
        _uiState.value = GameUiState(
            currentScrambledWord = pickRandomWordAndShuffle(currentDifficulty), // Pass difficulty explicitly
            difficulty = currentDifficulty // Ensure the new GameUiState instance has the correct difficulty
        )
    }

    fun updateUserGuess(guessedWord: String) {
        userGuess = guessedWord
    }

    fun checkUserGuess() {
        if (userGuess.equals(currentWord, ignoreCase = true)) {
            val updatedScore =
                _uiState.value.score.plus(SCORE_INCREASE)   //score_increase is defined in wordsdata.kt it just equals to 20
            updateGameState(updatedScore)
        } else {
            _uiState.update { currentState ->
                currentState.copy(isGuessedWordWrong = true)

            }
        }
        updateUserGuess("")
    }

    fun skipWord() {
        updateGameState(_uiState.value.score)
        updateUserGuess("")
    }

    private fun updateGameState(updatedScore: Int) {
        if (usedWords.size == MAX_NO_OF_WORDS) {
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    score = updatedScore,
                    isGameOver = true
                )
            }
        } else {
            val nextScrambledWord = pickRandomWordAndShuffle(_uiState.value.difficulty)
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    currentScrambledWord = nextScrambledWord,
                    currentWordCount = currentState.currentWordCount.inc(),
                    score = updatedScore
                )
            }
        }
    }


    private fun shuffleCurrentWord(word: String): String {
        val tempWord = word.toCharArray()
        tempWord.shuffle()
        while (String(tempWord) == word) {
            tempWord.shuffle()
        }
        return String(tempWord)
    }

    private fun pickRandomWordAndShuffle(difficulty: Int): String {
        currentWord = when (difficulty) {
            1 -> EasyWords.random()
            2 -> MediumWords.random()
            3 -> HardWords.random()
            else -> EasyWords.random()
        }
        return if (usedWords.contains(currentWord)) {
            pickRandomWordAndShuffle(difficulty)
        } else {
            usedWords.add(currentWord)
            shuffleCurrentWord(currentWord)
        }
    }
}