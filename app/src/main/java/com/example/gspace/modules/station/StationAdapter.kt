package com.example.gspace.modules.station

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gspace.R
import com.example.gspace.databinding.RowStationBinding
import com.example.gspace.modules.station.room.StationEntity

class StationAdapter(
    private val onImageViewClick: (StationEntity) -> Unit,
    private val onTextViewTravelClick: (StationEntity) -> Unit
) :
    RecyclerView.Adapter<StationViewHolder>() {

    var items: List<StationAdapterItem> = mutableListOf()
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
        holder.bindItem(items[position], onImageViewClick, onTextViewTravelClick)
    }

}

class StationViewHolder(private val binding: RowStationBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindItem(
        stationAdapterItem: StationAdapterItem,
        onImageViewClick: (StationEntity) -> Unit,
        onTextViewTravelClick: (StationEntity) -> Unit
    ) {
        binding.textViewName.text = stationAdapterItem.stationEntity.name
        binding.textViewPoint.text = stationAdapterItem.stationEntity.capacity.toString()
        binding.imageViewFav.setOnClickListener {
            onImageViewClick.invoke(stationAdapterItem.stationEntity)
        }

        binding.textViewTravel.setOnClickListener {
            onTextViewTravelClick.invoke(stationAdapterItem.stationEntity)
        }

        if (stationAdapterItem.hasStation) {
            binding.imageViewFav.setImageResource(R.drawable.icon_fav)
        } else {
            binding.imageViewFav.setImageResource(R.drawable.icon_fav_empty)

        }
    }
}

data class StationAdapterItem(
    val stationEntity: StationEntity,
    val hasStation: Boolean
)