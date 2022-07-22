package com.mahbub.rest_mvvm.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mahbub.rest_mvvm.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initComponent()
        initFunctionality()
        initObserver()


    }



    private fun initComponent() {

    }
    private fun initFunctionality() {

    }
    private fun initObserver() {

    }

}