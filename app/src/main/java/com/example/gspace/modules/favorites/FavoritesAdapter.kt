package com.example.gspace.modules.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gspace.databinding.RowFavoritesBinding
import com.example.gspace.modules.station.room.StationEntity

class FavoritesAdapter(private val onImageViewClick: (StationEntity) -> Unit) :
    RecyclerView.Adapter<FavoritesViewHolder>() {

    var items: List<StationEntity> = mutableListOf()
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
        holder.bindItem(items[position], onImageViewClick)
    }


}

class FavoritesViewHolder(private val binding: RowFavoritesBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindItem(item: StationEntity, onImageViewClick: (StationEntity) -> Unit) {
        binding.apply {
            textViewStationName.text = item.name + " - " + item.distance
            textViewStationEus.text = item.capacity.toString()
            imageViewStar.setOnClickListener {
                onImageViewClick.invoke(item)

            }
        }
    }

}