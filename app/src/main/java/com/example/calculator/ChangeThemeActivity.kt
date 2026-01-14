package com.example.calculator

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calculator.databinding.ActivityChangeThemeBinding

class ChangeThemeActivity : AppCompatActivity() {
    lateinit var changeThemeBinding: ActivityChangeThemeBinding
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        changeThemeBinding = ActivityChangeThemeBinding.inflate(layoutInflater)
        val view = changeThemeBinding.root
        setContentView(view)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        changeThemeBinding.toolBar2.setNavigationOnClickListener {
            finish()
        }

        changeThemeBinding.mySwitch.setOnCheckedChangeListener { _, isChecked ->

            sharedPreferences = this.getSharedPreferences("Dark Theme", MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            if(isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor.putBoolean("switch", true)

            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor.putBoolean("switch", false)

            }

            editor.apply()

        }
    }

    override fun onResume() {
        super.onResume()

        sharedPreferences = this.getSharedPreferences("Dark Theme", MODE_PRIVATE)
        val isDarkMode = sharedPreferences.getBoolean("switch", false)
        changeThemeBinding.mySwitch.isChecked = isDarkMode

    }
}