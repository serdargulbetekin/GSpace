package com.example.gspace.modules.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gspace.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment() {

    private val viewBinding by lazy { FragmentFavoritesBinding.inflate(layoutInflater) }
    private val adapter by lazy { FavoritesAdapter() }

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
            recyclerView.layoutManager = LinearLayoutManager(this@FavoritesFragment.context)
            recyclerView.adapter = adapter
        }
    }
}