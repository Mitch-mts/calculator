package com.example.calculator

import android.icu.text.DecimalFormat
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding
    var number: String? = null
    var firstNumber: Double = 0.0
    var lastNumber: Double = 0.0
    var status: String? = null
    var operator: Boolean = false
    var myFormatter = DecimalFormat("#######.######")
    var history: String = ""
    var currentString: String  = ""




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mainBinding.textViewResult.text = "0"

        mainBinding.btnZero.setOnClickListener {
            onNumberClick("0")
        }

        mainBinding.btnOne.setOnClickListener {
            onNumberClick("1")
        }

        mainBinding.btnTwo.setOnClickListener {
            onNumberClick("2")
        }

        mainBinding.btnThree.setOnClickListener {
            onNumberClick("3")
        }

        mainBinding.btnFour.setOnClickListener {
            onNumberClick("4")
        }

        mainBinding.btnFive.setOnClickListener {
            onNumberClick("5")
        }

        mainBinding.btnSix.setOnClickListener {
            onNumberClick("6")
        }

        mainBinding.btnSeven.setOnClickListener {
            onNumberClick("7")
        }

        mainBinding.btnEight.setOnClickListener {
            onNumberClick("8")
        }

        mainBinding.btnNine.setOnClickListener {
            onNumberClick("9")
        }

        mainBinding.btnAC.setOnClickListener {
            onButtonACClicked()
        }

        mainBinding.btnDelete.setOnClickListener {
            number?.let {
                number = it.substring(0, it.length - 1)
                mainBinding.textViewResult.text = number
            }
        }

        mainBinding.btnDivide.setOnClickListener {

            history = mainBinding.textViewHistory.text.toString()
            currentString = mainBinding.textViewResult.text.toString()
            mainBinding.textViewHistory.text = history.plus(currentString).plus("/")

            if(operator) {
                when(status) {
                    "+" -> plus()
                    "-" -> minus()
                    "*" -> multiply()
                    "/" -> divide()
                    else -> firstNumber = mainBinding.textViewResult.text.toString().toDouble()

                }
            }

            status = "/"
            operator = false
            number = null
        }

        mainBinding.btnMultiply.setOnClickListener {

            history = mainBinding.textViewHistory.text.toString()
            currentString = mainBinding.textViewResult.text.toString()
            mainBinding.textViewHistory.text = history.plus(currentString).plus("*")

            if(operator) {
                when(status) {
                    "+" -> plus()
                    "-" -> minus()
                    "*" -> multiply()
                    "/" -> divide()
                    else -> firstNumber = mainBinding.textViewResult.text.toString().toDouble()

                }
            }

            status = "*"
            operator = false
            number = null
        }

        mainBinding.btnMinus.setOnClickListener {

            history = mainBinding.textViewHistory.text.toString()
            currentString = mainBinding.textViewResult.text.toString()
            mainBinding.textViewHistory.text = history.plus(currentString).plus("-")

            if(operator) {
                when(status) {
                    "+" -> plus()
                    "-" -> minus()
                    "*" -> multiply()
                    "/" -> divide()
                    else -> firstNumber = mainBinding.textViewResult.text.toString().toDouble()

                }
            }

            status = "-"
            operator = false
            number = null
        }

        mainBinding.btnPlus.setOnClickListener {

            history = mainBinding.textViewHistory.text.toString()
            currentString = mainBinding.textViewResult.text.toString()
            mainBinding.textViewHistory.text = history.plus(currentString).plus("+")

            if(operator) {
                when(status) {
                    "+" -> plus()
                    "-" -> minus()
                    "*" -> multiply()
                    "/" -> divide()
                    else -> firstNumber = mainBinding.textViewResult.text.toString().toDouble()

                }
            }

            status = "+"
            operator = false
            number = null
        }

        mainBinding.btnEqual.setOnClickListener {

            history = mainBinding.textViewHistory.text.toString()
            currentString = mainBinding.textViewResult.text.toString()

            if(operator) {
                when(status) {
                    "+" -> plus()
                    "-" -> minus()
                    "*" -> multiply()
                    "/" -> divide()
                    else -> firstNumber = mainBinding.textViewResult.text.toString().toDouble()

                }

                mainBinding.textViewHistory.text = history.plus(currentString).plus("=").plus(mainBinding.textViewResult.text.toString())
            }

            operator = false
        }

        mainBinding.btnDot.setOnClickListener {
            number = if(number == null) {
                "0."
            } else {
                "$number."
            }

            mainBinding.textViewResult.text = number
        }
    }

    fun onButtonACClicked() {
        number = null
        status = null
        firstNumber = 0.0
        lastNumber = 0.0
        mainBinding.textViewResult.text = "0"
        mainBinding.textViewHistory.text = ""
    }

    fun onNumberClick(clickedNumber: String) {

        if(number == null) {
            number = clickedNumber
        } else {
            number += clickedNumber
        }

        mainBinding.textViewResult.text = number
        operator = true
    }

    fun plus() {
        lastNumber = mainBinding.textViewResult.text.toString().toDouble()
        firstNumber += lastNumber
        mainBinding.textViewResult.text = myFormatter.format(firstNumber)

    }

    fun minus() {
        lastNumber = mainBinding.textViewResult.text.toString().toDouble()
        firstNumber -= lastNumber
        mainBinding.textViewResult.text = myFormatter.format(firstNumber)

    }

    fun multiply() {
        lastNumber = mainBinding.textViewResult.text.toString().toDouble()
        firstNumber *= lastNumber
        mainBinding.textViewResult.text = myFormatter.format(firstNumber)

    }

    fun divide() {
        lastNumber = mainBinding.textViewResult.text.toString().toDouble()

        if(lastNumber == 0.0) {
            Toast.makeText(applicationContext, "You can't divide by zero", Toast.LENGTH_SHORT).show()
        } else {
            firstNumber /= lastNumber
            mainBinding.textViewResult.text = myFormatter.format(firstNumber)
        }


    }
}