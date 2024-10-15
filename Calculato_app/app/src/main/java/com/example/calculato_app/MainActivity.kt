package com.example.calculato_app

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var textResult: TextView

    private var state: Int = 1
    private var op: Int = 0
    private var op1: String = "0"
    private var op2: String = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        textResult = findViewById(R.id.text_result)

        findViewById<Button>(R.id.btn0).setOnClickListener(this)
        findViewById<Button>(R.id.btn1).setOnClickListener(this)
        findViewById<Button>(R.id.btn2).setOnClickListener(this)
        findViewById<Button>(R.id.btn3).setOnClickListener(this)
        findViewById<Button>(R.id.btn4).setOnClickListener(this)
        findViewById<Button>(R.id.btn5).setOnClickListener(this)
        findViewById<Button>(R.id.btn6).setOnClickListener(this)
        findViewById<Button>(R.id.btn7).setOnClickListener(this)
        findViewById<Button>(R.id.btn8).setOnClickListener(this)
        findViewById<Button>(R.id.btn9).setOnClickListener(this)
        findViewById<Button>(R.id.btnCE).setOnClickListener(this)
        findViewById<Button>(R.id.btnC).setOnClickListener(this)
        findViewById<Button>(R.id.btnDelete).setOnClickListener(this)
        findViewById<Button>(R.id.btnDivide).setOnClickListener(this)
        findViewById<Button>(R.id.btnDot).setOnClickListener(this)
        findViewById<Button>(R.id.btnEquals).setOnClickListener(this)
        findViewById<Button>(R.id.btnMinus).setOnClickListener(this)
        findViewById<Button>(R.id.btnMultiply).setOnClickListener(this)
        findViewById<Button>(R.id.btnPlus).setOnClickListener(this)
        findViewById<Button>(R.id.btnPlusMinus).setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        val id = p0?.id
        if (id == R.id.btn0) {
            addDigit(0)
        } else if (id == R.id.btn1) {
            addDigit(1)
        } else if (id == R.id.btn2) {
            addDigit(2)
        } else if (id == R.id.btn3) {
            addDigit(3)
        } else if (id == R.id.btn4) {
            addDigit(4)
        } else if (id == R.id.btn5) {
            addDigit(5)
        } else if (id == R.id.btn6) {
            addDigit(6)
        } else if (id == R.id.btn7) {
            addDigit(7)
        } else if (id == R.id.btn8) {
            addDigit(8)
        } else if (id == R.id.btn9) {
            addDigit(9)
        }
        else if (id == R.id.btnPlus) {
            op = 1
            state = 2
        } else if (id == R.id.btnMinus) {
            op = 2
            state = 2
        } else if (id == R.id.btnDivide) {
            op = 3
            state = 2
        } else if (id == R.id.btnMultiply){
            op = 4
            state = 2
        } else if ( id == R.id.btnCE){
            clear()
        } else if ( id == R.id.btnC){
            resetCalculator()
        } else if ( id == R.id.btnDelete){
            delete()
        } else if (id == R.id.btnDot){
            addDot()
        } else if (id == R.id.btnPlusMinus){
            toggle()
        }
        else if (id == R.id.btnEquals){
            calculateResult()
        }
    }

    fun addDigit(c: Int) {
        if (state == 1) {
            op1 = if (op1 == "0") c.toString() else op1 + c
            textResult.text = op1
        } else {
            op2 = if (op2 == "0") c.toString() else op2 + c
            textResult.text = op2
        }
    }


    fun addDot() {
        val currentOp = if (state == 1) op1 else op2
        if (!currentOp.contains('.')) {
            val newValue = "$currentOp."
            if (state == 1) {
                op1 = newValue
                textResult.text = op1
            } else {
                op2 = newValue
                textResult.text = op2
            }
        }
    }

    fun calculateResult() {
        val num1 = op1.toDoubleOrNull() ?: 0.0
        val num2 = op2.toDoubleOrNull() ?: 0.0
        val result = when (op) {
            1 -> num1 + num2
            2 -> num1 - num2
            3 -> if (num2 != 0.0) num1 / num2 else {
                textResult.text = "Math error"
                return
            }
            4 -> num1 * num2
            else -> 0.0
        }

        textResult.text = formatResult(result)
        op1 = formatResult(result)
        resetState()
    }

    fun formatResult(result: Double): String {
        return if (result == result.toInt().toDouble()) {
            result.toInt().toString()
        } else {
            String.format("%.4f", result).trimEnd('0').trimEnd('.')
        }
    }

    fun resetState() {
        state = 1
        op2 = "0"
        op = 0
    }

    fun resetCalculator() {
        op1 = "0"
        op2 = "0"
        op = 0
        state = 1
        textResult.text = "0"
    }

    fun clear() {
        if (state == 1) {
            op1 = "0"
        } else {
            op2 = "0"
        }
        textResult.text = if (state == 1) op1 else op2
    }

    fun delete() {
        val currentOp = if (state == 1) op1 else op2
        val newValue = if (currentOp.length > 1) {
            currentOp.dropLast(1)
        } else {
            "0"
        }

        if (state == 1) {
            op1 = newValue
            textResult.text = op1
        } else {
            op2 = newValue
            textResult.text = op2
        }
    }

    fun toggle() {
        val currentOp = if (state == 1) op1 else op2
        val newValue = if (currentOp.startsWith("-")) {
            "$currentOp"
        } else {
            "-$currentOp"
        }

        if (state == 1) {
            op1 = newValue
            textResult.text = op1
        } else {
            op2 = newValue
            textResult.text = op2
        }
    }
}