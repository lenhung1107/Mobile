package com.example.createinformationform

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

class MainActivity : AppCompatActivity() {
    private var etMssv: EditText? = null
    private var etFullName: EditText? = null
    private var etEmail: EditText? = null
    private var etPhone: EditText? = null
    private var radioGroupGender: RadioGroup? = null
    private var btnSelectDate: Button? = null
    private var btnSubmit: Button? = null
    private var calendarView: CalendarView? = null
    private var spinnerWard: Spinner? = null
    private var spinnerDistrict: Spinner? = null
    private var spinnerCity: Spinner? = null
    private var checkBoxSports: CheckBox? = null
    private var checkBoxMovies: CheckBox? = null
    private var checkBoxMusic: CheckBox? = null
    private var checkBoxTerms: CheckBox? = null
    private var selectedDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etMssv = findViewById<EditText>(R.id.et_mssv)
        val etFullName = findViewById<EditText>(R.id.et_full_name)
        val etEmail = findViewById<EditText>(R.id.et_email)
        val etPhone = findViewById<EditText>(R.id.et_phone)
        val radioGroupGender = findViewById<RadioGroup>(R.id.radioGroupGender)
        val btnSelectDate = findViewById<Button>(R.id.btn_select_date)
        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        val spinnerWard = findViewById<Spinner>(R.id.spinner_ward)
        val spinnerDistrict = findViewById<Spinner>(R.id.spinner_district)
        val spinnerCity = findViewById<Spinner>(R.id.spinner_city)
        val checkBoxSports = findViewById<CheckBox>(R.id.checkBox_sports)
        val checkBoxMovies = findViewById<CheckBox>(R.id.checkBox_movies)
        val checkBoxMusic = findViewById<CheckBox>(R.id.checkBox_music)
        val checkBoxTerms = findViewById<CheckBox>(R.id.checkBox_terms)
        val btnSubmit = findViewById<Button>(R.id.btn_submit)

        // Hiển thị và chọn ngày sinh
        btnSelectDate.setOnClickListener(View.OnClickListener { v: View? ->
            calendarView.setVisibility(
                if (calendarView.getVisibility() == View.GONE) View.VISIBLE else View.GONE
            )
        })
        calendarView.setOnDateChangeListener(CalendarView.OnDateChangeListener { view: CalendarView?, year: Int, month: Int, dayOfMonth: Int ->
            selectedDate = dayOfMonth.toString() + "/" + (month + 1) + "/" + year
            btnSelectDate.setText("Ngày sinh: $selectedDate")
            calendarView.setVisibility(View.GONE)
        })

        // Xử lý nút Submit
        btnSubmit.setOnClickListener(View.OnClickListener setOnClickListener@{ v: View? ->
            val mssv = etMssv.getText().toString()
            val fullName = etFullName.getText().toString()
            val email = etEmail.getText().toString()
            val phone = etPhone.getText().toString()
            val genderId = radioGroupGender.getCheckedRadioButtonId()
            val agreedToTerms = checkBoxTerms.isChecked()

            // Kiểm tra thông tin
            if (mssv.isEmpty() || fullName.isEmpty() || email.isEmpty() || phone.isEmpty() || genderId == -1 || selectedDate.isEmpty() || !agreedToTerms
            ) {
                Toast.makeText(
                    this,
                    "Vui lòng điền đầy đủ thông tin và đồng ý với các điều khoản.",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // Tiếp tục với logic lưu thông tin
            Toast.makeText(this, "Thông tin đã được lưu.", Toast.LENGTH_SHORT).show()
        })
    }
}
