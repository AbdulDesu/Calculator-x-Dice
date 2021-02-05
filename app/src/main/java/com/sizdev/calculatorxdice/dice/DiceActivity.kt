package com.sizdev.calculatorxdice.dice

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.sizdev.calculatorxdice.R
import kotlinx.android.synthetic.main.activity_dice.*
import kotlin.random.Random

class DiceActivity : AppCompatActivity() {

    // Set Media Player
    private lateinit var audio: MediaPlayer

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

        val dice1Number = Random.nextInt(6) + 1
        val dice2Number = Random.nextInt(6) + 1

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

        when (dice1Number + dice2Number) {
            2 -> {
                audio = MediaPlayer.create(this, R.raw.dicedouble2)
                audio.start()
                Toast.makeText(this, "You got double 2 !", Toast.LENGTH_SHORT).show()
            }
            3 -> {
                audio = MediaPlayer.create(this, R.raw.dice3)
                audio.start()
                Toast.makeText(this, "You got 3 !", Toast.LENGTH_SHORT).show()
            }
            4 -> {
                if (dice1Number == dice2Number) {
                    audio = MediaPlayer.create(this, R.raw.dicedouble4)
                    audio.start()
                    Toast.makeText(this, "You got double 4 !", Toast.LENGTH_SHORT).show()
                }
                else {
                    audio = MediaPlayer.create(this, R.raw.dice4)
                    audio.start()
                    Toast.makeText(this, "You got 4 !", Toast.LENGTH_SHORT).show()
                }
            }
            5 -> {
                audio = MediaPlayer.create(this, R.raw.dice5)
                audio.start()
                Toast.makeText(this, "You got 5 !", Toast.LENGTH_SHORT).show()
            }
            6 -> {
                if (dice1Number == dice2Number) {
                    audio = MediaPlayer.create(this, R.raw.dicedouble6)
                    audio.start()
                    Toast.makeText(this, "You got double 6 !", Toast.LENGTH_SHORT).show()
                }
                else {
                    audio = MediaPlayer.create(this, R.raw.dice6)
                    audio.start()
                    Toast.makeText(this, "You got 6 !", Toast.LENGTH_SHORT).show()
                }
            }
            7 -> {
                audio = MediaPlayer.create(this, R.raw.dice7)
                audio.start()
                Toast.makeText(this, "You got 7 !", Toast.LENGTH_SHORT).show()
            }
            8 -> {
                if (dice1Number == dice2Number) {
                    audio = MediaPlayer.create(this, R.raw.dicedouble8)
                    audio.start()
                    Toast.makeText(this, "You got double 8 !", Toast.LENGTH_SHORT).show()
                }
                else {
                    audio = MediaPlayer.create(this, R.raw.dice8)
                    audio.start()
                    Toast.makeText(this, "You got 8 !", Toast.LENGTH_SHORT).show()
                }
            }
            9 -> {
                audio = MediaPlayer.create(this, R.raw.dice9)
                audio.start()
                Toast.makeText(this, "You got 9 !", Toast.LENGTH_SHORT).show()
            }
            10 -> {
                if (dice1Number == dice2Number) {
                    audio = MediaPlayer.create(this, R.raw.dicedouble10)
                    audio.start()
                    Toast.makeText(this, "You got double 10 !", Toast.LENGTH_SHORT).show()
                } else {
                    audio = MediaPlayer.create(this, R.raw.dice10)
                    audio.start()
                    Toast.makeText(this, "You got 10 !", Toast.LENGTH_SHORT).show()
                }
            }
            11 -> {
                audio = MediaPlayer.create(this, R.raw.dice11)
                audio.start()
                Toast.makeText(this, "You got 11 !", Toast.LENGTH_SHORT).show()
            }
            else -> {
                if (dice1Number == dice2Number) {
                    audio = MediaPlayer.create(this, R.raw.dicedouble12)
                    audio.start()
                    Toast.makeText(this, "You got double 12 !", Toast.LENGTH_SHORT).show()
                }
                else {
                    audio = MediaPlayer.create(this, R.raw.dice12)
                    audio.start()
                    Toast.makeText(this, "You got 12 !", Toast.LENGTH_SHORT).show()
                }
            }
        }

        iv_showDice.setImageResource(dice1Graphic)
        iv_showDice2.setImageResource(dice2Graphic)
    }

}


