package com.example.amrxo.ui.game

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.example.amrxo.R
import com.example.amrxo.databinding.ActivityGameBinding
import com.example.amrxo.model.Player
import com.example.amrxo.model.Score
import com.example.amrxo.utils.ScoreManager

class GameActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val PLAYER_X_NAME = "PLAYER_X_NAME"
        const val PLAYER_O_NAME = "PLAYER_O_NAME"
    }

    private lateinit var binding: ActivityGameBinding

    private lateinit var playerX: Player
    private lateinit var playerO: Player
    private lateinit var currentPlayer: Player
    private lateinit var score: Score

    private val boardButtons = Array(3) { arrayOfNulls<Button>(3) }
    private val boardState = Array(3) { Array(3) { ' ' } }

    private var isGameOver = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val playerXName = intent.getStringExtra(PLAYER_X_NAME) ?: "Player X"
        val playerOName = intent.getStringExtra(PLAYER_O_NAME) ?: "Player O"

        playerX = Player(playerXName, 'X')
        playerO = Player(playerOName, 'O')
        currentPlayer = playerX

        score = ScoreManager.loadScore(this)

        initBoard()
        updateScoreDisplay()

        binding.btnResetGame.setOnClickListener { resetBoard() }
        binding.btnExitGame.setOnClickListener { finish() }
    }

    private fun initBoard() {
        for (i in 0..2) {
            for (j in 0..2) {
                val buttonID = "btnCell$i$j"
                val resID = resources.getIdentifier(buttonID, "id", packageName)
                val button = findViewById<Button>(resID)

                boardButtons[i][j] = button
                button.setOnClickListener(this)
            }
        }
        updateTurnIndicator()
    }

    override fun onClick(v: View?) {
        if (isGameOver || v !is Button) return

        for (i in 0..2) {
            for (j in 0..2) {
                if (v == boardButtons[i][j]) {
                    handleMove(i, j)
                    return
                }
            }
        }
    }

    private fun handleMove(row: Int, col: Int) {
        if (boardState[row][col] != ' ') return

        boardState[row][col] = currentPlayer.symbol

        val button = boardButtons[row][col]
        button?.apply {
            text = currentPlayer.symbol.toString()
            isEnabled = false
            setTextColor(
                if (currentPlayer.symbol == 'X')
                    resources.getColor(R.color.x_color, null)
                else
                    resources.getColor(R.color.o_color, null)
            )
        }

        if (checkWinner(currentPlayer.symbol)) {
            endGame(currentPlayer)
        } else if (isDraw()) {
            endGame(null)
        } else {
            switchTurn()
        }
    }

    private fun checkWinner(symbol: Char): Boolean {
        // Rows
        for (i in 0..2) {
            if (boardState[i][0] == symbol &&
                boardState[i][1] == symbol &&
                boardState[i][2] == symbol
            ) return true
        }

        // Columns
        for (i in 0..2) {
            if (boardState[0][i] == symbol &&
                boardState[1][i] == symbol &&
                boardState[2][i] == symbol
            ) return true
        }

        // Main diagonal
        if (boardState[0][0] == symbol &&
            boardState[1][1] == symbol &&
            boardState[2][2] == symbol
        ) return true

        // Reverse diagonal
        if (boardState[0][2] == symbol &&
            boardState[1][1] == symbol &&
            boardState[2][0] == symbol
        ) return true

        return false
    }

    private fun isDraw(): Boolean {
        for (i in 0..2)
            for (j in 0..2)
                if (boardState[i][j] == ' ') return false

        return true
    }

    private fun endGame(winner: Player?) {
        isGameOver = true

        val message: String
        if (winner != null) {
            if (winner.symbol == 'X') {
                score.playerXScore++
            } else {
                score.playerOScore++
            }
            message = "${winner.name} Wins!"
        } else {
            score.draws++
            message = "It's a Draw!"
        }
        ScoreManager.saveScore(this, score)
        updateScoreDisplay()


        disableAllButtons()
        showResultDialog(message, winner != null)
    }

    private fun disableAllButtons() {
        for (i in 0..2)
            for (j in 0..2)
                boardButtons[i][j]?.isEnabled = false
    }

    private fun switchTurn() {
        currentPlayer = if (currentPlayer == playerX) playerO else playerX
        updateTurnIndicator()
    }

    private fun resetBoard() {
        isGameOver = false
        currentPlayer = playerX

        for (i in 0..2) {
            for (j in 0..2) {
                boardState[i][j] = ' '
                boardButtons[i][j]?.apply {
                    text = ""
                    isEnabled = true
                }
            }
        }

        updateTurnIndicator()
    }

    private fun updateTurnIndicator() {
        binding.tvTurnIndicator.text = "Turn: ${currentPlayer.symbol}"
    }

    private fun updateScoreDisplay() {
        binding.tvScoreX.text = "${playerX.name}: ${score.playerXScore}"
        binding.tvScoreO.text = "${playerO.name}: ${score.playerOScore}"
    }

    private fun showResultDialog(message: String, isWin: Boolean) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_result)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val tvMessage = dialog.findViewById<TextView>(R.id.tvResult)
        val btnPlayAgain = dialog.findViewById<Button>(R.id.btnPlayAgain)
        val btnMainMenu = dialog.findViewById<Button>(R.id.btnMainMenu)
        val lottieAnimation = dialog.findViewById<LottieAnimationView>(R.id.lottieAnimation)

        tvMessage.text = message

        if (isWin) {
            lottieAnimation.setAnimation(R.raw.win_animation)
            lottieAnimation.playAnimation()
        } else {
            lottieAnimation.visibility = View.GONE
        }

        btnPlayAgain.setOnClickListener {
            resetBoard()
            dialog.dismiss()
        }

        btnMainMenu.setOnClickListener {
            dialog.dismiss()
            finish()
        }

        dialog.setCancelable(false)
        dialog.show()
    }
}
