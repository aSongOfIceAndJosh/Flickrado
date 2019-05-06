package com.android.joshuamarotta.flickrado.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.joshuamarotta.flickrado.R
import com.android.joshuamarotta.flickrado.adapters.MainRecyclerViewAdapter
import com.android.joshuamarotta.flickrado.itemDecoration.GridLayoutItemDecoration
import com.android.joshuamarotta.flickrado.viewmodels.MainViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.main_fragment.view.*
import javax.inject.Inject

class MainFragment : DaggerFragment() {
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var mainViewModel: MainViewModel

    private var mainRecyclerViewAdapter: MainRecyclerViewAdapter? = null
    private lateinit var mainRecyclerView: RecyclerView

    private lateinit var onMainRecyclerViewItemClick: (Bundle) -> Unit

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView =  inflater.inflate(R.layout.main_fragment, container, false)

        onMainRecyclerViewItemClick = {
            val detailDialogFragment = DetailDialogFragment()
            detailDialogFragment.arguments = it
            detailDialogFragment.show(childFragmentManager, "")
        }

        mainRecyclerViewAdapter = context?.let { context -> MainRecyclerViewAdapter(context, onMainRecyclerViewItemClick) }
        mainRecyclerView = rootView.main_fragment_recycler_view

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initMainRecyclerView()

        mainViewModel = ViewModelProviders.of(this, viewModelFactory)[MainViewModel::class.java]
        mainViewModel.flickrResponse
            .observe(this, Observer { flickrResponse -> flickrResponse?.let { mainRecyclerViewAdapter?.setItems(it.items) } }
            )

    }

    private fun initMainRecyclerView() {
        mainRecyclerView.apply {
            adapter = mainRecyclerViewAdapter
            layoutManager = GridLayoutManager(context, context.resources.getInteger(R.integer.main_fragment_grid_layout_manager_col_span))
            addItemDecoration(GridLayoutItemDecoration(context.resources.getInteger(R.integer.main_fragment_grid_layout_manager_col_span)))
        }
    }
}


