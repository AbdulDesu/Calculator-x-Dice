package com.sizdev.calculatorxdice.dice

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import com.sizdev.calculatorxdice.R
import kotlinx.android.synthetic.main.activity_dice.*
import kotlin.random.Random

class DiceActivity : AppCompatActivity() {

    // Number of dice
    private val dice1 = 1
    private val dice2 = 2
    private val dice3 = 3
    private val dice4 = 4
    private val dice5 = 5

    // Show drawable of dice
    private val drawableDice1 = R.drawable.dice_1
    private val drawableDice2 = R.drawable.dice_2
    private val drawableDice3 = R.drawable.dice_3
    private val drawableDice4 = R.drawable.dice_4
    private val drawableDice5 = R.drawable.dice_5
    private val drawableDice6 = R.drawable.dice_6

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dice)

        bt_startRoll.setOnClickListener {
            diceRolled()
        }
    }

    private fun diceRolled() {
        val diceGraphic = when (Random.nextInt(6)+1) {
            dice1 -> drawableDice1
            dice2 -> drawableDice2
            dice3 -> drawableDice3
            dice4 -> drawableDice4
            dice5 -> drawableDice5
            else -> drawableDice6
        }
        iv_showDice.setImageResource(diceGraphic)
    }
}
