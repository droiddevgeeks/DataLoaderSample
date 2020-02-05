package com.example.dataloadersample.ui.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.dataloadersample.R
import com.example.dataloadersample.base.core.BaseActivity
import com.example.dataloadersample.ui.viewmodel.MainViewModel
import com.example.dataloadersample.util.Constants
import javax.inject.Inject
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dataloadersample.api.PinBoard
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity<MainViewModel>() {

    private lateinit var pinAdapter: PinBoardAdapter
    private val pinItems: MutableList<PinBoard> by lazy { mutableListOf<PinBoard>() }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val viewModel: MainViewModel
        get() = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)

    override fun getLayoutRes() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchJsonData(Constants.DATA_URL)
        initImageAdapter()
        observeDataChange()
    }

    private fun initImageAdapter() {
        pinAdapter = PinBoardAdapter(this, pinItems)
        val pinLayoutManager = GridLayoutManager(this, 3)
        with(pinRecycler) {
            layoutManager = pinLayoutManager
            adapter = pinAdapter
        }
    }

    private fun observeDataChange() {
        viewModel.pinBoardLiveData.observe(this, Observer { setImageList(it) })
        viewModel.loadingState.observe(this, Observer { loadingStatus(it) })
    }

    /**
     * added data to same list, just for testing purpose
     */
    private fun setImageList(pinBoardList: List<PinBoard>) {
        pinItems.addAll(pinBoardList)
        pinItems.addAll(pinBoardList)
        pinItems.addAll(pinBoardList)
        pinItems.addAll(pinBoardList)
        pinItems.addAll(pinBoardList)
        pinItems.addAll(pinBoardList)
        pinItems.addAll(pinBoardList)
        pinItems.addAll(pinBoardList)
        pinAdapter.notifyDataSetChanged()
    }

    private fun loadingStatus(loading: Boolean) {
        progress.visibility = if (loading) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}
