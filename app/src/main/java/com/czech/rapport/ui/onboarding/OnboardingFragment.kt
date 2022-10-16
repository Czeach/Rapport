package com.czech.rapport.ui.onboarding

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.czech.rapport.R
import com.czech.rapport.databinding.ActivityOnboardingBinding
import com.czech.rapport.databinding.FragmentOnboardingBinding
import com.czech.rapport.utils.hide
import com.czech.rapport.utils.show

class OnboardingFragment : Fragment() {

    private lateinit var viewPager: ViewPager2

    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)

        viewPager = binding.vp2Pager

        val pagerAdapter = OnboardingAdapter(requireContext())
        viewPager.adapter = pagerAdapter
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            // triggered when there is any scrolling activity for the current page
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                updatePageViews(binding, position)
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            // triggered when you select a new page
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handleClicks(binding, position)
            }

        })

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun updatePageViews(binding: FragmentOnboardingBinding, position: Int) {
        when (position) {
            0 -> {
                binding.apply {
                    firstCircle.setImageResource(R.drawable.filled_dot)
                    secondCircle.setImageResource(R.drawable.unfilled_dot)
                    thirdCircle.setImageResource(R.drawable.unfilled_dot)
                    nextButton.show()
                    previousButton.hide()
                    authContainer.hide()
                }
            }
            1 -> {
                binding.apply {
                    secondCircle.setImageResource(R.drawable.filled_dot)
                    firstCircle.setImageResource(R.drawable.unfilled_dot)
                    thirdCircle.setImageResource(R.drawable.unfilled_dot)
                    previousButton.show()
                    nextButton.show()
                    authContainer.hide()
                }
            }
            2 -> {
                binding.apply {
                    thirdCircle.setImageResource(R.drawable.filled_dot)
                    secondCircle.setImageResource(R.drawable.unfilled_dot)
                    firstCircle.setImageResource(R.drawable.unfilled_dot)
                    authContainer.show()
                    previousButton.hide()
                    nextButton.hide()

                }

            }
        }
    }

    private fun handleClicks(binding: FragmentOnboardingBinding, position: Int) {
        when (position) {
            0 -> {
                binding.nextButton.setOnClickListener { binding.vp2Pager.currentItem += 1 }
            }
            1 -> {
                binding.previousButton.setOnClickListener { binding.vp2Pager.currentItem -= 1 }
                binding.nextButton.setOnClickListener { binding.vp2Pager.currentItem += 1 }
            }
            2 -> {
                //TODO: Handle navigation to main screens
            }
        }
    }

}