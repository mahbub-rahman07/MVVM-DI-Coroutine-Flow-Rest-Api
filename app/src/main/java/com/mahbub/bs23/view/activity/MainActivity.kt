package com.mahbub.bs23.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.mahbub.bs23.R
import com.mahbub.bs23.databinding.ActivityMainBinding
import com.mahbub.bs23.utils.ResponseHandler
import com.mahbub.bs23.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

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