package org.hyperskill.calculator

import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import org.hyperskill.calculator.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var editText: EditText
    private var operator  = ""

    private var firstNumber = 0.0
    private var lastOperator = ""
    private var secondNumber = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        editText = binding.displayEditText
        editText.inputType = InputType.TYPE_NULL

        val clearButton = binding.clearButton
        val equalButton = binding.equalButton

        val addButton = binding.addButton
        val subButton = binding.subtractButton
        val multiplyButton = binding.multiplyButton
        val divideButton = binding.divideButton

        val button0 = binding.button0
        val button1 = binding.button1
        val button2 = binding.button2
        val button3 = binding.button3
        val button4 = binding.button4
        val button5 = binding.button5
        val button6 = binding.button6
        val button7 = binding.button7
        val button8 = binding.button8
        val button9 = binding.button9
        val dotButton = binding.dotButton


        // Handle the Clear button click
        clearButton.setOnClickListener { clear() }


        // Handle all of the numbers button click
        button0.setOnClickListener {
            if (!(editText.text.length == 1 && editText.text.toString() == "0"))  editText.text.append(button0.text)
        }
        button1.setOnClickListener { addNumber(button1.text.toString()) }
        button2.setOnClickListener { addNumber(button2.text.toString()) }
        button3.setOnClickListener { addNumber(button3.text.toString()) }
        button4.setOnClickListener { addNumber(button4.text.toString()) }
        button5.setOnClickListener { addNumber(button5.text.toString()) }
        button6.setOnClickListener { addNumber(button6.text.toString()) }
        button7.setOnClickListener { addNumber(button7.text.toString()) }
        button8.setOnClickListener { addNumber(button8.text.toString()) }
        button9.setOnClickListener { addNumber(button9.text.toString()) }

        // Handle the dot button
        dotButton.setOnClickListener {
            if (!editText.text.contains(dotButton.text)) {
                when {
                    editText.text.toString() == "-"  -> editText.text.append(getString(R.string.zeroDot))
                    editText.text.isNotEmpty() -> editText.text.append(dotButton.text)
                    else -> editText.setText(getString(R.string.zeroDot))
                }
            }
        }

        // Handle of all operators buttons
        addButton.setOnClickListener { addOperator(addButton.text.toString()) }
        multiplyButton.setOnClickListener { addOperator(multiplyButton.text.toString()) }
        divideButton.setOnClickListener { addOperator(divideButton.text.toString()) }
        subButton.setOnClickListener {
            if (editText.text.isEmpty() && (operator != "" || (lastOperator == "")) ) editText.text.append(subButton.text)
            else addOperator(subButton.text.toString())
        }

        equalButton.setOnClickListener {
            when {
                operator != "" -> calculate(firstNumber, editText.text.toString(), operator)
                lastOperator != "" -> calculate(firstNumber, editText.text.toString(), lastOperator)
                else -> {
                    editText.hint = if (editText.text.isEmpty()) convertNumToString(firstNumber.toString())
                    else  convertNumToString(editText.text.toString())
                }
            }

            editText.text.clear()
            operator = ""
        }
    }

    private fun addNumber(number: String) {
        when {
            editText.text.length == 1 && editText.text.toString() == "0" -> editText.setText(number)
            editText.text.length == 2 && editText.text.toString() == "-0" -> editText.setText("-${number}")
            else -> editText.append(number)
        }
    }

    private fun addOperator(op: String) {
        if (editText.text.isNotEmpty()) {
            firstNumber = editText.text.toString().toDouble()
            secondNumber = editText.text.toString().toDouble()
            editText.hint =  convertNumToString(editText.text.toString())
            editText.text.clear()
        } else {
            firstNumber = editText.hint.toString().toDouble()
            secondNumber = editText.hint.toString().toDouble()
            editText.hint = convertNumToString(editText.hint.toString())
        }
        operator = op
    }

    private fun calculate(leftNumber: Double, rightNumber: String, symbol: String) {
        val right = if (rightNumber.isEmpty()) secondNumber else rightNumber.toDouble()

        val result = when (symbol) {
            "+" -> (leftNumber + right).toString()
            "-" -> (leftNumber - right).toString()
            "/" -> (leftNumber / right).toString()
            else -> (leftNumber * right).toString()
        }

        editText.hint = convertNumToString(result)
        firstNumber = result.toDouble()
        secondNumber = right
        lastOperator = symbol
    }

    private fun convertNumToString(number: String) = if (number.contains(".0"))  number.dropLast(2) else number

    private fun clear() {
        editText.text.clear()
        editText.hint = getString(R.string.zero)
        operator = ""
        lastOperator = ""
        firstNumber = 0.0
        secondNumber = 0.0
    }

}