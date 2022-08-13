package com.czech.rapport.ui.signUp.employee

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.czech.rapport.databinding.EmployeeSignUpFragmentBinding

class EmployeeSignUpFragment : Fragment() {

    private var _binding: EmployeeSignUpFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<EmployeeSignUpViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = EmployeeSignUpFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }


}