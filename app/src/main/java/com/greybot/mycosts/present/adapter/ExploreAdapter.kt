package com.greybot.mycosts.present.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.greybot.mycosts.databinding.*
import com.greybot.mycosts.models.AdapterItems
import com.greybot.mycosts.utility.inflateAdapter

class ExploreAdapter(
    val onClick: (AdapterCallback) -> Unit
) : ListAdapter<AdapterItems, ExploreAdapter.Holder>(
    AsyncDifferConfig.Builder(DiffCallback()).build()
) {
//    ListAdapter<AdapterItems,ExploreAdapter.Holder>(DiffCallback()){
//    RecyclerView.Adapter<ExploreAdapter.Holder>() {

    companion object {
        const val EXPLORE_ITEM = 0
        const val ADD_CONTENT_ITEM = 1
        const val ROW_ITEM = 2
        const val TOTAL_ITEM = 3
        const val SPACE_ITEM = 4
    }

    private val list get() = currentList

    fun updateAdapter(newList: List<AdapterItems>) {
        submitList(newList)
    }

    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            is AdapterItems.FolderItem -> EXPLORE_ITEM
            is AdapterItems.ButtonAddItem -> ADD_CONTENT_ITEM
            is AdapterItems.RowItem -> ROW_ITEM
            is AdapterItems.TotalItem -> TOTAL_ITEM
            is AdapterItems.SpaceItem -> SPACE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return when (viewType) {
            EXPLORE_ITEM -> FolderHolder(parent.inflateAdapter(ExploreAdapterItemBinding::inflate))
            ADD_CONTENT_ITEM -> ButtonAddHolder(parent.inflateAdapter(ButtonAddAdapterItemBinding::inflate))
            ROW_ITEM -> RowHolder(parent.inflateAdapter(RowAdapterItemBinding::inflate))
            TOTAL_ITEM -> TotalHolder(parent.inflateAdapter(TotalAdapterItemBinding::inflate))
            SPACE_ITEM -> SpaceHolder(parent.inflateAdapter(SpaceAdapterItemBinding::inflate))
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

    inner class FolderHolder(private val binding: ExploreAdapterItemBinding) :
        Holder(binding.root) {

        override fun onBind(item: AdapterItems) {
            item as AdapterItems.FolderItem
            binding.exploreItemTitle.text = item.name
            binding.exploreItemCount.text = item.countInner
            binding.exploreItemTotal.text = item.total
            itemView.setOnClickListener {
                onClick.invoke(AdapterCallback.FolderOpen(item))
            }
            itemView.setOnLongClickListener {
                onClick.invoke(AdapterCallback.FolderLong(item))
                false
            }
        }
    }

    inner class ButtonAddHolder(private val binding: ButtonAddAdapterItemBinding) :
        Holder(binding.root) {
        override fun onBind(item: AdapterItems) {
            item as AdapterItems.ButtonAddItem
            binding.buttonAddItemName.text = item.type.row
            itemView.setOnClickListener {
                onClick.invoke(AdapterCallback.AddButton(item))
            }
        }
    }

    inner class RowHolder(private val binding: RowAdapterItemBinding) :
        Holder(binding.root) {
        override fun onBind(item: AdapterItems) {
            item as AdapterItems.RowItem
            binding.rowItemCheckDone.isChecked = item.isBought
            binding.rowItemTitle.text = item.name
            binding.rowItemBody.text = item.price.toString()

            binding.rowItemTitle.setOnClickListener {
                onClick.invoke(AdapterCallback.Name(item))
            }
            binding.rowItemBody.setOnClickListener {
                onClick.invoke(AdapterCallback.Price(item))
            }
            binding.rowItemCheckDone.setOnClickListener {
                onClick.invoke(AdapterCallback.Buy(item))//item.changeBuy()
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
                onClick.invoke(AdapterCallback.Total(item))
            }
        }
    }

    inner class SpaceHolder(private val binding: SpaceAdapterItemBinding) : Holder(binding.root) {
        override fun onBind(item: AdapterItems) {
            item as AdapterItems.SpaceItem
            binding.spaceView.post {
                binding.spaceView.layoutParams.height =
                    itemView.resources.getDimension(item.heightRes).toInt()
                binding.spaceView.requestLayout()
            }
        }
    }

}