package com.calories.calorieslookout.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.calories.calorieslookout.FavoriteFragmentDirections
import com.calories.calorieslookout.database.CaloriesData
import com.calories.calorieslookout.databinding.FavoriteItemBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase



class FavoriteAdapter(val removeClickListener: (item:CaloriesData) -> Unit) : ListAdapter<CaloriesData, FavoriteAdapter.ResultsItemViewHolder>(DiffCallback) {

    class ResultsItemViewHolder(private var binding: FavoriteItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(Item: CaloriesData) {
            binding.item = Item
            binding.executePendingBindings()
        }

        var favoriteButton: ImageView = binding.like
        var image: ImageView = binding.posterImage
        var label: TextView = binding.title
        var calories: TextView = binding.caloriesnum
        var itemOfMeal = binding.favoriteItem
    }

    companion object DiffCallback : DiffUtil.ItemCallback<CaloriesData>() {
        override fun areItemsTheSame(oldItem: CaloriesData, newItem: CaloriesData): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: CaloriesData, newItem: CaloriesData): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultsItemViewHolder {
        return ResultsItemViewHolder(FavoriteItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ResultsItemViewHolder, position: Int) {
        val listMeal = getItem(position)

      holder.bind(listMeal)

        holder.favoriteButton.setOnClickListener{

            holder.favoriteButton.visibility = View.VISIBLE

            removeClickListener(listMeal)
            notifyItemRemoved(position)
        }

        holder.itemOfMeal.setOnClickListener{
            var action = FavoriteFragmentDirections.actionFavoriteFragmentToBreakfastDescriptionFragment2(id = position, favorite = 1)
            holder.itemOfMeal.findNavController().navigate(action)
        }
    }

}