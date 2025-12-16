package com.example.amrxo.ui.scoreboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.amrxo.databinding.ActivityScoreboardBinding
import com.example.amrxo.model.Score
import com.example.amrxo.utils.ScoreManager

class ScoreboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScoreboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateScoreboard()

        binding.btnResetScore.setOnClickListener {
            ScoreManager.resetScore(this)
            updateScoreboard()
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun updateScoreboard() {
        val score = ScoreManager.loadScore(this)
        binding.tvScoreboardXValue.text = score.playerXScore.toString()
        binding.tvScoreboardOValue.text = score.playerOScore.toString()
        binding.tvScoreboardDrawValue.text = score.draws.toString()
    }
}
