package com.calories.calorieslookout.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.calories.calorieslookout.BreakfastFragmentDirections
import com.calories.calorieslookout.R
import com.calories.calorieslookout.database.CaloriesData
import com.calories.calorieslookout.databinding.FavoriteItemBinding
import com.calories.calorieslookout.network.HitsItem
import com.calories.calorieslookout.viewModel.OverviewViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class FavoriteAdapter: ListAdapter<CaloriesData, FavoriteAdapter.ResultsItemViewHolder>(DiffCallback) {
    private var isFavorite = true
    private val CaloriesDataCollection = Firebase.firestore.collection("CaloriesData")

//    private val _likeItem = MutableLiveData<List<CaloriesData?>?>()
//    var likeItem: LiveData<List<CaloriesData?>?> = _likeItem

    class ResultsItemViewHolder(private var binding: FavoriteItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(Item: CaloriesData) {
            binding.item = Item
            binding.executePendingBindings()
        }

        var favoriteButton: ImageView = binding.like
        var disLikeButton: ImageView = binding.disLike
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

    override fun onBindViewHolder(holder: ResultsItemViewHolder, position: Int) {
        val listMeal = getItem(position)

      holder.bind(listMeal)

//        holder.favoriteButton.setOnFocusChangeListener {buttonView , isFavorite ->
//
//            //CaloriesData[position].C = isChecked
//
//        }

        holder.favoriteButton.setOnClickListener{
            if(isFavorite){
                holder.favoriteButton.visibility = View.VISIBLE
                isFavorite = false
                holder.favoriteButton.visibility = View.VISIBLE
                holder.disLikeButton.visibility = View.GONE

               // val oldData = retriveData()
                //removeData()
            }else{
                isFavorite = true
                holder.disLikeButton.visibility = View.VISIBLE
                holder.favoriteButton.visibility = View.GONE
            }
        }
//        holder.itemOfMeal.setOnClickListener {
//            var action = BreakfastFragmentDirections.actionBreakfastFragment2ToBreakfastDescriptionFragment2(id = position)
//            holder.itemOfMeal.findNavController().navigate(action)
//        }
    }

    fun removeData(itemFavorate : CaloriesData){
        CaloriesDataCollection.whereEqualTo("image", itemFavorate.image)
            .whereEqualTo("label", itemFavorate.label)
            .whereEqualTo("calories", itemFavorate.calories)
            .get()
            .addOnCompleteListener{ task ->
                if (task.isSuccessful){
                    if (task.result!!.documents.isNotEmpty()){
                        for (data in task.result!!.documents){
                            CaloriesDataCollection.document(data.id).delete()
                        }
                    }else{

                    }
                }

            }
    }

//    fun retriveData(){
//        CaloriesDataCollection.get().addOnCompleteListener{task ->
//            if (task.isSuccessful) {
//                val item = mutableListOf<CaloriesData>()
//                for (data in task.result!!.documents) {
//                    val calories = data.toObject<CaloriesData>()
//                    item.add(calories!!)
//                }
//                Log.d("TAG", "retriveData: $item")
//                _likeItem.value=item
////                binding.favoriteItem = item
//            }else{
//
//            }
//        }.addOnFailureListener {
//            println(it.message)
//        }
//    }



//    fun getOldData(): CaloriesData{
//
//        return
//    }
}