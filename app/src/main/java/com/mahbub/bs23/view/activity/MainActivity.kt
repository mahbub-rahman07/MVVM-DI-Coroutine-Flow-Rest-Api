package com.mahbub.bs23.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.mahbub.bs23.databinding.ActivityMainBinding
import com.mahbub.bs23.utils.ResponseHandler
import com.mahbub.bs23.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

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
        viewModel.getTop50Repositories()

    }
    private fun initObserver() {
        viewModel.observer.observe(this) {
            when(it) {
                is ResponseHandler.Loading -> {

                }
                is ResponseHandler.Success -> {

                    Timber.tag("MAIN_VIEW").d("items: ${it.data?.size}")

                }
                is ResponseHandler.Error -> {

                }else -> {

                }
            }
        }

    }

}