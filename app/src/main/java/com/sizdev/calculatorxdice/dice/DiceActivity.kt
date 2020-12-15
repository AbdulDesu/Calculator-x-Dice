package com.sizdev.calculatorxdice.dice

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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
        val dice02: MediaPlayer? = MediaPlayer.create(this, R.raw.dice2)
        val dice03: MediaPlayer? = MediaPlayer.create(this, R.raw.dice3)
        val dice04: MediaPlayer? = MediaPlayer.create(this, R.raw.dice4)
        val dice05: MediaPlayer? = MediaPlayer.create(this, R.raw.dice5)
        val dice06: MediaPlayer? = MediaPlayer.create(this, R.raw.dice6)
        val dice07: MediaPlayer? = MediaPlayer.create(this, R.raw.dice7)
        val dice08: MediaPlayer? = MediaPlayer.create(this, R.raw.dice8)
        val dice09: MediaPlayer? = MediaPlayer.create(this, R.raw.dice9)
        val dice10: MediaPlayer? = MediaPlayer.create(this, R.raw.dice10)
        val dice11: MediaPlayer? = MediaPlayer.create(this, R.raw.dice11)
        val dice12: MediaPlayer? = MediaPlayer.create(this, R.raw.dice12)
        val diceDouble2: MediaPlayer? = MediaPlayer.create(this, R.raw.dicedouble2)
        val diceDouble4: MediaPlayer? = MediaPlayer.create(this, R.raw.dicedouble4)
        val diceDouble6: MediaPlayer? = MediaPlayer.create(this, R.raw.dicedouble6)
        val diceDouble8: MediaPlayer? = MediaPlayer.create(this, R.raw.dicedouble8)
        val diceDouble10: MediaPlayer? = MediaPlayer.create(this, R.raw.dicedouble10)
        val diceDouble12: MediaPlayer? = MediaPlayer.create(this, R.raw.dicedouble12)

        val dice1Number = Random.nextInt(6) + 1
        val dice2Number = Random.nextInt(6) + 1

        when (dice1Number + dice2Number) {
            2 -> { if (dice1Number == dice2Number) {diceDouble2?.start()} else dice02?.start()}
            3 -> dice03?.start()
            4 -> {if (dice1Number == dice2Number) {diceDouble4?.start()} else dice04?.start()}
            5 -> dice05?.start()
            6 -> {if (dice1Number == dice2Number) {diceDouble6?.start()} else dice06?.start()}
            7 -> dice07?.start()
            8 -> {if (dice1Number == dice2Number) {diceDouble8?.start()} else dice08?.start()}
            9 -> dice09?.start()
            10 -> {if (dice1Number == dice2Number) {diceDouble10?.start()} else dice10?.start()}
            11 -> dice11?.start()
            else -> {if (dice1Number == dice2Number) {diceDouble12?.start()} else dice12?.start()}
        }

        val dice1Graphic = when (dice1Number) {
            dice1 -> drawableDice1
            dice2 -> drawableDice2
            dice3 -> drawableDice3
            dice4 -> drawableDice4
            dice5 -> drawableDice5
            else -> drawableDice6
        }

        val dice2Graphic = when (dice2Number) {
            dice1 -> drawableDice1
            dice2 -> drawableDice2
            dice3 -> drawableDice3
            dice4 -> drawableDice4
            dice5 -> drawableDice5
            else -> drawableDice6
        }

        iv_showDice.setImageResource(dice1Graphic)
        iv_showDice2.setImageResource(dice2Graphic)
    }

}


