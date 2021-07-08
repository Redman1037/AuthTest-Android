package com.appstyx.authtest.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.appstyx.authtest.R
import com.appstyx.authtest.common.*
import com.appstyx.authtest.databinding.FragmentSignupBinding
import com.appstyx.authtest.models.Gender
import com.appstyx.authtest.models.GenderResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment : Fragment() {

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<SignupViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackPressed {
            //kill app
            findNavController().popBackStack(R.id.homeFragment, true)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModelObservers()
        initActionsListeners()
        getGenderListIfRequiredElseSetData()
    }

    private fun getGenderListIfRequiredElseSetData() {

        val genderList = viewModel.getGendersList()
        if (genderList == null) {
            binding.progressBarGender.visible()
            viewModel.getGenders()
        } else {
            binding.progressBarGender.gone()
            setGenderAdapter(genderList)
        }
    }

    private fun initViewModelObservers() {
        viewModel.genderResponse.observe(viewLifecycleOwner) { genderResponse ->
            if (genderResponse != null) {
                setGenderAdapter(viewModel.getGendersList() ?: mutableListOf())
            }
        }

        viewModel.genderApiException.observe(viewLifecycleOwner) { commonResponse ->
            if (!commonResponse.message.isNullOrBlank()) {
                showSnackBar(commonResponse.message!!,MessageType.FAILURE)
            }
        }
        viewModel.signUpApiException.observe(viewLifecycleOwner) { commonResponse ->
            if (!commonResponse.message.isNullOrBlank()) {
                showSnackBar(commonResponse.message!!,MessageType.FAILURE)
            }
        }
        viewModel.errorEvent.observe(viewLifecycleOwner) { errorEvent ->
            when (errorEvent) {
                Constants.ERROR_EVENT_EMPTY_EMAIL -> {
                    binding.inputLayoutEmail.setError(getString(R.string.signup_validation_empty_field))
                }
                Constants.ERROR_EVENT_INVALID_EMAIL -> {
                    binding.inputLayoutEmail.setError(getString(R.string.signup_validation_invalid_field))
                }
                Constants.ERROR_EVENT_EMPTY_NAME -> {
                    binding.inputLayoutFirstName.setError(getString(R.string.signup_validation_empty_field))
                }
                Constants.ERROR_EVENT_INVALID_NAME -> {
                    binding.inputLayoutFirstName.setError(getString(R.string.signup_validation_invalid_field))
                }
                Constants.ERROR_EVENT_EMPTY_LAST_NAME -> {
                    binding.inputLayoutLastName.setError(getString(R.string.signup_validation_empty_field))
                }
                Constants.ERROR_EVENT_INVALID_LAST_NAME -> {
                    binding.inputLayoutLastName.setError(getString(R.string.signup_validation_invalid_field))
                }
                Constants.ERROR_EVENT_EMPTY_GENDER -> {
                    setGenderError()
                }
            }
        }

        viewModel.commonEvent.observe(viewLifecycleOwner) { event ->
            when (event) {
                Constants.EVENT_SHOW_PROGRESS -> showProgress(activity)
                Constants.EVENT_DISMISS_PROGRESS -> dismissProgress()
            }
        }

        viewModel.signUpResponse.observe(viewLifecycleOwner) { signUpResponse ->

            if (!signUpResponse.data?.token.isNullOrBlank() && activity != null) {
                showSnackBar(getString(R.string.signup_success_msg),MessageType.SUCCESS)
                PreferencesUtils.saveAccessToken(requireContext(), signUpResponse.data?.token!!)
                //move back to homeScreen
                findNavController().popBackStack(R.id.homeFragment, false)
            }

        }
    }

    private fun initActionsListeners() {
        binding.signupButton.setOnClickListener {
            viewModel.onSignupClick()
        }

        binding.editTextEmail.doOnTextChanged { text, start, before, count ->
            binding.inputLayoutEmail.error = null
            viewModel.user.email = text?.trim()?.toString()
        }
        binding.editTextFirstName.doOnTextChanged { text, start, before, count ->
            binding.inputLayoutFirstName.error = null
            viewModel.user.firstName = text?.trim()?.toString()
        }
        binding.editTextLastName.doOnTextChanged { text, start, before, count ->
            binding.inputLayoutLastName.error = null
            viewModel.user.lastName = text?.trim()?.toString()
        }

        binding.spinnerGender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                removeGenderError()
                viewModel.user.gender = viewModel.getGendersList()?.get(position)?.id
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    private fun setGenderAdapter(genderList: List<Gender>) {

        binding.progressBarGender.gone()

        val genderAdapter = getSpinnerWithHintAdapter(requireContext(), genderList)
        genderAdapter.setDropDownViewResource(R.layout.spinner_item)
        binding.spinnerGender.adapter = genderAdapter

    }

    private fun setGenderError() {
        binding.spinnerGender.setBackgroundResource(R.drawable.rounded_stroke_error_red_bg_8dp)
        binding.tvGenderErrorMsg.visible()
    }

    private fun removeGenderError() {
        binding.spinnerGender.setBackgroundResource(R.drawable.rounded_stroke_purple_500_bg_8dp)
        binding.tvGenderErrorMsg.gone()
    }


    companion object {
        fun newInstance() = SignupFragment()
    }

}