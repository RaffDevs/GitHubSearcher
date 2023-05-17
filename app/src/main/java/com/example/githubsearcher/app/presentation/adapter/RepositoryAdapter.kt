package com.example.githubsearcher.app.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearcher.R
import com.example.githubsearcher.app.domain.model.Repository

class RepositoryAdapter() :
    RecyclerView.Adapter<RepositoryAdapter.ViewHolder>() {
    private var listRepository: List<Repository> = emptyList()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.text_repository_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentRepository = listRepository[position]
        holder.textView.text = currentRepository.name
    }

    override fun getItemCount() = listRepository.size

    fun updateRepositories(repositories: List<Repository>) {
        listRepository = repositories
        notifyDataSetChanged()
    }


}

