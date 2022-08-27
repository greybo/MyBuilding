package com.greybot.mycosts.present.explore

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.greybot.mycosts.base.BaseBindingFragment
import com.greybot.mycosts.databinding.ExploreFragmentBinding
import com.greybot.mycosts.models.AdapterItems
import com.greybot.mycosts.present.adapter.ExploreAdapter
import com.greybot.mycosts.utility.animateFabHide
import com.greybot.mycosts.utility.animateShowFab
import com.greybot.mycosts.utility.getRouter


class ExploreFragment :
    BaseBindingFragment<ExploreFragmentBinding>(ExploreFragmentBinding::inflate) {

    private val viewModel by viewModels<ExploreViewModel>()
    private var adapter: ExploreAdapter? = null
    private val router: IExploreRouter by getRouter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setFragmentResultListener(FRAGMENT_RESULT_ADD_FOLDER) { key, bundle ->
//            if (FRAGMENT_RESULT_ADD_FOLDER == key) {
//                val name = (bundle.get(ARG_FOLDER_NAME) as? String)
//                val path = (bundle.get(ARG_FOLDER_PATH) as? String)
//                viewModel.addFolder(name, path)
//            }
//        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        binding.toolbar.getBuilder()
            .title("My costs")
            .create()
        viewModel.state.observe(viewLifecycleOwner) {
            adapter?.updateAdapter(it)
        }
        viewModel.fetchData()
    }

    private fun initViews() {
        with(binding) {
            exploreFloatButton.setOnClickListener {
                binding.exploreFloatButton.animateFabHide {
                    router.fromExploreToAddFolder("")
                }
            }
            adapter = ExploreAdapter({
                handleOnClick(it)
            }, {
                handleOnLongClick(it)
            })
            mainRecyclerViewX.setHasFixedSize(true)
            mainRecyclerViewX.adapter = adapter
        }
    }

    private fun handleOnClick(item: AdapterItems) {
        when (item) {
            is AdapterItems.FolderItem -> {
                binding.exploreFloatButton.animateFabHide {
                    router.fromExploreToFolder(item.path)
                }
            }
            else -> TODO()
        }
    }

    private fun handleOnLongClick(item: AdapterItems) {
        when (item) {
            is AdapterItems.FolderItem -> {
                binding.exploreFloatButton.animateFabHide {
                    router.fromExploreToFolder(item.path)
                }
            }
            else -> TODO()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.exploreFloatButton.animateShowFab()
    }
}

