package com.example.myapplication4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputLayout
import kotlin.random.Random

private const val NONE = -1

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val checkBox1 = findViewById<CheckBox>(R.id.checkbox1)
        val checkBox2 = findViewById<CheckBox>(R.id.checkbox2)
        val switch = findViewById<SwitchMaterial>(R.id.switch1)
        findViewById<SwitchMaterial>(R.id.switch1).setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                checkBox1.isChecked = true
                checkBox2.isChecked = true
                checkBox1.isClickable = true
                checkBox2.isClickable = true
                checkBox1.setTextColor(getColor(R.color.black))
                checkBox2.setTextColor(getColor(R.color.black))
                Toast.makeText(this, "Уведомления включены!", Toast.LENGTH_SHORT).show()
            } else {
                checkBox1.isChecked = false
                checkBox2.isChecked = false
                checkBox1.isClickable = false
                checkBox2.isClickable = false
                checkBox1.setTextColor(getColor(R.color.grey1))
                checkBox2.setTextColor(getColor(R.color.grey1))
                Toast.makeText(this, "Уведомления отключены!", Toast.LENGTH_SHORT).show()
            }
        }

        val progressBarHorizontal = findViewById<ProgressBar>(R.id.progressBar)
        val textViewHorizontalProgress = findViewById<TextView>(R.id.textView3)
        val isStarted = false
        var progressStatus = Random.nextInt(101)
        var handler: Handler? = null

        handler = Handler(Handler.Callback {
            if (isStarted) {
                progressStatus++
            }
            progressBarHorizontal.progress = progressStatus
            textViewHorizontalProgress.text = "${progressStatus}/${progressBarHorizontal.max}"
            handler?.sendEmptyMessageDelayed(0, 100)

            true
        })
        handler.sendEmptyMessage(0)

        val textInputLayoutName = findViewById<TextInputLayout>(R.id.text_input_layout_name)
        val name = findViewById<EditText>(R.id.name)

        name.doOnTextChanged { text, _, _, _ ->
            if (isNameValid(text)) {
                textInputLayoutName.isErrorEnabled = false
            } else {
                textInputLayoutName.error = "Некорректное Имя"
                textInputLayoutName.isErrorEnabled = true
            }
        }

        val textInputLayoutPhone = findViewById<TextInputLayout>(R.id.text_input_layout_phone)
        val phone = findViewById<EditText>(R.id.phone)

        phone.doOnTextChanged { text, _, _, _ ->
            if (isPhoneValid(text)) {
                textInputLayoutPhone.isErrorEnabled = false
            } else {
                textInputLayoutPhone.error = "Некорректный Телефон"
                textInputLayoutPhone.isErrorEnabled = true
            }
        }

        fun showSnackbar(message: String) {
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
        }

        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        radioGroup.setOnCheckedChangeListener { _, buttonId ->
            when (buttonId) {
                R.id.radioButtonOne -> showSnackbar("Мужской пол")
                R.id.radioButtonTwo -> showSnackbar("Женский пол")
                NONE -> showSnackbar("Ничего не выбрано")
            }
        }

        val saveButton = findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener {
            if (name.text.trim().isNotEmpty() && phone.text.trim().isNotEmpty() && radioGroup.getCheckedRadioButtonId()==-1 && switch.isChecked && checkBox1.isChecked || checkBox2.isChecked) {
                Toast.makeText(this, "Информация сохранена", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Информация не сохранена", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isNameValid(name: CharSequence?): Boolean {
        return !name.isNullOrEmpty()
    }

    private fun isPhoneValid(phone: CharSequence?): Boolean {
        return !phone.isNullOrEmpty()
    }
}

