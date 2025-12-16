package com.example.amrxo.utils

import android.content.Context
import com.example.amrxo.model.Score

object ScoreManager {

    private const val PREFS_NAME = "XOScores"
    private const val KEY_PLAYER_X_SCORE = "playerXScore"
    private const val KEY_PLAYER_O_SCORE = "playerOScore"
    private const val KEY_DRAWS = "draws"

    fun saveScore(context: Context, score: Score) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().apply {
            putInt(KEY_PLAYER_X_SCORE, score.playerXScore)
            putInt(KEY_PLAYER_O_SCORE, score.playerOScore)
            putInt(KEY_DRAWS, score.draws)
            apply()
        }
    }

    fun loadScore(context: Context): Score {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val playerXScore = prefs.getInt(KEY_PLAYER_X_SCORE, 0)
        val playerOScore = prefs.getInt(KEY_PLAYER_O_SCORE, 0)
        val draws = prefs.getInt(KEY_DRAWS, 0)
        return Score(playerXScore, playerOScore, draws)
    }

    fun resetScore(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
    }
}
