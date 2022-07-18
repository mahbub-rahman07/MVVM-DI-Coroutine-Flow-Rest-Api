package com.tenminuteschool.bs23.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.tenminuteschool.bs23.R
import com.tenminuteschool.bs23.databinding.ListItemViewBinding
import com.tenminuteschool.bs23.model.Item

class ListAdapter (private val dataSet: ArrayList<Item>) :
RecyclerView.Adapter<ListAdapter.ViewHolder>(){

    var onItemClick: ((Item) -> Unit)? = null

    class ViewHolder(val binding: ListItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemViewBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.title.text = dataSet[position].full_name

        holder.binding.root.setOnClickListener {
            onItemClick?.invoke(dataSet[position])
        }

    }

    override fun getItemCount() = dataSet.size
    fun setData(data: List<Item>) {
        dataSet.clear()
        dataSet.addAll(data)
        notifyDataSetChanged()
    }
}