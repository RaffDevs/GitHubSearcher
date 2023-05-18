package com.example.githubsearcher.app.presentation.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearcher.R
import com.example.githubsearcher.app.domain.model.Repository
import com.example.githubsearcher.app.presentation.fragments.WebViewBottomSheetDialog

class RepositoryAdapter() :
    RecyclerView.Adapter<RepositoryAdapter.ViewHolder>() {
    private var listRepository: List<Repository> = emptyList()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.text_repository_name)
        val shareIcon: ImageView = view.findViewById(R.id.image_view_share_button)

        init {
            view.setOnClickListener {
                val item = listRepository[adapterPosition]
                showWebViewDialog(item.url)
            }
        }

        private fun showWebViewDialog(url: String) {
            val bottomSheetDialog = WebViewBottomSheetDialog(url)
            bottomSheetDialog.show(
                (itemView.context as AppCompatActivity).supportFragmentManager,
                bottomSheetDialog.tag
            )

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentRepository = listRepository[position]
        holder.textView.text = currentRepository.name
        holder.shareIcon.setOnClickListener {
            shareURL(currentRepository.url, holder.itemView.context)
        }

    }

    override fun getItemCount() = listRepository.size

    private fun shareURL(url: String, context: Context) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, url)
        context.startActivity(Intent.createChooser(intent, context.getString(R.string.share_title)))
    }


    fun updateRepositories(repositories: List<Repository>) {
        listRepository = repositories
        notifyDataSetChanged()
    }


}

