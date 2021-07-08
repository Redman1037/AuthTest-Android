package com.appstyx.authtest.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.appstyx.authtest.R
import com.appstyx.authtest.common.*
import com.appstyx.authtest.databinding.FragmentHomeBinding
import com.appstyx.authtest.models.User
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Start destination should always be homeFragment this is as per recommended guidelines
        if (!PreferencesUtils.isUserLoggedIn(requireContext())) {
            gotoSignUpPage()
            return
        }

        getUserDataOrSetUi()
        initViewModelObservers()
        initActionsListeners()
    }

    private fun getUserDataOrSetUi() {
        if (viewModel.userResponse.value?.data?.user == null) {
            viewModel.getMyProfile()
        } else {
            setUserData(viewModel.userResponse.value?.data?.user!!)
        }
    }

    private fun initViewModelObservers() {
        viewModel.commonEvent.observe(viewLifecycleOwner) { event ->
            when (event) {
                Constants.EVENT_SHOW_PROGRESS -> showProgress(activity)
                Constants.EVENT_DISMISS_PROGRESS -> dismissProgress()
            }
        }
        viewModel.userResponse.observe(viewLifecycleOwner){userResponse->
            if (viewModel.userResponse.value?.data?.user != null){
                setUserData(viewModel.userResponse.value?.data?.user!!)
            }
        }
        viewModel.userProfileApiException.observe(viewLifecycleOwner){commonResponse->
            if (!commonResponse.message.isNullOrBlank()){
                showSnackBar(commonResponse.message!!,MessageType.FAILURE)
            }
        }
    }

    private fun initActionsListeners() {
        binding.logoutButton.setOnClickListener(this)
    }

    private fun setUserData(user: User) {

        binding.textViewFirstName.text=user.firstName
        binding.textViewLastName.text=user.lastName
        if (!user.avatarURL.isNullOrBlank()) {
            binding.imageViewAvatar.loadImage(user.avatarURL!!)
        }

    }

    private fun gotoSignUpPage() {
        val action = HomeFragmentDirections.actionHomeFragmentToSignUpFragment()
        findNavController().navigate(action)
    }

    private fun logOutAction() {
        viewModel.onLogoutClick()
        PreferencesUtils.clearSharedPreferences(requireActivity())
        gotoSignUpPage()
    }

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.logoutButton -> {
                logOutAction()
            }
        }
    }
}