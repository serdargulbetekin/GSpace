package com.example.gspace.modules.station

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gspace.databinding.FragmentStationBinding

class StationFragment : Fragment() {

    private val viewBinding by lazy { FragmentStationBinding.inflate(layoutInflater) }
    private val adapter by lazy { StationAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.apply {
            recyclerView.layoutManager =
                LinearLayoutManager(this@StationFragment.context, RecyclerView.HORIZONTAL, false)
            recyclerView.adapter = adapter
        }
    }
}