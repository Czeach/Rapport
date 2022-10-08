package com.czech.rapport.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.czech.rapport.R
import com.czech.rapport.databinding.ActivityOnboardingBinding
import com.czech.rapport.utils.hide
import com.czech.rapport.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingActivity : FragmentActivity() {

    private lateinit var viewPager: ViewPager2

    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewPager = binding.vp2Pager

        val pagerAdapter = OnboardingAdapter(this)
        viewPager.adapter = pagerAdapter
        viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
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

    }

    /**
     * Update views based on fragment position
     */
    private fun updatePageViews(binding: ActivityOnboardingBinding, position: Int) {
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

    private fun handleClicks(binding: ActivityOnboardingBinding, position: Int) {
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
