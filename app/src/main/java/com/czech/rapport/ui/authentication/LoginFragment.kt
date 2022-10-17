package com.czech.rapport.ui.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.czech.rapport.R
import com.czech.rapport.databinding.CompanySignUpFragmentBinding
import com.czech.rapport.databinding.FragmentLoginBinding
import com.czech.rapport.utils.launchFragment

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)

        navigateToPages()

        return binding.root
    }

    private fun navigateToPages() {
        binding.apply {
            companySignUpText.setOnClickListener {
                launchFragment(LoginFragmentDirections.actionLoginFragmentToCompanySignUpFragment2())
            }
            employeeSignUpText.setOnClickListener {
                launchFragment(LoginFragmentDirections.actionLoginFragmentToEmployeeSignUpFragment())
            }
        }
    }

}