package com.nemanjamiseljic.dogoapp.breeds

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.nemanjamiseljic.dogoapp.R
import com.nemanjamiseljic.dogoapp.databinding.BreedsCardBinding
import com.nemanjamiseljic.dogoapp.models.breeds.BreedsItem
import com.nemanjamiseljic.dogoapp.models.selectedbreeds.SelectedBreedItem
import javax.inject.Inject

class BreedsAdapter @Inject constructor(
   private val glide: RequestManager
) : RecyclerView.Adapter<BreedsAdapter.BreedsViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<SelectedBreedItem>() {
        override fun areItemsTheSame(oldItem: SelectedBreedItem, newItem: SelectedBreedItem) = oldItem == newItem

        override fun areContentsTheSame(oldItem: SelectedBreedItem, newItem: SelectedBreedItem) = oldItem == newItem
    }
    private val differ = AsyncListDiffer(this, differCallback)

    var breedsList: List<SelectedBreedItem>
        get() = differ.currentList
        set(value) = differ.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedsViewHolder {
        val binding: BreedsCardBinding = BreedsCardBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        )
        return BreedsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BreedsViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount() = differ.currentList.size

    inner class BreedsViewHolder(private val binding: BreedsCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(breed: SelectedBreedItem) {

            Log.d("Adapter", "bind: loading new data")

            binding.root.setOnClickListener {
                Toast.makeText(binding.root.context,""+ breed.breeds, Toast.LENGTH_SHORT).show()
            }
            glide.load(breed.url).into(binding.bcImage)

        }
    }
}