package com.greybot.mycosts.present.explore

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.greybot.mycosts.base.BaseBindingFragment
import com.greybot.mycosts.databinding.ExploreFragmentBinding
import com.greybot.mycosts.utility.ARG_FOLDER_NAME
import com.greybot.mycosts.utility.FRAGMENT_RESULT_ADD_FOLDER
import com.greybot.mycosts.utility.getRouter


class ExploreFragment :
    BaseBindingFragment<ExploreFragmentBinding>(ExploreFragmentBinding::inflate) {

    private val viewModel by viewModels<ExploreViewModel>()
    private var adapter: ExploreAdapter? = null
    private val router: IExploreRouter by getRouter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(FRAGMENT_RESULT_ADD_FOLDER) { key, bundle ->
            if (FRAGMENT_RESULT_ADD_FOLDER == key) {
                val name = (bundle.get(ARG_FOLDER_NAME) as? String) ?: ""
                viewModel.addFolder(name)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initViews()

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

    private fun initViews() {
        binding.exploreFloatButton.setOnClickListener {
            router.fromExploreToAddFolder()
        }
    }

}

