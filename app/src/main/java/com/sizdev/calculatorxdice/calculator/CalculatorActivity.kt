package com.sizdev.calculatorxdice.calculator

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.IdRes
import com.sizdev.calculatorxdice.R
import kotlinx.android.synthetic.main.activity_calculator.*
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.ParseException

class CalculatorActivity : AppCompatActivity(), HistoryFragment.Listener  {
    private var isFutureOperationButtonClicked: Boolean = false
    private var isInstantOperationButtonClicked: Boolean = false
    private var isEqualButtonClicked: Boolean = false

    private var currentNumber: Double = 0.0 // Default Value who can be changed later by an user action
    private var currentResult: Double = 0.0
    private var memory: Double = 0.0

    private var historyText = "" // Recognize type of variable without declaring it (As String)
    private var historyInstantOperationText = ""
    private var historyActionList: ArrayList<String> = ArrayList()

    private val ZERO: String = "0" // Value number of calculator button
    private val ONE: String = "1"
    private val TWO: String = "2"
    private val THREE: String = "3"
    private val FOUR: String = "4"
    private val FIVE: String = "5"
    private val SIX: String = "6"
    private val SEVEN: String = "7"
    private val EIGHT: String = "8"
    private val NINE: String = "9"

    private val INIT = ""

    private val ADDITION = " + " // String Value of Math Operation
    private val SUBTRACTION = " − "
    private val MULTIPLICATION = " × "
    private val DIVISION = " ÷ "

    private val PERCENTAGE = ""
    private val ROOT = "√"
    private val SQUARE = "sqr"
    private val FRACTION = "1/"

    private val NEGATE = "negate"
    private val COMMA = ","
    private val EQUAL = " = "

    private var currentOperation = INIT


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        bt_zero.setOnClickListener {
            onNumberButtonClick(ZERO)
        }

        bt_one.setOnClickListener {
            onNumberButtonClick(ONE)
        }

        bt_two.setOnClickListener {
            onNumberButtonClick(TWO)
        }

        bt_three.setOnClickListener {
            onNumberButtonClick(THREE)
        }

        bt_four.setOnClickListener {
            onNumberButtonClick(FOUR)
        }

        bt_five.setOnClickListener {
            onNumberButtonClick(FIVE)
        }

        bt_six.setOnClickListener {
            onNumberButtonClick(SIX)
        }

        bt_seven.setOnClickListener {
            onNumberButtonClick(SEVEN)
        }

        bt_eight.setOnClickListener {
            onNumberButtonClick(EIGHT)
        }

        bt_nine.setOnClickListener {
            onNumberButtonClick(NINE)
        }

        bt_addition.setOnClickListener {
            onFutureOperationButtonClick(ADDITION)
        }

        bt_subtraction.setOnClickListener {
            onFutureOperationButtonClick(SUBTRACTION)
        }

        bt_multiplication.setOnClickListener {
            onFutureOperationButtonClick(MULTIPLICATION)
        }

        bt_division.setOnClickListener {
            onFutureOperationButtonClick(DIVISION)
        }

        bt_clearEntry.setOnClickListener {
            clearEntry()
        }

        bt_clear.setOnClickListener {
            currentNumber = 0.0
            currentResult = 0.0
            currentOperation = INIT

            historyText = ""
            historyInstantOperationText = ""

            tv_currentNumber.text = formatDoubleToString(currentNumber)
            tv_historyNumber.text = historyText

            isFutureOperationButtonClicked = false
            isEqualButtonClicked = false
            isInstantOperationButtonClicked = false
        }

        bt_backspace.setOnClickListener {

            if (isFutureOperationButtonClicked || isInstantOperationButtonClicked || isEqualButtonClicked) return@setOnClickListener

            var currentValue: String = tv_currentNumber.text.toString()

            val charsLimit = if (currentValue.first().isDigit()) 1 else 2

            if (currentValue.length > charsLimit)
                currentValue = currentValue.substring(0, currentValue.length - 1)
            else
                currentValue = ZERO

            tv_currentNumber.text = currentValue
            currentNumber = formatStringToDouble(currentValue)
        }

        bt_plusMinus.setOnClickListener {

            val currentValue: String = tv_currentNumber.text.toString()

            currentNumber = formatStringToDouble(currentValue)
            if (currentNumber == 0.0) return@setOnClickListener

            currentNumber *= -1
            tv_currentNumber.text = formatDoubleToString(currentNumber)

            if (isInstantOperationButtonClicked) {
                historyInstantOperationText = "($historyInstantOperationText)"
                historyInstantOperationText = StringBuilder().append(NEGATE).append(historyInstantOperationText).toString()
                tv_historyNumber.text = StringBuilder().append(historyText).append(currentOperation).append(historyInstantOperationText).toString()
            }

            if (isEqualButtonClicked) {
                currentOperation = INIT
            }

            isFutureOperationButtonClicked = false
            isEqualButtonClicked = false
        }

        bt_comma.setOnClickListener {

            var currentValue: String = tv_currentNumber.text.toString()

            if (isFutureOperationButtonClicked || isInstantOperationButtonClicked || isEqualButtonClicked) {
                currentValue = StringBuilder().append(ZERO).append(COMMA).toString()
                if (isInstantOperationButtonClicked) {
                    historyInstantOperationText = ""
                    tv_historyNumber.text = StringBuilder().append(historyText).append(currentOperation).toString()
                }
                if (isEqualButtonClicked) currentOperation = INIT
                currentNumber = 0.0
            } else if (currentValue.contains(COMMA)) {
                return@setOnClickListener
            } else currentValue = StringBuilder().append(currentValue).append(COMMA).toString()

            tv_currentNumber.text = currentValue

            isFutureOperationButtonClicked = false
            isInstantOperationButtonClicked = false
            isEqualButtonClicked = false
        }

        bt_equal.setOnClickListener {

            if (isFutureOperationButtonClicked) {
                currentNumber = currentResult
            }

            val historyAllText = calculateResult()

            Toast.makeText(applicationContext, historyAllText, Toast.LENGTH_LONG).show()

            historyActionList.add(historyAllText)

            historyText = StringBuilder().append(formatDoubleToString(currentResult)).toString()

            tv_historyNumber.text = ""

            isFutureOperationButtonClicked = false
            isEqualButtonClicked = true
        }

        bt_percentage.setOnClickListener {
            onInstantOperationButtonClick(PERCENTAGE)
        }

        bt_root.setOnClickListener {
            onInstantOperationButtonClick(ROOT)
        }

        bt_square.setOnClickListener {
            onInstantOperationButtonClick(SQUARE)
        }

        bt_fraction.setOnClickListener {
            onInstantOperationButtonClick(FRACTION)
        }

        bt_memory_clear.isEnabled = false
        bt_memory_clear.setOnClickListener {

            bt_memory_clear.isEnabled = false
            bt_memory_reload.isEnabled = false

            memory = 0.0

            Toast.makeText(applicationContext, getString(R.string.memory_cleared_toast), Toast.LENGTH_SHORT).show()
        }

        bt_memory_reload.isEnabled = false
        bt_memory_reload.setOnClickListener {

            clearEntry(memory)

            Toast.makeText(applicationContext, getString(R.string.memory_recalled_toast), Toast.LENGTH_SHORT).show()
        }

        bt_memory_add.setOnClickListener {

            bt_memory_clear.isEnabled = true
            bt_memory_reload.isEnabled = true

            val currentValue: String = tv_currentNumber.text.toString()
            val thisOperationNumber: Double = formatStringToDouble(currentValue)

            val newMemory = memory + thisOperationNumber

            Toast.makeText(applicationContext, getString(R.string.memory_added_toast) + "${formatDoubleToString(memory)} + ${formatDoubleToString(thisOperationNumber)} = ${formatDoubleToString(newMemory)}", Toast.LENGTH_LONG).show()

            memory = newMemory
        }

        bt_memory_substract.setOnClickListener {

            bt_memory_clear.isEnabled = true
            bt_memory_reload.isEnabled = true

            val currentValue: String = tv_currentNumber.text.toString()
            val thisOperationNumber: Double = formatStringToDouble(currentValue)

            val newMemory = memory - thisOperationNumber

            Toast.makeText(applicationContext, getString(R.string.memory_subtracted_toast) + "${formatDoubleToString(memory)} - ${formatDoubleToString(thisOperationNumber)} = ${formatDoubleToString(newMemory)}", Toast.LENGTH_LONG).show()

            memory = newMemory
        }

        bt_memory_store.setOnClickListener {

            val currentValue: String = tv_currentNumber.text.toString()
            memory = formatStringToDouble(currentValue)

            bt_memory_clear.isEnabled = true
            bt_memory_reload.isEnabled = true

            Toast.makeText(applicationContext, getString(R.string.memory_stored_toast) + formatDoubleToString(memory), Toast.LENGTH_SHORT).show()
        }

    }

    @Throws(IllegalArgumentException::class)
    private fun onNumberButtonClick(number: String, isHistory: Boolean = false) {

        var currentValue: String = tv_currentNumber.text.toString()
        currentValue = if (currentValue == ZERO || isFutureOperationButtonClicked || isInstantOperationButtonClicked || isEqualButtonClicked || isHistory) number else StringBuilder().append(currentValue).append(number).toString()

        try {
            currentNumber = formatStringToDouble(currentValue)
        } catch (e: ParseException) {
            throw IllegalArgumentException("String must be number.")
        }

        tv_currentNumber.text = currentValue

        if (isEqualButtonClicked) {
            currentOperation = INIT
            historyText = ""
        }

        if (isInstantOperationButtonClicked) {
            historyInstantOperationText = ""
            tv_historyNumber.text = StringBuilder().append(historyText).append(currentOperation).toString()
            isInstantOperationButtonClicked = false
        }

        isFutureOperationButtonClicked = false
        isEqualButtonClicked = false
    }

    private fun onFutureOperationButtonClick(operation: String) {

        if (!isFutureOperationButtonClicked && !isEqualButtonClicked) {
            calculateResult()
        }

        currentOperation = operation

        if (isInstantOperationButtonClicked) {
            isInstantOperationButtonClicked = false
            historyText = tv_historyNumber.text.toString()
        }
        tv_historyNumber.text = StringBuilder().append(historyText).append(operation).toString()

        isFutureOperationButtonClicked = true
        isEqualButtonClicked = false
    }

    private fun onInstantOperationButtonClick(operation: String) {

        var currentValue: String = tv_currentNumber.text.toString()
        var thisOperationNumber: Double = formatStringToDouble(currentValue)

        currentValue = "(${formatDoubleToString(thisOperationNumber)})"

        when (operation) {
            PERCENTAGE -> {
                thisOperationNumber = (currentResult * thisOperationNumber) / 100
                currentValue = formatDoubleToString(thisOperationNumber)
            }
            ROOT -> thisOperationNumber = thisOperationNumber.sqrt
            SQUARE -> thisOperationNumber = thisOperationNumber * thisOperationNumber
            FRACTION -> thisOperationNumber = 1 / thisOperationNumber
        }

        if (isInstantOperationButtonClicked) {
            historyInstantOperationText = "($historyInstantOperationText)"
            historyInstantOperationText = StringBuilder().append(operation).append(historyInstantOperationText).toString()
            tv_historyNumber.text = if (isEqualButtonClicked) historyInstantOperationText else StringBuilder().append(historyText).append(currentOperation).append(historyInstantOperationText).toString()
        } else if (isEqualButtonClicked) {
            historyInstantOperationText = StringBuilder().append(operation).append(currentValue).toString()
            tv_historyNumber.text = historyInstantOperationText
        } else {
            historyInstantOperationText = StringBuilder().append(operation).append(currentValue).toString()
            tv_historyNumber.text = StringBuilder().append(historyText).append(currentOperation).append(historyInstantOperationText).toString()
        }

        tv_currentNumber.text = formatDoubleToString(thisOperationNumber)

        if (isEqualButtonClicked) currentResult = thisOperationNumber else currentNumber = thisOperationNumber

        isInstantOperationButtonClicked = true
        isFutureOperationButtonClicked = false
    }

    private fun calculateResult(): String {

        when (currentOperation) {
            INIT -> {
                currentResult = currentNumber
                historyText = StringBuilder().append(tv_historyNumber.text.toString()).toString()
            }
            ADDITION -> currentResult = currentResult + currentNumber
            SUBTRACTION -> currentResult = currentResult - currentNumber
            MULTIPLICATION -> currentResult = currentResult * currentNumber
            DIVISION -> currentResult = currentResult / currentNumber
        }

        tv_currentNumber.text = formatDoubleToString(currentResult)

        if (isInstantOperationButtonClicked) {
            isInstantOperationButtonClicked = false
            historyText = tv_historyNumber.text.toString()
            if (isEqualButtonClicked) historyText = StringBuilder().append(historyText).append(currentOperation).append(formatDoubleToString(currentNumber)).toString()
        } else {
            historyText = StringBuilder().append(historyText).append(currentOperation).append(formatDoubleToString(currentNumber)).toString()
        }

        return StringBuilder().append(historyText).append(EQUAL).append(formatDoubleToString(currentResult)).toString()
    }

    private fun useNumberFormat(): DecimalFormat {

        val symbols = DecimalFormatSymbols()
        symbols.decimalSeparator = ','

        val format = DecimalFormat("#.##############")
        format.decimalFormatSymbols = symbols

        return format
    }

    private fun formatDoubleToString(number: Double): String {
        return useNumberFormat().format(number)
    }

    private fun formatStringToDouble(number: String): Double {
        return useNumberFormat().parse(number).toDouble()
    }


    private val Double.sqrt: Double get() = Math.sqrt(this)

    private fun clearEntry(newNumber: Double = 0.0) {
        historyInstantOperationText = ""

        if (isEqualButtonClicked) {
            currentOperation = INIT
            historyText = ""
        }

        if (isInstantOperationButtonClicked) tv_historyNumber.text = StringBuilder().append(historyText).append(currentOperation).toString()

        isInstantOperationButtonClicked = false
        isFutureOperationButtonClicked = false
        isEqualButtonClicked = false

        currentNumber = newNumber
        tv_currentNumber.text = formatDoubleToString(newNumber)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.menu_item_history -> {
                HistoryFragment.newInstance(historyActionList).show(getSupportFragmentManager(), "dialog")
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }

    override fun onHistoryItemClicked(resultText: String) {

        try {
            onNumberButtonClick(resultText, true)
        } catch (e: IllegalArgumentException) {
            return
        }

        Toast.makeText(applicationContext, getString(R.string.history_result) + resultText, Toast.LENGTH_SHORT).show()
    }

    // Extension function created to add special behaviour to our Activity.
    // Here keyword lazy means it won’t be initialised right away but the first time the value is actually needed.
    fun <T : View> Activity.bind(@IdRes idRes: Int): Lazy<T> {
        // Function will be called only by the main thread to improve performance.
        return lazy(LazyThreadSafetyMode.NONE) { findViewById<T>(idRes) }
    }
}