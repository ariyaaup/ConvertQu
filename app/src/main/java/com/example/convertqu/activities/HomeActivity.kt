package com.example.convertqu

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.convertqu.api.ConvertQuAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    private lateinit var amountEditText: EditText
    private lateinit var resultTextView: TextView
    private lateinit var convertButton: Button
    private lateinit var currencySpinner1: Spinner
    private lateinit var currencySpinner2: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        amountEditText = findViewById(R.id.amountInput)
        resultTextView = findViewById(R.id.convertedAmount)
        convertButton = findViewById(R.id.convertButton)
/*        currencySpinner1 = findViewById<Spinner>(R.id.currencySpinner1)
        currencySpinner2 = findViewById<Spinner>(R.id.currencySpinner2)*/

        val flags = listOf(
            R.drawable.usa_img, R.drawable.usa_img, R.drawable.usa_img, R.drawable.usa_img, R
                .drawable.usa_img, R.drawable.usa_img, R.drawable.usa_img, R.drawable.usa_img, R
                    .drawable.usa_img,R.drawable.usa_img, R.drawable.usa_img, R.drawable.usa_img,
            R.drawable.usa_img, R.drawable.usa_img, R.drawable.usa_img,R.drawable.usa_img, R
                .drawable.usa_img, R.drawable.usa_img,R.drawable.usa_img, R.drawable.usa_img, R
                    .drawable.usa_img,R.drawable.usa_img, R.drawable.usa_img, R.drawable.usa_img,
            R.drawable.usa_img, R.drawable.usa_img, R.drawable.usa_img,R.drawable.usa_img, R
                .drawable.usa_img, R.drawable.usa_img,R.drawable.usa_img, R.drawable.usa_img, R.drawable.usa_img
        )
        val countries = listOf("USD","AUD","BGN","BRL", "CAD", "CHF","CNY","CZK","DKK","EUR","GBP","HKD","HRK","HUF","IDR","ILS","INR","ISK","JPY","KRW","MXN","MYR","NOK","NZD","PHP","PLN","RON","RUB","SEK","SGD","THB","TRY","ZAR")

        val spinner1 = findViewById<Spinner>(R.id.currencySpinner1)
        val spinner2 = findViewById<Spinner>(R.id.currencySpinner2)

        val adapter = SpinnerAdapter(this, flags, countries)

        spinner1.adapter = adapter
        spinner2.adapter = adapter

        convertButton.setOnClickListener {
            val amount = amountEditText.text.toString().toDoubleOrNull()
            if (amount != null) {
                convertCurrency(amount)
            } else {
                Toast.makeText(this, "Please enter a valid amount", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun convertCurrency(amount: Double) {
        ConvertQuAPI.currencyApi.getExchangeRates().enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val exchangeRates = response.body()?.data

                    // Pastikan data tidak null
                    if (exchangeRates == null) {
                        Toast.makeText(this@HomeActivity, "Exchange rates data is null", Toast.LENGTH_SHORT).show()
                        return
                    }

                    // Menambahkan log untuk memeriksa data respons API
                    Log.d("APIResponse", "Exchange Rates: $exchangeRates")

                    val spinner1 = findViewById<Spinner>(R.id.currencySpinner1)
                    val spinner2 = findViewById<Spinner>(R.id.currencySpinner2)
                    var textExchangeRate = findViewById<TextView>(R.id.exchangeRateText)
                    val currencyFrom = spinner1.selectedItem.toString()
                    val currencyTo = spinner2.selectedItem.toString()

                    // Menambahkan log untuk memeriksa mata uang yang dipilih
                    Log.d("Currency", "Currency From: $currencyFrom, Currency To: $currencyTo")

                    // Ambil nilai tukar untuk kedua mata uang yang dipilih
                    val exchangeRateFrom = exchangeRates[currencyFrom] ?: 0.0
                    val exchangeRateTo = exchangeRates[currencyTo] ?: 0.0
                    textExchangeRate.text = String.format("Exchange Rate: %.2f", exchangeRateTo)


                    // Menambahkan log untuk memeriksa nilai tukar
                    Log.d("ExchangeRate", "Exchange Rate From: $exchangeRateFrom, Exchange Rate To: $exchangeRateTo")

                    // Validasi nilai tukar
                    if (exchangeRateFrom != 0.0 && exchangeRateTo != 0.0) {
                        val convertedAmount = (amount / exchangeRateFrom) * exchangeRateTo
                        resultTextView.text = String.format("%.2f %s", convertedAmount, currencyTo)
                    } else {
                        Toast.makeText(this@HomeActivity, "Invalid exchange rates", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Menangani kasus gagal mengambil data dari API
                    Toast.makeText(this@HomeActivity, "Failed to get data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                // Menangani kegagalan saat melakukan permintaan API
                Toast.makeText(this@HomeActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


}
