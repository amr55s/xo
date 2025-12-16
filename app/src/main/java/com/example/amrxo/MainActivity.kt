package com.example.amrxo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.amrxo.databinding.ActivityMainBinding
import com.example.amrxo.ui.howtoplay.HowToPlayActivity
import com.example.amrxo.ui.playerentry.PlayerEntryActivity
import com.example.amrxo.ui.scoreboard.ScoreboardActivity
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStartGame.setOnClickListener {
            startActivity(Intent(this, PlayerEntryActivity::class.java))
        }

        binding.btnScoreboard.setOnClickListener {
            startActivity(Intent(this, ScoreboardActivity::class.java))
        }

        binding.btnHowToPlay.setOnClickListener {
            startActivity(Intent(this, HowToPlayActivity::class.java))
        }

        binding.btnExitApp.setOnClickListener {
            finishAffinity() // Closes all activities
            exitProcess(0) // Exits the app process
        }
    }
}
