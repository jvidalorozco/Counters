package com.cornershop.counterstest.presentation.welcome

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cornershop.counterstest.databinding.ActivityWelcomeBinding
import com.cornershop.counterstest.utils.startActivityNew
import com.cornershop.counterstest.presentation.main.MainScreenActivity


class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        binding.includesWelcomeContent.buttonStart.setOnClickListener {
            startActivityNew<MainScreenActivity>()
        }


    }


}
