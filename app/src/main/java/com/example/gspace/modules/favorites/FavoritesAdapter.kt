package com.example.gspace.modules.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gspace.databinding.RowFavoritesBinding

class FavoritesAdapter : RecyclerView.Adapter<FavoritesViewHolder>() {

    var items: List<String> = mutableListOf()
        set(value) {
            (field as? MutableList)?.apply {
                clear()
                addAll(value)
            }
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FavoritesViewHolder(RowFavoritesBinding.inflate(LayoutInflater.from(parent.context)))


    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bindItem(items[position])
    }


}

class FavoritesViewHolder(private val binding: RowFavoritesBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindItem(item: String) {

    }

}