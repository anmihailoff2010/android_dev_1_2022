package com.example.myapplication10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication10.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import java.util.Calendar

private const val TAG = "MainActivity"
const val TIMER_PROGRESS = "timerProgress"
class MainActivity : AppCompatActivity() {
    private var countNumber = 10
    private var job: Job? = null
    private var isRunning = false
    private lateinit var binding: ActivityMainBinding
    private var timerValue: Int? = null
    private var timeInMillis: Int? = null
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        savedInstanceState?.let {
            timerValue = it.getInt(TIMER_PROGRESS)
            Toast.makeText(this, "$timerValue", Toast.LENGTH_SHORT).show()
            if (timerValue != null) {
                isRunning = false
                updateUI(TimerState.Idle())
                actionTimer(timerValue!!)
                timerValue = null
            }
        }

        binding.sliderBar.addOnChangeListener { _, _, _ -> updateUI(TimerState.Idle()) }

        binding.actionButton.setOnClickListener {
            calendar.timeInMillis
            actionTimer(countNumber)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        job?.cancel()
        if (timerValue != null) outState.putInt(TIMER_PROGRESS, timerValue!!)
        super.onSaveInstanceState(outState)
        outState.putBoolean("isRunning", isRunning)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        isRunning = savedInstanceState.getBoolean("isRunning")
    }

    private fun updateUI(state: TimerState, timerValue: Int? = null) {
        when (state) {
            is TimerState.Idle -> {
                countNumber = binding.sliderBar.value.toInt()
                binding.timerNumber.text = binding.sliderBar.value.toInt().toString()
                binding.progressBar.max = binding.sliderBar.value.toInt()
                binding.progressBar.progress = binding.sliderBar.value.toInt()
                isRunning = false
            }
            is TimerState.Run -> {
                isRunning = true
                binding.actionButton.setText(R.string.stop_timer)
                binding.sliderBar.isEnabled = false
            }
            is TimerState.Stop -> {
                isRunning = false
                binding.timerNumber.text = binding.sliderBar.value.toInt().toString()
                binding.progressBar.progress = countNumber
                binding.actionButton.setText(R.string.start_timer)
                binding.sliderBar.isEnabled = true
                Toast.makeText(this, "Таймер остановлен", Toast.LENGTH_SHORT).show()

            }
            is TimerState.Finish -> {
                isRunning = false
                binding.timerNumber.text = binding.sliderBar.value.toInt().toString()
                binding.progressBar.progress = countNumber
                binding.actionButton.setText(R.string.start_timer)
                binding.sliderBar.isEnabled = true
                Toast.makeText(this, "Таймер завершен", Toast.LENGTH_SHORT).show()
            }
            is TimerState.UpdateProgress -> {
                binding.timerNumber.text = timerValue.toString()
                binding.progressBar.progress = timerValue.toString().toInt()
            }
        }
    }

    private fun actionTimer(countNumber: Int) {
        if (job != null) {
            job?.cancel() // Отмена предыдущей задачи
        }
        if (!isRunning) {
            var counter = countNumber
            updateUI(TimerState.Run())
            job = CoroutineScope(Dispatchers.Main).launch {
                for (timerValue in countNumber downTo 1) {
                    updateUI(TimerState.UpdateProgress(), timerValue)
                    delay(1000)
                    this@MainActivity.timerValue = timerValue
                }
                updateUI(TimerState.Finish())
                cancel()
            }
            isRunning = true // Устанавливаем флаг в true, когда таймер начинает работать
        } else {
            job?.cancel()
            isRunning = false // Устанавливаем флаг в false, когда таймер останавливается
            updateUI(TimerState.Stop())
        }
    }

    sealed class TimerState {
        class Stop : TimerState()
        class Run : TimerState()
        class Finish : TimerState()
        class Idle : TimerState()
        class UpdateProgress : TimerState()
    }
}