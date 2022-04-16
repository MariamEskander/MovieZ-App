package app.telda.task.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import app.telda.task.databinding.ActivitySplashBinding
import app.telda.task.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint


@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity  : AppCompatActivity(){

    private lateinit var timer: CountDownTimer

    private lateinit var binding: ActivitySplashBinding

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startCounter()
   }

    private fun startCounter() {
        timer = object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                MainActivity.start(this@SplashActivity, true)
            }
        }.start()
    }


}