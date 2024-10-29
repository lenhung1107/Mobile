package com.example.currencyapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var editText1: EditText
    private lateinit var editText2: EditText
    private lateinit var spinnerCurrency1: Spinner
    private lateinit var spinnerCurrency2: Spinner
    private val decimalFormat = DecimalFormat("#,##0.00")


    companion object {
        private const val DEFAULT_FROM_CURRENCY_INDEX = 0 // USD
        private const val DEFAULT_TO_CURRENCY_INDEX = 3   // VND
    }

    private val exchangeRates = mapOf(
        "USD" to mapOf(
            "EUR" to 0.93,
            "JPY" to 145.0,
            "VND" to 25376.0,
            "KRW" to 1.389,
            "CNY" to 7.12
        ),
        "EUR" to mapOf(
            "USD" to 1.08,
            "JPY" to 156.0,
            "VND" to 27396.0,
            "KRW" to 1.499,
            "CNY" to 7.64
        ),
        "JPY" to mapOf(
            "USD" to 0.0069,
            "EUR" to 0.0064,
            "VND" to 175.0,
            "KRW" to 0.0096,
            "CNY" to 0.046
        ),
        "VND" to mapOf(
            "USD" to 0.000041,
            "EUR" to 0.000036,
            "JPY" to 0.0057,
            "KRW" to 0.000021,
            "CNY" to 0.00015
        ),
        "KRW" to mapOf(
            "USD" to 0.719,
            "EUR" to 0.667,
            "JPY" to 104.0,
            "VND" to 4734.0,
            "CNY" to 0.068
        ),
        "CNY" to mapOf(
            "USD" to 0.140,
            "EUR" to 0.131,
            "JPY" to 21.7,
            "VND" to 6644.0,
            "KRW" to 14.75
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()
        setupSpinners()
        setupTextChangeListeners()
        setupSpinnerListeners()
    }

    private fun initializeViews() {
        editText1 = findViewById(R.id.editText1)
        editText2 = findViewById(R.id.editText2)
        spinnerCurrency1 = findViewById(R.id.spinnerCurrency1)
        spinnerCurrency2 = findViewById(R.id.spinnerCurrency2)
    }

    private fun setupSpinners() {
        val currencies = CurrencyModel.getDefaultCurrencies()
        val adapter = CurrencyAdapter(this, currencies)

        spinnerCurrency1.apply {
            this.adapter = adapter
            setSelection(DEFAULT_FROM_CURRENCY_INDEX)
        }

        spinnerCurrency2.apply {
            this.adapter = adapter
            setSelection(DEFAULT_TO_CURRENCY_INDEX)
        }
    }

    private fun setupTextChangeListeners() {
        editText1.addTextChangeListener { text ->
            if (editText1.hasFocus() && text.isNotEmpty()) {
                convertCurrency(
                    amount = text,
                    fromCurrency = spinnerCurrency1.selectedItem as CurrencyModel,
                    toCurrency = spinnerCurrency2.selectedItem as CurrencyModel,
                    targetEditText = editText2
                )
            }
        }

        editText2.addTextChangeListener { text ->
            if (editText2.hasFocus() && text.isNotEmpty()) {
                convertCurrency(
                    amount = text,
                    fromCurrency = spinnerCurrency2.selectedItem as CurrencyModel,
                    toCurrency = spinnerCurrency1.selectedItem as CurrencyModel,
                    targetEditText = editText1
                )
            }
        }
    }

    private fun EditText.addTextChangeListener(afterTextChanged: (String) -> Unit) {
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s?.toString()?.let(afterTextChanged)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun setupSpinnerListeners() {
        val spinnerListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateConversion()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spinnerCurrency1.onItemSelectedListener = spinnerListener
        spinnerCurrency2.onItemSelectedListener = spinnerListener
    }

    private fun updateConversion() {
        when {
            editText1.hasFocus() && editText1.text.isNotEmpty() -> {
                convertCurrency(
                    amount = editText1.text.toString(),
                    fromCurrency = spinnerCurrency1.selectedItem as CurrencyModel,
                    toCurrency = spinnerCurrency2.selectedItem as CurrencyModel,
                    targetEditText = editText2
                )
            }
            editText2.hasFocus() && editText2.text.isNotEmpty() -> {
                convertCurrency(
                    amount = editText2.text.toString(),
                    fromCurrency = spinnerCurrency2.selectedItem as CurrencyModel,
                    toCurrency = spinnerCurrency1.selectedItem as CurrencyModel,
                    targetEditText = editText1
                )
            }
        }
    }

    private fun convertCurrency(
        amount: String,
        fromCurrency: CurrencyModel,
        toCurrency: CurrencyModel,
        targetEditText: EditText
    ) {
        try {
            val numericAmount = amount.replace(",", "").toDouble()
            val exchangeRate = getExchangeRate(fromCurrency.name, toCurrency.name)
            val result = numericAmount * exchangeRate
            targetEditText.setText(decimalFormat.format(result))
        } catch (e: Exception) {
            targetEditText.setText("")
        }
    }

    private fun getExchangeRate(fromCurrency: String, toCurrency: String): Double {
        return when {
            fromCurrency == toCurrency -> 1.0
            exchangeRates[fromCurrency]?.get(toCurrency) != null ->
                exchangeRates[fromCurrency]!![toCurrency]!!
            exchangeRates[toCurrency]?.get(fromCurrency) != null ->
                1.0 / exchangeRates[toCurrency]!![fromCurrency]!!
            else -> 1.0
        }
    }
}