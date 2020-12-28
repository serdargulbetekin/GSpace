package com.example.gspace.modules.station

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gspace.databinding.FragmentStationBinding


class StationFragment : Fragment() {

    private lateinit var viewModel: StationViewModel
    private val viewBinding by lazy { FragmentStationBinding.inflate(layoutInflater) }
    private val adapter by lazy {
        StationAdapter(
            onImageViewClick = {
                viewModel.onImageViewClick(it)
            },
            onTextViewTravelClick = {
                viewModel.onTextViewTravelClick(it)
            })
    }
    private val viewModelFactory by lazy { StationShipViewModelFactory() }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(StationViewModel::class.java)
        viewBinding.apply {
            recyclerView.layoutManager =
                LinearLayoutManager(this@StationFragment.context)
            recyclerView.adapter = adapter
        }
        viewModel.stationList.observe(viewLifecycleOwner, Observer {
            adapter.items = it
        })
    }
}