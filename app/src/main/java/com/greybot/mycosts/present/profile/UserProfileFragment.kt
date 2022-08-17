package com.greybot.mycosts.present.profile

import android.os.Bundle
import android.view.View
import com.greybot.mycosts.base.BaseBindingFragment
import com.greybot.mycosts.databinding.ProfileFragmentBinding

class UserProfileFragment :
    BaseBindingFragment<ProfileFragmentBinding>(ProfileFragmentBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.phoneInput.setOnClickListener {

        }
    }

}