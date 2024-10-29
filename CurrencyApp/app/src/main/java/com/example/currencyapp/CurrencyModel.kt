package com.example.currencyapp

data class CurrencyModel(
    val name: String,
    val iconResId: Int
) {
    companion object {
        fun getDefaultCurrencies(): List<CurrencyModel> = listOf(
            CurrencyModel("USD", R.drawable.ic_usd),
            CurrencyModel("EUR", R.drawable.ic_eur),
            CurrencyModel("JPY", R.drawable.ic_jpy),
            CurrencyModel("VND", R.drawable.ic_vnd),
            CurrencyModel("KRW", R.drawable.ic_krw),
            CurrencyModel("CNY", R.drawable.ic_cny)

        )
    }
}