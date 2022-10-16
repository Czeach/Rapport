package com.czech.rapport.ui.onboarding

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.czech.rapport.databinding.SplashScreenFragmentBinding
import com.czech.rapport.utils.launchFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {

    private var _binding: SplashScreenFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<SplashScreenViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SplashScreenFragmentBinding.inflate(layoutInflater, container, false)

        delayAndObserve()

        return binding.root
    }

    private fun delayAndObserve() {
        lifecycleScope.launch {
            delay(2000)
            goToNextScreen()
        }
    }

    private fun goToNextScreen() {
        launchFragment(SplashFragmentDirections.actionSplashFragmentToOnboardingFragment())
    }

}