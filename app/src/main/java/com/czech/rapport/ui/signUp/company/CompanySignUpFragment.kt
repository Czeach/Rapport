package com.czech.rapport.ui.signUp.company

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.czech.rapport.databinding.CompanySignUpFragmentBinding

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


}