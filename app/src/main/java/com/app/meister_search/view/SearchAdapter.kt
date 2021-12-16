package com.app.meister_search.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.meister_search.databinding.SearchRowitemBinding
import com.app.meister_search.model.Paging
import com.app.meister_search.model.Project
import com.app.meister_search.model.SearchResponse
import com.app.meister_search.model.Section

class SearchAdapter : RecyclerView.Adapter<SearchViewHolder>() {

    var searchResponse = SearchResponse()
    var paging = SearchResponse().paging

    fun searchResults(response: SearchResponse, paging: Paging) {
        this.searchResponse = response
        this.paging = paging
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SearchRowitemBinding.inflate(inflater, parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val task = searchResponse.results?.tasks?.get(position)
        val selectedSection: Section =
            searchResponse.results?.sections?.filter { s -> s.id == task?.sectionId }!!.single()
        val selectedProject: Project =
            searchResponse.results?.projects?.filter { p -> p.id == selectedSection.projectId }!!
                .single()
        holder.binding.taskName.text = task?.name ?: "N/A"
        holder.binding.projectName.text = selectedProject.name
    }

    override fun getItemCount(): Int {
        return paging?.totalResults!!
    }
}

class SearchViewHolder(val binding: SearchRowitemBinding) : RecyclerView.ViewHolder(binding.root)
