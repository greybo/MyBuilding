package com.greybot.mycosts.present.explore

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.greybot.mycosts.databinding.AddContentAdapterItemBinding
import com.greybot.mycosts.databinding.ExploreAdapterItemBinding
import com.greybot.mycosts.models.AdapterItems
import com.greybot.mycosts.utility.inflateAdapter

class ExploreAdapter(val callback: (AdapterItems) -> Unit) :
    RecyclerView.Adapter<ExploreAdapter.Holder>() {

    companion object {
        const val EXPLORE_ITEM = 0
        const val ADD_CONTENT_ITEM = 1
    }

    private val list = mutableListOf<AdapterItems>()

    fun updateAdapter(newList: List<AdapterItems>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            is AdapterItems.ExploreItem -> EXPLORE_ITEM
            is AdapterItems.AddContentItem -> ADD_CONTENT_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return when (viewType) {
            EXPLORE_ITEM -> ExploreHolder(parent.inflateAdapter(ExploreAdapterItemBinding::inflate))
            ADD_CONTENT_ITEM -> AddContentHolder(parent.inflateAdapter(AddContentAdapterItemBinding::inflate))
            else -> throw Throwable()
        }
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    abstract class Holder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun onBind(item: AdapterItems)
    }

    inner class ExploreHolder(private val binding: ExploreAdapterItemBinding) :
        Holder(binding.root) {

        override fun onBind(item: AdapterItems) {
            item as AdapterItems.ExploreItem
            binding.exploreItemTitle.text = item.name
            itemView.setOnClickListener {
                callback.invoke(item)
            }
        }
    }

    inner class AddContentHolder(private val binding: AddContentAdapterItemBinding) :
        Holder(binding.root) {
        override fun onBind(item: AdapterItems) {
            item as AdapterItems.AddContentItem
            binding.exploreItemTitle.text = item.name
            itemView.setOnClickListener {
                callback.invoke(item)
            }
        }
    }

}