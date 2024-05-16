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


        // Handle the Clear button click
        binding.clearButton.setOnClickListener { clear() }

        // Handle all of the numbers button click
        binding.button0.setOnClickListener {
            if (!(editText.text.length == 1 && editText.text.toString() == "0"))  editText.text.append(getString(R.string.zero))
        }

        binding.button1.setOnClickListener { addNumber(getString(R.string.one)) }
        binding.button2.setOnClickListener { addNumber(getString(R.string.two)) }
        binding.button3.setOnClickListener { addNumber(getString(R.string.three)) }
        binding.button4.setOnClickListener { addNumber(getString(R.string.four)) }
        binding.button5.setOnClickListener { addNumber(getString(R.string.five)) }
        binding.button6.setOnClickListener { addNumber(getString(R.string.six)) }
        binding.button7.setOnClickListener { addNumber(getString(R.string.seven)) }
        binding.button8.setOnClickListener { addNumber(getString(R.string.eight)) }
        binding.button9.setOnClickListener { addNumber(getString(R.string.nine )) }

        // Handle the dot button
        binding.dotButton.setOnClickListener {
            if (!editText.text.contains(getString(R.string.dot))) {
                when {
                    editText.text.toString() == "-"  -> editText.text.append(getString(R.string.zeroDot))
                    editText.text.isNotEmpty() -> editText.text.append(getString(R.string.dot))
                    else -> editText.setText(getString(R.string.zeroDot))
                }
            }
        }

        // Handle of all operators buttons
        binding.multiplyButton.setOnClickListener { addOperator(getString(R.string.multiply)) }
        binding.addButton.setOnClickListener { addOperator(getString(R.string.add)) }
        binding.divideButton.setOnClickListener { addOperator(getString(R.string.divide)) }
        binding.subtractButton.setOnClickListener {
            if (editText.text.isEmpty() && (operator != "" || (lastOperator == "")) ) editText.text.append(getString(R.string.subtract))
            else addOperator(getString(R.string.subtract))
        }

        binding.equalButton.setOnClickListener {
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