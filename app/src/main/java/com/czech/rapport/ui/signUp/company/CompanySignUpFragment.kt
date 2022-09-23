package com.czech.rapport.ui.signUp.company

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.czech.rapport.R
import com.czech.rapport.data.models.CompanyInfo
import com.czech.rapport.databinding.CompanySignUpFragmentBinding
import com.czech.rapport.utils.hideKeyboard

class CompanySignUpFragment : Fragment() {

    private var _binding: CompanySignUpFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<CompanySignUpViewModel>()

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

                viewModel.createCompany(companyInfo)
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
            !Patterns.EMAIL_ADDRESS.matcher(binding.companyNameText.text).matches() -> {
                binding.companyEmail.error = getString(R.string.enter_valid_email_address)
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
                binding.confirmPassword.error = getString(R.string.field_cannot_be_empty)
                false
            }
            binding.createPasswordText.text?.length!! < 8 ->{
                binding.createPassword.error = getString(R.string.password_must_be_at_least_8_characters)
                false
            }
            binding.createPasswordText.text != binding.confirmPasswordText.text -> {
                binding.confirmPassword.error = getString(R.string.password_must_match)
                false
            }
            else -> true
        }

    }

}