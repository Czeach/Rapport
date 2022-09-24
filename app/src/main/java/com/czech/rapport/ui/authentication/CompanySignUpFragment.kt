package com.czech.rapport.ui.authentication

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.czech.rapport.R
import com.czech.rapport.data.models.CompanyInfo
import com.czech.rapport.databinding.CompanySignUpFragmentBinding
import com.czech.rapport.utils.*
import com.czech.rapport.utils.states.CompanySignUpState
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import com.czech.rapport.utils.Constants.PASSWORD_LIMIT
import java.util.regex.Pattern

class CompanySignUpFragment : Fragment() {

    private var _binding: CompanySignUpFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CompanySignUpFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.signUpButton.setOnClickListener {
            hideKeyboard(requireActivity())

            if (isAuthDetailsValid()) {
                val companyInfo = CompanyInfo(
                    companyName = binding.companyNameText.text.toString().trim().uppercase(),
                    companyEmail = binding.companyEmailText.text.toString().trim(),
                    industryType = binding.industryTypeText.text.toString().trim(),
                    companySize = binding.companySizeText.text.toString().trim(),
                    headquarterAddress = binding.companyHqAddressText.text.toString().trim(),
                    nameOfRegistrar = binding.yourFullNameText.text.toString().trim(),
                    positionOfRegistrar = binding.yourRoleAtCompText.text.toString().trim(),
                    companyDescription = binding.briefDescText.text.toString().trim(),
                    companyPassword = binding.createPasswordText.text.toString()
                )

                viewModel.companySignUp(companyInfo)
            }
        }
        observe()
    }

    private fun observe() {
        lifecycleScope.launchWhenStarted {
            viewModel.companySignUpState.collect {
                when (it) {
                    is CompanySignUpState.Loading -> {
                        showProgress(true)
                    }
                    is CompanySignUpState.Error -> {
                        showProgress(false)
                        requireActivity().showShortToast(it.message)
                    }
                    is CompanySignUpState.Success -> {
                        showProgress(false)
                        requireActivity().showShortToast(it.data.toString())
                    }
                    else -> {}
                }
            }
        }
    }

    private fun isAuthDetailsValid(): Boolean {
        return when {
            binding.companyNameText.text?.isEmpty() == true -> {
                binding.companyName.error = getString(R.string.field_cannot_be_empty)
                false
            }
            binding.companyEmailText.text?.isEmpty() == true -> {
                binding.companyEmail.error = getString(R.string.field_cannot_be_empty)
                false
            }
            binding.industryTypeText.text?.isEmpty() == true -> {
                binding.industryType.error = getString(R.string.field_cannot_be_empty)
                false
            }
            binding.companySizeText.text?.isEmpty() == true -> {
                binding.companySize.error = getString(R.string.field_cannot_be_empty)
                false
            }
            binding.companyWebsiteText.text?.isEmpty() == true -> {
                binding.companyWebsite.error = getString(R.string.field_cannot_be_empty)
                false
            }
            binding.companyHqAddressText.text?.isEmpty() == true -> {
                binding.companyHqAddress.error = getString(R.string.field_cannot_be_empty)
                false
            }
            binding.briefDescText.text?.isEmpty() == true -> {
                binding.briefDesc.error = getString(R.string.field_cannot_be_empty)
                false
            }
            binding.yourFullNameText.text?.isEmpty() == true -> {
                binding.yourFullName.error = getString(R.string.field_cannot_be_empty)
                false
            }
            binding.yourRoleAtCompText.text?.isEmpty() == true -> {
                binding.yourRoleAtComp.error = getString(R.string.field_cannot_be_empty)
                false
            }
            binding.createPasswordText.text?.isEmpty() == true -> {
                binding.createPassword.error = getString(R.string.field_cannot_be_empty)
                false
            }
            binding.confirmPasswordText.text?.isEmpty() == true -> {
                binding.confirmPassword.error = getString(R.string.password_must_match)
                false
            }
            binding.createPasswordText.text?.toString()?.length!! < PASSWORD_LIMIT ->{
                binding.createPassword.error = getString(R.string.password_must_be_at_least_8_characters)
                false
            }
            binding.createPasswordText.text.toString() != binding.confirmPasswordText.text.toString() -> {
                binding.confirmPassword.error = getString(R.string.password_must_match)
                false
            }
            else -> true
        }
    }

    private fun showProgress(show: Boolean) {
        binding.signUpButton.apply {
            if (show) {
                disableView()
                showProgress {
                    buttonText = ""
                    progressColor = ContextCompat.getColor(requireContext(), R.color.white)
                }
            } else {
                enableView()
                hideProgress("Sign up")
            }
        }
    }
}