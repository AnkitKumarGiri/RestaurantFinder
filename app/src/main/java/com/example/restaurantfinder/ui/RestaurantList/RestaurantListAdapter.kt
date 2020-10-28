package com.example.restaurantfinder.ui.RestaurantList

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restaurantfinder.R
import com.example.restaurantfinder.persistence.Restaurant
import org.w3c.dom.Text

// TODO Milestone2 (03) Add RestaurantListAdapter
class RestaurantListAdapter(
    private var restaurantList: List<Restaurant> = emptyList(),
    val restaurantListClickListener: RestaurantListClickListener
) :
    RecyclerView.Adapter<RestaurantListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RestaurantListAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.restaurant_item_row, parent, false)

        return ViewHolder(v)
    }

    fun setData(data: List<Restaurant>) {
        restaurantList = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }

    override fun onBindViewHolder(holder: RestaurantListAdapter.ViewHolder, position: Int) {
        holder.bindView(restaurantList[position], restaurantListClickListener)
    }

    // TODO Milestone2 (04) Add RestaurantListViewModelHolder
    open class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val name: TextView
        private val cuisine: TextView
        private val image: ImageView
        private val parentLayout: View

        fun bindView(
            restaurant: Restaurant,
            listClickListener: RestaurantListClickListener
        ) {
            name.text = restaurant.name
            cuisine.text = restaurant.cuisines

            // TODO Milestone2 (02) Load image using glide
            Glide.with(image.context)
                .load(restaurant.featuredImage)
                .optionalCenterCrop()
                .into(image)

            // TODO Milestone4 (02) Launch RestaurantDetailActivity when a restaurant is selected from the list
            parentLayout.setOnClickListener { listClickListener.onRestaurantClicked(restaurantId = restaurant.id) }
            // TODO Milestone4 (03) Pass restaurant id to RestaurantDetailActivity
        }

        init {
            v.setOnClickListener { Log.d(TAG, "Element $adapterPosition clicked.") }
            name = v.findViewById(R.id.restaurant_name)
            cuisine = v.findViewById(R.id.restaurant_cuisine)
            image = v.findViewById(R.id.restaurant_cover)
            parentLayout = v.findViewById(R.id.parent_layout)
        }
    }

    companion object {
        private val TAG = RestaurantListAdapter::class.java.simpleName
    }

    interface RestaurantListClickListener {
        fun onRestaurantClicked(restaurantId: Int)
    }
}