package com.greybot.mycosts.present.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.greybot.mycosts.base.BaseBindingFragment
import com.greybot.mycosts.databinding.ExploreFragmentBinding
import com.greybot.mycosts.present.adapter.AdapterCallback
import com.greybot.mycosts.present.adapter.ExploreAdapter
import com.greybot.mycosts.utility.animateFabHide
import com.greybot.mycosts.utility.animateShowFab
import com.greybot.mycosts.utility.getRouter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainExploreFragment :
    BaseBindingFragment<ExploreFragmentBinding>(ExploreFragmentBinding::inflate) {

    private val viewModel by viewModels<MainExploreViewModel>()
    private var adapter: ExploreAdapter? = null
    private val router: IMainExploreRouter by getRouter()

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
                exploreFloatButton.animateFabHide {
                    router.fromExploreToAddFolder("root")
                }
            }
        }
        initAdapter()
    }

    private fun initAdapter() {
        with(binding) {
            adapter = ExploreAdapter {
                handleOnClick(it)
            }
            mainRecyclerViewX.setHasFixedSize(true)
            mainRecyclerViewX.adapter = adapter
        }
    }

    private fun handleOnClick(callback: AdapterCallback) {
        when (callback) {
            is AdapterCallback.FolderOpen -> {
                binding.exploreFloatButton.animateFabHide {
                    router.fromExploreToFolder(callback.value.objectId ?: "root")
                }
            }
            is AdapterCallback.FolderLong -> {
                binding.exploreFloatButton.animateFabHide {
//                    router.fromExploreToFolder(, callback.value.path)
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

