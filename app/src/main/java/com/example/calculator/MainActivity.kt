package com.example.calculator

import android.content.Intent
import android.content.SharedPreferences
import android.icu.text.DecimalFormat
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
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
    var dotControl: Boolean = true
    var buttonEqualsControl: Boolean = false
    lateinit var sharedPreferences: SharedPreferences




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
                if(it.length == 1) {
                    onButtonACClicked()
                } else{
                    number = it.substring(0, it.length - 1)
                    mainBinding.textViewResult.text = number
                    dotControl = number!!.contains(".")
                }

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
            dotControl = true
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
            dotControl = true
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
            dotControl = true
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
            dotControl = true
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
            dotControl = true
            buttonEqualsControl = true
        }

        mainBinding.btnDot.setOnClickListener {

            if(dotControl) {
                number = if(number == null) {
                    "0."
                } else if(buttonEqualsControl) {
                    if(mainBinding.textViewResult.text.toString().contains(".")) {
                        mainBinding.textViewResult.text.toString()
                    } else {
                        mainBinding.textViewResult.text.toString().plus(".")
                    }
                }
                else {
                    "$number."
                }

                mainBinding.textViewResult.text = number
            }

            dotControl = false

        }

        mainBinding.toolBar.setOnMenuItemClickListener { item ->
            when(item.itemId) {
                R.id.settings_item -> {
                    val intent = Intent(this@MainActivity, ChangeThemeActivity::class.java)
                    startActivity(intent)
                    return@setOnMenuItemClickListener true
                }
                else -> {
                    return@setOnMenuItemClickListener false
                }

            }

        }
    }

    fun onButtonACClicked() {
        number = null
        status = null
        firstNumber = 0.0
        lastNumber = 0.0
        mainBinding.textViewResult.text = "0"
        mainBinding.textViewHistory.text = ""
        dotControl = true
    }

    fun onNumberClick(clickedNumber: String) {

        if(number == null) {
            number = clickedNumber
        } else if(buttonEqualsControl) {

            number = if(dotControl) {
                clickedNumber
            } else {
                mainBinding.textViewResult.text.toString().plus(clickedNumber)
            }

            firstNumber = number!!.toDouble()
            lastNumber = 0.0
            status = null
            mainBinding.textViewHistory.text = ""
        }
        else {
            number += clickedNumber
        }

        mainBinding.textViewResult.text = number
        operator = true
        buttonEqualsControl = false
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

    override fun onResume() {
        super.onResume()

        sharedPreferences = this.getSharedPreferences("Dark Theme", MODE_PRIVATE)
        val isDarkMode = sharedPreferences.getBoolean("switch", false)

        if(isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    override fun onPause() {
        super.onPause()

        sharedPreferences = this.getSharedPreferences("Calculations", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("history", mainBinding.textViewHistory.text.toString())
        editor.putString("result", mainBinding.textViewResult.text.toString())
        editor.putBoolean("operator", operator)
        editor.putBoolean("buttonEqualsControl", buttonEqualsControl)
        editor.putBoolean("dotControl", dotControl)
        editor.putString("status", status)
        editor.putString("number", number)
        editor.putString("firstNumber", firstNumber.toString())
        editor.putString("lastNumber", lastNumber.toString())
        editor.apply()

    }

    override fun onStart() {
        super.onStart()

        sharedPreferences = this.getSharedPreferences("Calculations", MODE_PRIVATE)
        mainBinding.textViewHistory.text = sharedPreferences.getString("history", "")
        mainBinding.textViewResult.text = sharedPreferences.getString("result", "")
        operator = sharedPreferences.getBoolean("operator", false)
        buttonEqualsControl = sharedPreferences.getBoolean("buttonEqualsControl", false)
        dotControl = sharedPreferences.getBoolean("dotControl", true)
        status = sharedPreferences.getString("status", null)
        number = sharedPreferences.getString("number", null)
        firstNumber = sharedPreferences.getString("firstNumber", "0.0")!!.toDouble()
        lastNumber = sharedPreferences.getString("lastNumber", "0.0")!!.toDouble()

    }
}