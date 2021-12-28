package com.calories.calorieslookout.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.calories.calorieslookout.BreakfastFragmentDirections
import com.calories.calorieslookout.databinding.FavoriteItemBinding
import com.calories.calorieslookout.network.HitsItem

class FavoriteAdapter: ListAdapter<HitsItem, FavoriteAdapter.ResultsItemViewHolder>(GridAdapter){

    class ResultsItemViewHolder(private var binding: FavoriteItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(Item: HitsItem) {
            binding.item = Item
            binding.executePendingBindings()
        }

        var itemOfMeal = binding.FoodItem
    }

    companion object DiffCallback : DiffUtil.ItemCallback<HitsItem>() {
        override fun areItemsTheSame(oldItem: HitsItem, newItem: HitsItem): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: HitsItem, newItem: HitsItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultsItemViewHolder {
        return ResultsItemViewHolder(FavoriteItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ResultsItemViewHolder, position: Int) {
        val listMeal = getItem(position)

        holder.bind(listMeal)
        holder.itemOfMeal.setOnClickListener {
            var action = BreakfastFragmentDirections.actionBreakfastFragment2ToBreakfastDescriptionFragment2(id = position)
            holder.itemOfMeal.findNavController().navigate(action)

        }
    }
}