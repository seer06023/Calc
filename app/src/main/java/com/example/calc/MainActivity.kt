package com.example.calc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat

enum class CalculatorMode {
    None, Add, Subtract, Multiplicate
}

class MainActivity : AppCompatActivity() {
    private var lastButtonWasMode = false
    private var currentMode = CalculatorMode.None
    private var labelString = ""
    private var savedNum = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupCalculator()
    }
    private fun setupCalculator () {
        val allButtons = arrayOf(button0, button1, button2, button3, button4, button5, button6, button7,
            button8, button9, buttonC, buttonMinus, buttonPlus, buttonEquals, buttonMultiplicate)
        for (i in allButtons.indices) {
            allButtons[i].setOnClickListener { didPressNumber(i) }}

        buttonPlus.setOnClickListener { changeMode(CalculatorMode.Add) }
        buttonMinus.setOnClickListener { changeMode(CalculatorMode.Subtract) }
        buttonMultiplicate.setOnClickListener { changeMode(CalculatorMode.Multiplicate) }
        buttonEquals.setOnClickListener { didPressEquls() }
        buttonC.setOnClickListener { didPressClear() }

    }
    private fun didPressEquls () {
        if (lastButtonWasMode) {
            return
        }
        val labelInt = labelString.toInt()
        when (currentMode) {
            CalculatorMode.Add -> savedNum += labelInt
            CalculatorMode.Subtract -> savedNum -= labelInt
            CalculatorMode.Multiplicate -> savedNum *=labelInt
            CalculatorMode.None -> return
        }
        currentMode = CalculatorMode.None
        labelString = "$savedNum"
        updateText()
        lastButtonWasMode = true
    }

    private fun didPressClear() {
        lastButtonWasMode = false
        currentMode = CalculatorMode.None
        labelString = ""
        savedNum = 0
        textView3.text = getString(R.string.zero)
    }

    private fun updateText() {
        if (labelString.length > 8) {
            didPressClear()
            textView3.text = getString(R.string.tooBig)
            return
        }
        val labelInt = labelString.toInt()
        labelString = labelInt.toString()
        if (currentMode == CalculatorMode.None){
            savedNum = labelInt
        }
        val df = DecimalFormat ("#,###")
        textView3.text = df.format(labelInt)
    }

    private fun changeMode(mode: CalculatorMode) {
        if (savedNum == 0) {
            return
        }
        currentMode = mode
        lastButtonWasMode = true
    }

    private fun didPressNumber(num: Int) {
        val strVal = num.toString()
        if (lastButtonWasMode){
            lastButtonWasMode = false
            labelString = getString(R.string.zero)
        }
        labelString = "$labelString$strVal"
        updateText()
    }
}
/*
test commit*/
