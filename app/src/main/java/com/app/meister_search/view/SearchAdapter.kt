package com.app.meister_search.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.meister_search.R
import com.app.meister_search.databinding.SearchRowitemBinding
import com.app.meister_search.model.*

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    lateinit var resultList:List<CustomTask>
    var onItemClick: ((CustomTask) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding:SearchRowitemBinding = DataBindingUtil.inflate(inflater, R.layout.search_rowitem, parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.binding.taskName.text = resultList[position].taskName
        holder.binding.projectName.text = resultList[position].projectName
    }

    override fun getItemCount(): Int {
        return resultList.size
    }

    inner class SearchViewHolder(val binding: SearchRowitemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener{
                onItemClick?.invoke(resultList[bindingAdapterPosition])
            }
        }
    }

}

