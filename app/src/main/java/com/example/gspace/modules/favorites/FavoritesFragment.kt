package com.example.gspace.modules.favorites

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.gspace.databinding.FragmentFavoritesBinding
import com.example.gspace.modules.createspaceship.factory.Factory

class FavoritesFragment : Fragment() {

    private val viewBinding by lazy { FragmentFavoritesBinding.inflate(layoutInflater) }
    private val adapter by lazy {
        FavoritesAdapter {
            viewModel.deleteStationEntity(it)
        }
    }
    private val factory by lazy { Factory() }
    private lateinit var viewModel: FavoritesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return viewBinding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, factory).get(FavoritesViewModel::class.java)
        viewBinding.apply {
            recyclerView.layoutManager = StaggeredGridLayoutManager(1, RecyclerView.VERTICAL)
            recyclerView.adapter = adapter
        }

        viewModel.favLiveData.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                viewBinding.linearLayoutEmptyContainer.visibility = View.VISIBLE
                viewBinding.recyclerView.visibility = View.GONE
                viewBinding.textViewMessage.text = "Favorite list is empty. You can add it in Station section via clicking star icon."
            } else {
                viewBinding.linearLayoutEmptyContainer.visibility = View.GONE
                viewBinding.recyclerView.visibility = View.VISIBLE
                adapter.items = it

            }
        })


    }
}