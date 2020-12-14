package com.sizdev.calculatorxdice

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sizdev.calculatorxdice.calculator.CalculatorActivity
import com.sizdev.calculatorxdice.dice.DiceActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        bt_rollDice.setOnClickListener {
            val intent = Intent(this, DiceActivity::class.java)
            startActivity(intent)
        }

        bt_startCalculator.setOnClickListener {
            val intent = Intent(this, CalculatorActivity::class.java)
            startActivity(intent)
        }
    }
}