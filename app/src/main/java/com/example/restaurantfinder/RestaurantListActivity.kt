package com.example.restaurantfinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.restaurantfinder.ui.RestaurantList.RestaurantListViewModel
import com.example.restaurantfinder.ui.RestaurantList.RestaurantListViewState
import kotlinx.android.synthetic.main.activity_main.*

class RestaurantListActivity : AppCompatActivity() {

    private lateinit var viewModelFactory: RestaurantListViewModel.Factory

    private val viewModel: RestaurantListViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModelFactory = Injection.provideRestaurantListViewModelFactory(this)

        viewModel.getLiveData().observe(this, Observer {
            renderState(it)
        })
        viewModel.loadData()
    }

    private fun renderState(viewState: RestaurantListViewState) {
        when {
            viewState.isLoading -> renderLoadingState()
            viewState.error != null -> renderErrorState()
            else -> renderData(viewState)
        }
    }

    private fun renderLoadingState() {
        loading_text.visibility = View.VISIBLE
        error_loading.visibility = View.GONE
    }

    private fun renderErrorState() {
        loading_text.visibility = View.GONE
        error_loading.visibility = View.VISIBLE
    }

    private fun renderData(viewState: RestaurantListViewState) {
        loading_text.visibility = View.GONE
        error_loading.visibility = View.GONE
    }
}