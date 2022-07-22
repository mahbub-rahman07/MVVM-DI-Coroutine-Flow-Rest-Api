package com.mahbub.rest_mvvm.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.mahbub.rest_mvvm.R
import com.mahbub.rest_mvvm.databinding.FragmentDetailsBinding
import com.mahbub.rest_mvvm.model.Item
import com.mahbub.rest_mvvm.utils.Extensions.loadImage
import com.mahbub.rest_mvvm.utils.ITEM_DETAILS
import com.mahbub.rest_mvvm.utils.Utils
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
       val jsonString = arguments?.getString(ITEM_DETAILS)
       val details =  gson.fromJson(jsonString, Item::class.java)

        setView(details)

    }

    private fun setView(details: Item) {
        Log.d("DETAILS", "setView: $details")

        binding.imageView.loadImage(details.owner.avatar_url, R.drawable.placeholder )
        binding.nameTv.text = details.full_name // could not find owner name field
        binding.descTv.text = details.description
        binding.lastUpdatedTv.text = Utils.getDateTime(details.updated_at)

    }

}