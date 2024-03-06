package com.example.skillbox_hw_quiz

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.skillbox_hw_quiz.databinding.ActivityWelcomeBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Calendar

class WelcomeActivity : AppCompatActivity() {
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("dd.MM.yyyy")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.skipButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.pickDateButton.setOnClickListener {
            val dateDialog = MaterialDatePicker.Builder.datePicker()
                .setTitleText(resources.getString(R.string.choose_your_birthday))
                .build()
            dateDialog.addOnPositiveButtonClickListener { timeInMillis ->
                calendar.timeInMillis = timeInMillis
                Snackbar.make(
                    binding.pickDateButton,
                    dateFormat.format(calendar.time),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
            dateDialog.show(supportFragmentManager, "DatePicker")
        }
    }
}