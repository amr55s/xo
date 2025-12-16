package com.example.amrxo.ui.howtoplay

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.amrxo.databinding.ActivityHowToPlayBinding

class HowToPlayActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHowToPlayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHowToPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish() // Go back to the previous screen
        }
    }
}
