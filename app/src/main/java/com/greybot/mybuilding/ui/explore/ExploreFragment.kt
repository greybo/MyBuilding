package com.greybot.mybuilding.ui.explore

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.greybot.mybuilding.MainActivity
import com.greybot.mybuilding.base.BaseBindingFragment
import com.greybot.mybuilding.databinding.ExploreFragmentBinding

class ExploreFragment :
    BaseBindingFragment<ExploreFragmentBinding>(ExploreFragmentBinding::inflate) {

    private val viewModel by viewModels<ExploreViewModel>()
    private var adapter: ExploreAdapter? = null
    val router: ExploreRouter by getRouter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()

        viewModel.state.observe(viewLifecycleOwner) {
            adapter?.updateAdapter(it)
        }
        viewModel.fetchData()
    }


    private fun initAdapter() {
        adapter = ExploreAdapter {
            router.toFolder(it)
        }
        binding.mainRecyclerViewX.setHasFixedSize(true)
        binding.mainRecyclerViewX.adapter = adapter
    }
}

inline fun <reified T> Fragment.getRouter(): Lazy<T> {
    val activity = (requireActivity() as? MainActivity) ?: throw Throwable()

    return when (this) {
        is ExploreFragment -> lazy { activity.router as T }
        else -> TODO()
    }
}

