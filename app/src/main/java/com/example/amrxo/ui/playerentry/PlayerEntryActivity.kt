package com.example.amrxo.ui.playerentry

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.amrxo.MainActivity
import com.example.amrxo.databinding.ActivityPlayerEntryBinding
import com.example.amrxo.ui.game.GameActivity
import com.google.android.material.snackbar.Snackbar

class PlayerEntryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerEntryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            val playerXName = binding.etPlayerX.text.toString().trim()
            val playerOName = binding.etPlayerO.text.toString().trim()

            if (playerXName.isEmpty() || playerOName.isEmpty()) {
                Snackbar.make(binding.root, "Please enter names for both players.", Snackbar.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, GameActivity::class.java).apply {
                    putExtra(GameActivity.PLAYER_X_NAME, playerXName)
                    putExtra(GameActivity.PLAYER_O_NAME, playerOName)
                }
                startActivity(intent)
            }
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}
