package com.calories.calorieslookout.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.calories.calorieslookout.database.CaloriesData
import com.calories.calorieslookout.databinding.FavoriteItemBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import androidx.core.content.ContextCompat.startActivity

import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.recreate
import androidx.core.content.ContextCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import com.calories.calorieslookout.FavoriteFragmentDirections
import androidx.core.content.ContextCompat.startActivity
import com.calories.calorieslookout.MainActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.ContextCompat.startActivity
import com.calories.calorieslookout.FavoriteFragment
import androidx.core.content.ContextCompat.startActivity
import android.os.Build
import androidx.fragment.app.FragmentTransaction


class FavoriteAdapter(val removeClickListener: (item:CaloriesData) -> Unit) : ListAdapter<CaloriesData, FavoriteAdapter.ResultsItemViewHolder>(DiffCallback) {

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

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ResultsItemViewHolder, position: Int) {
        val listMeal = getItem(position)

      holder.bind(listMeal)

//        holder.favoriteButton.setOnFocusChangeListener {buttonView , isFavorite ->
//
//            //CaloriesData[position].C = isChecked
//
//        }

        holder.favoriteButton.setOnClickListener{

               // holder.favoriteButton.visibility = View.VISIBLE

            holder.favoriteButton.visibility = View.VISIBLE
              //  holder.disLikeButton.visibility = View.GONE

            removeClickListener(listMeal)
            notifyItemRemoved(position)
            // recreate(FavoriteFragment)


           // getFragmentManager().beginTransaction().detach(this).attach(this).commit()


//            val ft: FragmentTransaction = getFragmentManager().beginTransaction()
//            if (Build.VERSION.SDK_INT >= 26) {
//                ft.setReorderingAllowed(false)
//            }
//            ft.detach(this).attach(this).commit()


          //  intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

//            val intent = Intent(this@FavoriteAdapter, FavoriteAdapter::class.java)
//            finish()
//            startActivity(getIntent())


        }
//        holder.itemOfMeal.setOnClickListener {
//            var action = BreakfastFragmentDirections.actionBreakfastFragment2ToBreakfastDescriptionFragment2(id = position)
//            holder.itemOfMeal.findNavController().navigate(action)
//        }
    }

}