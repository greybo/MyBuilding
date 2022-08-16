package com.greybot.mybuilding.profile

import android.os.Bundle
import android.view.View
import com.greybot.mybuilding.base.BaseBindingFragment
import com.greybot.mybuilding.databinding.ProfileFragmentBinding

class UserProfileFragment :
    BaseBindingFragment<ProfileFragmentBinding>(ProfileFragmentBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.phoneInput.setOnClickListener {

        }
    }

}