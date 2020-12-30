package com.example.gspace.modules.station

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.gspace.R
import com.example.gspace.databinding.RowStationBinding
import com.example.gspace.modules.station.room.StationEntity

class StationAdapter(
    private val onImageViewClick: (StationEntity) -> Unit,
    private val onTextViewTravelClick: (StationEntity) -> Unit
) :
    RecyclerView.Adapter<StationViewHolder>(), Filterable {


    private val allItems = mutableListOf<StationAdapterItem>()
    private val items = mutableListOf<StationAdapterItem>()


    fun updateList(cryptoCoin: List<StationAdapterItem>) {
        items.clear()
        items.addAll(cryptoCoin)
        allItems.clear()
        allItems.addAll(cryptoCoin)
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

    override fun getFilter(): Filter {
        return filter
    }

    private val filter = object : Filter() {
        override fun performFiltering(charSequence: CharSequence?): FilterResults {
            val filteredList = mutableListOf<StationAdapterItem>()
            if (charSequence.toString().isEmpty()) {
                filteredList.addAll(allItems)
            } else {
                allItems.forEach {
                    if (it.stationEntity.name.toLowerCase().contains(
                            charSequence.toString().toLowerCase()
                        )
                    ) {
                        filteredList.add(it)
                    }
                }

            }
            val filterResults = FilterResults()
            filterResults.values = filteredList
            return filterResults

        }

        override fun publishResults(p0: CharSequence?, filterResults: FilterResults?) {
            items.clear()
            items.addAll(filterResults?.values as List<StationAdapterItem>)
            notifyDataSetChanged()
        }

    }

}

class StationViewHolder(private val binding: RowStationBinding) :
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bindItem(
        stationAdapterItem: StationAdapterItem,
        onImageViewClick: (StationEntity) -> Unit,
        onTextViewTravelClick: (StationEntity) -> Unit
    ) {
        binding.textViewName.text =
            stationAdapterItem.stationEntity.name + " - " + stationAdapterItem.stationEntity.distance
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

        if (stationAdapterItem.isCurrent) {
            binding.root.setBackgroundResource(R.drawable.selector_price_to_distribute_green_border)
        } else {
            binding.root.setBackgroundResource(R.drawable.selector_price_to_distribute_black_border)
        }
    }
}

data class StationAdapterItem(
    val stationEntity: StationEntity,
    val hasStation: Boolean,
    val isCurrent: Boolean = false
)