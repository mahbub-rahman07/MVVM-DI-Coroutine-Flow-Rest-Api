package com.mahbub.rest_mvvm.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.mahbub.rest_mvvm.R
import com.mahbub.rest_mvvm.databinding.FragmentDetailsBinding
import com.mahbub.rest_mvvm.model.Item
import com.mahbub.rest_mvvm.utils.Extensions.loadImage
import com.mahbub.rest_mvvm.utils.ITEM_DETAILS
import com.mahbub.rest_mvvm.utils.Utils
import com.mahbub.rest_mvvm.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    lateinit var binding: FragmentDetailsBinding

    @Inject lateinit var gson: Gson
//    We are using shred view model @activityViewModels
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        binding = FragmentDetailsBinding.inflate(inflater, container,false)

       // initComponent()
        initFunctionality()
        initObserver()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            findNavController().navigateUp()
        }

        return binding.root
    }

    private fun initObserver() {
        lifecycleScope.launchWhenStarted{
            viewModel.itemDetails.collectLatest {
                Log.d("DETAILS", "initObserver: ${it.name}")
                setView(it)
            }
        }

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