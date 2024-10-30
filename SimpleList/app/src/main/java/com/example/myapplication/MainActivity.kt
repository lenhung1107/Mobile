package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class MainActivity : AppCompatActivity() {
    private lateinit var edt1: EditText
    private lateinit var edt2: TextView
    private lateinit var btn: Button
    private lateinit var radioButton: RadioButton
    private lateinit var radioButton2: RadioButton
    private lateinit var radioButton3: RadioButton
    private lateinit var radioGroup: RadioGroup
    private lateinit var lv: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edt1 = findViewById(R.id.edt1)
        edt2 = findViewById(R.id.edt2)
        radioButton = findViewById(R.id.radioButton)
        radioButton2 = findViewById(R.id.radioButton2)
        radioButton3 = findViewById(R.id.radioButton3)
        radioGroup = findViewById(R.id.radioGroup)
        btn = findViewById(R.id.btn)
        lv = findViewById(R.id.lv)

        btn.setOnClickListener {
            val input = edt1.text.toString()
            edt2.visibility = View.VISIBLE
            edt2.text = "" //Delete error notification

            // Check input
            val n = try {
                input.toInt()
            } catch (e: NumberFormatException) {
                edt2.text = "Please enter a valid integer!"
                return@setOnClickListener
            }

            // Choose kind of number
            val selectedRadioButtonId = radioGroup.checkedRadioButtonId
            if (selectedRadioButtonId == -1) {
                edt2.text = "Please choose kind of number!"
                return@setOnClickListener
            }

            // List number will display
            val numbers = when (selectedRadioButtonId) {
                R.id.radioButton -> getEvenNumbers(n)
                R.id.radioButton2 -> getOddNumbers(n)
                R.id.radioButton3 -> getPerfectSquareNumbers(n)
                else -> emptyList()
            }

            // List number in ListView
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, numbers)
            lv.adapter = adapter
        }
    }

    private fun getEvenNumbers(n: Int): List<Int> {
        return (0..n).filter { it % 2 == 0 }
    }

    private fun getOddNumbers(n: Int): List<Int> {
        return (1..n).filter { it % 2 != 0 }
    }

    private fun getPerfectSquareNumbers(n: Int): List<Int> {
        return (0..n).filter { k ->
            val sqrt = Math.sqrt(k.toDouble()).toInt()
            sqrt * sqrt == k
        }
    }
}
