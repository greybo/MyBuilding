package com.greybot.mybuilding.ui.explore

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.greybot.mybuilding.databinding.ExploreAdapterItemBinding
import com.greybot.mybuilding.utility.inflateAdapter

class ExploreAdapter(val callback: (String) -> Unit) :
    RecyclerView.Adapter<ExploreAdapter.Holder>() {

    private val list = mutableListOf<String>()

    fun updateAdapter(newList: List<String>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return ExploreHolder(parent.inflateAdapter(ExploreAdapterItemBinding::inflate))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    abstract class Holder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun onBind(item: String)
    }

    inner class ExploreHolder(private val binding: ExploreAdapterItemBinding) :
        Holder(binding.root) {

        override fun onBind(item: String) {
            binding.exploreItemTitle.text = item//
            itemView.setOnClickListener {
                callback.invoke(item)
            }
        }
    }

}