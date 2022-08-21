package com.greybot.mycosts.present.explore

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.greybot.mycosts.databinding.ButtonAddAdapterItemBinding
import com.greybot.mycosts.databinding.ExploreAdapterItemBinding
import com.greybot.mycosts.databinding.RowAdapterItemBinding
import com.greybot.mycosts.databinding.TotalAdapterItemBinding
import com.greybot.mycosts.models.AdapterItems
import com.greybot.mycosts.utility.inflateAdapter

class ExploreAdapter(val callback: (AdapterItems) -> Unit) :
    RecyclerView.Adapter<ExploreAdapter.Holder>() {

    companion object {
        const val EXPLORE_ITEM = 0
        const val ADD_CONTENT_ITEM = 1
        const val ROW_ITEM = 2
        const val TOTAL_ITEM = 3
    }

    private val list = mutableListOf<AdapterItems>()

    fun updateAdapter(newList: List<AdapterItems>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            is AdapterItems.FolderItem -> EXPLORE_ITEM
            is AdapterItems.ButtonAddItem -> ADD_CONTENT_ITEM
            is AdapterItems.RowItem -> ROW_ITEM
            is AdapterItems.TotalItem -> TOTAL_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return when (viewType) {
            EXPLORE_ITEM -> ExploreHolder(parent.inflateAdapter(ExploreAdapterItemBinding::inflate))
            ADD_CONTENT_ITEM -> ButtonAddHolder(parent.inflateAdapter(ButtonAddAdapterItemBinding::inflate))
            ROW_ITEM -> RowHolder(parent.inflateAdapter(RowAdapterItemBinding::inflate))
            TOTAL_ITEM -> TotalHolder(parent.inflateAdapter(TotalAdapterItemBinding::inflate))
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
            item as AdapterItems.FolderItem
            binding.exploreItemTitle.text = item.name
            binding.exploreItemBody.text = item.description
            itemView.setOnClickListener {
                callback.invoke(item)
            }
        }
    }

    inner class ButtonAddHolder(private val binding: ButtonAddAdapterItemBinding) :
        Holder(binding.root) {
        override fun onBind(item: AdapterItems) {
            item as AdapterItems.ButtonAddItem
            binding.buttonAddItemName.text = item.type.row
            itemView.setOnClickListener {
                callback.invoke(item)
            }
        }
    }

    inner class RowHolder(private val binding: RowAdapterItemBinding) :
        Holder(binding.root) {
        override fun onBind(item: AdapterItems) {
            item as AdapterItems.RowItem
            binding.rowItemTitle.text = item.name
            binding.rowItemBody.text = item.price.toString()
            itemView.setOnClickListener {
                callback.invoke(item)
            }
        }
    }

    inner class TotalHolder(private val binding: TotalAdapterItemBinding) :
        Holder(binding.root) {
        override fun onBind(item: AdapterItems) {
            item as AdapterItems.TotalItem
            binding.totalItemTitle.text = item.name
            binding.totalItemValue.text = item.value.toString()
            itemView.setOnClickListener {
                callback.invoke(item)
            }
        }
    }

}