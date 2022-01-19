package com.calories.calorieslookout.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.calories.calorieslookout.BreakfastFragmentDirections
import com.calories.calorieslookout.databinding.GridItemBinding
import com.calories.calorieslookout.network.HitsItem


class GridAdapter :
    androidx.recyclerview.widget.ListAdapter<HitsItem, GridAdapter.ResultsItemViewHolder>(DiffCallback) {

    class ResultsItemViewHolder(private var binding: GridItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(Item : HitsItem ) {
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
        return ResultsItemViewHolder(GridItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }


    override fun onBindViewHolder(holder: ResultsItemViewHolder, position: Int) {
        val listMeal = getItem(position)

        holder.bind(listMeal)
        holder.itemOfMeal.setOnClickListener {
            var action = BreakfastFragmentDirections.actionBreakfastFragment2ToBreakfastDescriptionFragment2(id = position, favorite = 0)
            holder.itemOfMeal.findNavController().navigate(action)

        }
    }

}