package app.telda.task.ui.main

import android.app.Activity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import app.telda.task.R
import app.telda.task.databinding.ActivityMainBinding
import app.telda.task.utils.extensions.launchActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity  : AppCompatActivity(){

    companion object {
        fun start(activity: Activity?, finish: Boolean = true) {
            if (finish)
                activity?.finish()
            activity?.launchActivity<MainActivity>()
        }
    }

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

}