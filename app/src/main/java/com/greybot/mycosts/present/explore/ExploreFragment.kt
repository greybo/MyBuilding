package com.greybot.mycosts.present.explore

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.greybot.mycosts.MainActivity
import com.greybot.mycosts.base.BaseBindingFragment
import com.greybot.mycosts.databinding.ExploreFragmentBinding

class ExploreFragment :
    BaseBindingFragment<ExploreFragmentBinding>(ExploreFragmentBinding::inflate) {

    private val viewModel by viewModels<ExploreViewModel>()
    private var adapter: ExploreAdapter? = null
    private val router: IExploreRouter by getRouter()

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
            router.fromExploreToFolder(it)
        }
        binding.mainRecyclerViewX.setHasFixedSize(true)
        binding.mainRecyclerViewX.adapter = adapter
    }
}

inline fun <reified T> Fragment.getRouter(): Lazy<T> {
    return lazy {
        val activity = (requireActivity() as? MainActivity) ?: throw Throwable()
        activity.router as T
    }
}

