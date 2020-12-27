package com.example.gspace.modules.station

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gspace.databinding.RowStationBinding
import com.example.gspace.modules.main.GSpaceStation

class StationAdapter : RecyclerView.Adapter<StationViewHolder>() {

    var items: List<GSpaceStation> = mutableListOf()
        set(value) {
            (field as? MutableList)?.apply {
                clear()
                addAll(value)
            }
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
        return StationViewHolder(
            RowStationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

}

class StationViewHolder(private val binding: RowStationBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindItem(station: GSpaceStation) {

    }
}