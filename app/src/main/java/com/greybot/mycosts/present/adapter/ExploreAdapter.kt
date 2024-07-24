//package com.greybot.mycosts.present.adapter
//
//import android.graphics.Color
//import android.view.View
//import android.view.ViewGroup
//import androidx.core.content.ContextCompat
//import androidx.recyclerview.widget.AsyncDifferConfig
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//import com.greybot.mycosts.R
//import com.greybot.mycosts.databinding.*
//import com.greybot.mycosts.models.AdapterItems
//import com.greybot.mycosts.utility.inflateAdapter
//import com.greybot.mycosts.utility.roundToString
//
//class ExploreAdapter(
//    val onClick: (AdapterCallback) -> Unit
//) : ListAdapter<AdapterItems, ExploreAdapter.Holder>(
//    AsyncDifferConfig.Builder(ExploreDiffCallback()).build()
//) {
//    var highlightGlobal = false
//
//    companion object {
//        const val EXPLORE_ITEM = 0
//        const val ADD_CONTENT_ITEM = 1
//        const val ROW_ITEM = 2
//        const val TOTAL_ITEM = 3
//        const val SPACE_ITEM = 4
//    }
//
//    private val list get() = currentList
//
//    fun updateAdapter(newList: List<AdapterItems>) {
//        submitList(newList)
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return when (list[position]) {
//            is AdapterItems.FolderItem -> EXPLORE_ITEM
//            is AdapterItems.ButtonAddItem -> ADD_CONTENT_ITEM
//            is AdapterItems.RowItem -> ROW_ITEM
//            is AdapterItems.TotalItem -> TOTAL_ITEM
//            is AdapterItems.SpaceItem -> SPACE_ITEM
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
//        return when (viewType) {
//            EXPLORE_ITEM -> FolderHolder(parent.inflateAdapter(ExploreAdapterItemBinding::inflate))
//            ADD_CONTENT_ITEM -> ButtonAddHolder(parent.inflateAdapter(ButtonAddAdapterItemBinding::inflate))
//            ROW_ITEM -> RowHolder(parent.inflateAdapter(RowAdapterItemBinding::inflate))
//            TOTAL_ITEM -> TotalHolder(parent.inflateAdapter(TotalAdapterItemBinding::inflate))
//            SPACE_ITEM -> SpaceHolder(parent.inflateAdapter(SpaceAdapterItemBinding::inflate))
//            else -> throw Throwable()
//        }
//    }
//
//    override fun onBindViewHolder(holder: Holder, position: Int) {
//        holder.onBind(list[position])
//    }
//
//    override fun getItemCount(): Int {
//        return list.size
//    }
//
//    abstract class Holder(view: View) : RecyclerView.ViewHolder(view) {
//        abstract fun onBind(item: AdapterItems)
//    }
//
//    inner class FolderHolder(private val binding: ExploreAdapterItemBinding) :
//        Holder(binding.root) {
//        private val markItem by lazy { MarkItem(itemView) }
//        override fun onBind(item: AdapterItems) {
//            item as AdapterItems.FolderItem
//            binding.exploreItemTitle.text = item.name
//            binding.exploreItemCount.text = item.countInner
//            binding.exploreItemTotal.text = item.total
//            itemView.setOnClickListener {
//                if (markItem.highlight) {
//                    markItem.mark()
//                } else onClick.invoke(AdapterCallback.FolderOpen(item))
//            }
//            itemView.setOnLongClickListener {
//                onClick.invoke(AdapterCallback.FolderHighlight(item))
//                markItem.mark()
//                true
//            }
//        }
//    }
//
//    inner class ButtonAddHolder(private val binding: ButtonAddAdapterItemBinding) :
//        Holder(binding.root) {
//        override fun onBind(item: AdapterItems) {
//            item as AdapterItems.ButtonAddItem
//            binding.buttonAddItemName.text = item.type.row
//            itemView.setOnClickListener {
//                onClick.invoke(AdapterCallback.AddButton(item))
//            }
//        }
//    }
//
//    inner class RowHolder(private val binding: RowAdapterItemBinding) :
//        Holder(binding.root) {
//
//        private val markItem by lazy { MarkItem(itemView) }
//
//        override fun onBind(item: AdapterItems) {
//            item as AdapterItems.RowItem
//            binding.rowItemCheckDone.isChecked = item.isBought
//            binding.rowItemName.text = item.name
//            binding.rowItemCount.text = item.count.roundToString()
//            binding.rowItemPrice.text = item.price.toInt().toString()
//            binding.rowItemName.setOnClickListener {
//                if (markItem.highlight || highlightGlobal) {
//                    onClick.invoke(AdapterCallback.FileHighlight(item))
//                    markItem.mark()
//                } else onClick.invoke(AdapterCallback.RowName(item))
//            }
//            binding.rowItemPrice.setOnClickListener {
//                if (!highlightGlobal)
//                    onClick.invoke(AdapterCallback.RowPrice(item))
//            }
//            binding.rowItemCount.setOnClickListener {
//                if (!highlightGlobal)
//                    onClick.invoke(AdapterCallback.RowCount(item))
//            }
//            binding.rowItemCheckDone.setOnClickListener {
//                onClick.invoke(AdapterCallback.RowBuy(item))
//            }
//
//            binding.rowItemName.setOnLongClickListener {
//                onClick.invoke(AdapterCallback.FileHighlight(item))
//                markItem.mark()
//                true
//            }
//        }
//    }
//
//    inner class MarkItem(private val itemView: View) {
//        var highlight = false
//
//        private val colorHighlight = ContextCompat.getColor(
//            itemView.context,
//            R.color.divider_color
//        )
//
//        fun mark() {
//            highlight = !highlight
//            if (highlight)
//                itemView.setBackgroundColor(colorHighlight)
//            else
//                itemView.setBackgroundColor(Color.WHITE)
//        }
//    }
//
//    inner class TotalHolder(private val binding: TotalAdapterItemBinding) :
//        Holder(binding.root) {
//        override fun onBind(item: AdapterItems) {
//            item as AdapterItems.TotalItem
//            binding.totalItemTitle1.text = item.name1
//            binding.totalItemValue1.text = item.value1
//            binding.totalItemTitle2.text = item.name2
//            binding.totalItemValue2.text = item.value2
//            itemView.setOnClickListener {
//                onClick.invoke(AdapterCallback.Total(item))
//            }
//        }
//    }
//
//    inner class SpaceHolder(private val binding: SpaceAdapterItemBinding) : Holder(binding.root) {
//        override fun onBind(item: AdapterItems) {
//            item as AdapterItems.SpaceItem
//            binding.spaceView.post {
//                binding.spaceView.layoutParams.height =
//                    itemView.resources.getDimension(item.heightRes).toInt()
//                binding.spaceView.requestLayout()
//            }
//        }
//    }
//
//}