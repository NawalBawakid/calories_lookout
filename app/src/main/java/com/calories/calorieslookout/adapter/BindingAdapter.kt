package com.calories.calorieslookout.adapter

import android.util.Log
import android.util.Log.i
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.startup.StartupLogger.i
import com.bumptech.glide.Glide
import com.calories.calorieslookout.R
import com.calories.calorieslookout.network.HitsItem
import com.calories.calorieslookout.viewModel.CaloriesApiStatus
import java.util.logging.Logger


@BindingAdapter("textset")
fun bindText(textView: TextView, name : String?) {
    textView.setText(name)
}


@BindingAdapter("imageUrl")
fun bindImage(ImageView: ImageView, imageUrl: String?) {
    imageUrl?.let {
        val photoUri = imageUrl.toUri().buildUpon().build()
        Glide.with(ImageView)
            .load("${photoUri}")
            .into(ImageView) }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<HitsItem?>?) {
    Log.e("infooo","$data")
    val adapter = recyclerView.adapter as GridAdapter
    adapter.submitList(data)
}

@BindingAdapter("favoriteData")
fun bindFavoriteRecyclerView(recyclerView: RecyclerView, data: List<HitsItem?>?) {
    Log.e("infooo","$data")
    val adapter = recyclerView.adapter as FavoriteAdapter
    adapter.submitList(data)
}


@BindingAdapter("calorieApiStatus")
fun bindStatus(statusImageView: ImageView, status: CaloriesApiStatus?) {
    when(status){
        CaloriesApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        CaloriesApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        CaloriesApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}