package com.mahbub.bs23.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.mahbub.bs23.databinding.FragmentDetailsBinding
import com.mahbub.bs23.model.Item
import com.mahbub.bs23.utils.Extensions.loadImage
import com.mahbub.bs23.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    lateinit var binding: FragmentDetailsBinding

    @Inject lateinit var gson: Gson

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        binding = FragmentDetailsBinding.inflate(inflater, container,false)

        initComponent()
        initFunctionality()

        return binding.root
    }

    private fun initFunctionality() {


    }

    private fun initComponent() {
       val jsonString = arguments?.getString("details")
       val details =  gson.fromJson(jsonString, Item::class.java)

        setView(details)

    }

    private fun setView(details: Item) {
        Log.d("DETAILS", "setView: $details")

        binding.imageView.loadImage(details.owner.avatar_url)
        binding.nameTv.text = details.full_name
        binding.descTv.text = details.description
        binding.lastUpdatedTv.text = Utils.getDateTime(details.updated_at)

    }

}