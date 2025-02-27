package com.example.gspace.modules.station

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gspace.databinding.FragmentStationBinding
import com.example.gspace.modules.createspaceship.factory.Factory


class StationFragment : Fragment() {

    private lateinit var viewModel: StationViewModel
    private val viewBinding by lazy { FragmentStationBinding.inflate(layoutInflater) }
    private val adapter by lazy {
        StationAdapter(
            onImageViewClick = {
                viewModel.onImageViewClick(it)
            },
            onTextViewTravelClick = {
                viewModel.onTextViewTravelClick(it) {
                    Toast.makeText(
                        this.context,
                        "You are already in this station.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
    private val factory by lazy { Factory() }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, factory).get(StationViewModel::class.java)
        viewBinding.apply {
            recyclerView.layoutManager =
                LinearLayoutManager(this@StationFragment.context)
            recyclerView.adapter = adapter

            editTextSearch.addTextChangedListener {
                adapter.filter.filter(it.toString())
            }
        }
        viewModel.stationList.observe(viewLifecycleOwner, Observer {
            adapter.updateList(it)
        })

        viewModel.spaceShip.observe(viewLifecycleOwner, Observer {
            viewBinding.textViewStationName.text = it?.name ?: ""
        })

        viewModel.currentStation.observe(viewLifecycleOwner, Observer {
            viewBinding.textViewBottom.text = it?.name
        })
    }
}