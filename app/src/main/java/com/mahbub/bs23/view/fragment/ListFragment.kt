package com.mahbub.bs23.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.mahbub.bs23.R
import com.mahbub.bs23.databinding.FragmentListBinding
import com.mahbub.bs23.model.Item
import com.mahbub.bs23.utils.ITEM_DETAILS
import com.mahbub.bs23.utils.ResponseHandler
import com.mahbub.bs23.view.adapter.ListAdapter
import com.mahbub.bs23.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ListFragment : Fragment() {


    private lateinit var binding: FragmentListBinding

    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var listAdapter: ListAdapter
    @Inject
    lateinit var gson: Gson

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)

        initComponent()
        initFunctionality()
        initObserver()

        return binding.root
    }

    private fun initComponent() {

        binding.listView.adapter = listAdapter
        listAdapter.onItemClick = {
            Timber.tag("ITEM_CLICK").d("items: $it")

            val bundle = bundleOf(ITEM_DETAILS to gson.toJson(it).toString())
            findNavController().navigate(R.id.to_detailsFragment, bundle)
        }


    }

    private fun initFunctionality() {
        viewModel.getTop50Repositories()

    }

    private fun initObserver() {
        viewModel.observer.observe(viewLifecycleOwner) {
            when (it) {
                is ResponseHandler.Loading -> {

                }
                is ResponseHandler.Success -> {

                    Timber.tag("MAIN_VIEW").d("items: ${it.data?.size}")

                    if (it.data != null) {
                        setItemList(it.data)
                    }

                }
                is ResponseHandler.Error -> {

                }
                else -> {}
            }
        }

    }

    private fun setItemList(data: List<Item>) {
        listAdapter.setData(data)
    }

}