package com.czech.rapport.ui.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.czech.rapport.R
import com.czech.rapport.data.models.EmployeeInfo
import com.czech.rapport.databinding.EmployeeSignUpFragmentBinding
import com.czech.rapport.utils.*
import com.czech.rapport.utils.states.EmployeeSignUpState
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress

class EmployeeSignUpFragment : Fragment() {

    private var _binding: EmployeeSignUpFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = EmployeeSignUpFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signUpButton.setOnClickListener {
            hideKeyboard(requireActivity())

            if (isAuthDetailsValid()) {
                val employeeInfo = EmployeeInfo(
                    firstName = binding.firstNameText.text.toString().trim().uppercase(),
                    lastName = binding.lastNameText.text.toString().trim().uppercase(),
                    workEmail = binding.workEmailText.text.toString().trim(),
                    currentCompany = binding.currentCompanyText.text.toString().trim().uppercase(),
                    positionAtCompany = binding.roleAtCompanyText.text.toString().trim(),
                    password = binding.createPasswordText.text.toString()
                )

                viewModel.employeeSignUp(employeeInfo)
            }
        }
        observe()
    }

    private fun observe() {
        lifecycleScope.launchWhenStarted {
            viewModel.employeeSignUpState.collect {
                when (it) {
                    is EmployeeSignUpState.Loading -> {
                        showProgress(true)
                    }
                    is EmployeeSignUpState.Error -> {
                        showProgress(false)
                        requireActivity().showShortToast(it.message)
                    }
                    is EmployeeSignUpState.Success -> {
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
            binding.firstNameText.text?.isEmpty() == true -> {
                binding.firstName.error = getString(R.string.field_cannot_be_empty)
                false
            }
            binding.lastNameText.text?.isEmpty() == true -> {
                binding.firstName.error = getString(R.string.field_cannot_be_empty)
                false
            }
            binding.workEmailText.text?.isEmpty() == true -> {
                binding.workEmail.error = getString(R.string.field_cannot_be_empty)
                false
            }
            binding.currentCompanyText.text?.isEmpty() == true -> {
                binding.currentCompany.error = getString(R.string.field_cannot_be_empty)
                false
            }
            binding.roleAtCompanyText.text?.isEmpty() == true -> {
                binding.roleAtCompany.error = getString(R.string.field_cannot_be_empty)
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
            binding.createPasswordText.text?.toString()?.length!! < Constants.PASSWORD_LIMIT ->{
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