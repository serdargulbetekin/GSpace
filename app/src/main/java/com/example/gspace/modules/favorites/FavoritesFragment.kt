package com.example.gspace.modules.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.gspace.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment() {

    private val viewBinding by lazy { FragmentFavoritesBinding.inflate(layoutInflater) }
    private val adapter by lazy {
        FavoritesAdapter {
            viewModel.deleteStationEntity(it)
        }
    }
    private val viewModelFactory by lazy { FavoritesViewModelFactory() }
    private lateinit var viewModel: FavoritesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(FavoritesViewModel::class.java)
        viewBinding.apply {
            recyclerView.layoutManager = StaggeredGridLayoutManager(1,RecyclerView.VERTICAL)
            recyclerView.adapter = adapter
        }

        viewModel.favLiveData.observe(viewLifecycleOwner, Observer {
            adapter.items = it
        })


    }
}