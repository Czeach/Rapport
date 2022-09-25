package com.czech.rapport.ui.onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.czech.rapport.R
import com.czech.rapport.databinding.ActivityOnboardingBinding

private const val NUM_PAGES = 3

class OnboardingActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private var onBoardingPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            updateCircleMarker(binding, position)
        }
    }

    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewPager = findViewById(R.id.vp2_pager)

        val pagerAdapter = ScreenSlidePagerAdapter(this)
        viewPager.adapter = pagerAdapter
        viewPager.registerOnPageChangeCallback(onBoardingPageChangeCallback)
//        viewPager.setPageTransformer(ZoomOutPageTransformer())

    }

    override fun onDestroy() {
        binding.vp2Pager.unregisterOnPageChangeCallback(onBoardingPageChangeCallback)
        super.onDestroy()
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }

    /**
     * Update slider circle view based on fragment position
     */
    private fun updateCircleMarker(binding: ActivityOnboardingBinding, position: Int) {
        when (position) {
            0 -> {
                binding.firstCircle.setImageDrawable(getDrawable(R.drawable.filled_dot))
                binding.secondCircle.setImageDrawable(getDrawable(R.drawable.unfilled_dot))
                binding.thirdCircle.setImageDrawable(getDrawable(R.drawable.unfilled_dot))
            }
            1 -> {
                binding.secondCircle.setImageDrawable(getDrawable(R.drawable.filled_dot))
                binding.firstCircle.setImageDrawable(getDrawable(R.drawable.unfilled_dot))
                binding.thirdCircle.setImageDrawable(getDrawable(R.drawable.unfilled_dot))
            }
            2 -> {
                binding.thirdCircle.setImageDrawable(getDrawable(R.drawable.filled_dot))
                binding.secondCircle.setImageDrawable(getDrawable(R.drawable.unfilled_dot))
                binding.firstCircle.setImageDrawable(getDrawable(R.drawable.unfilled_dot))
            }
            3 -> {
                binding.thirdCircle.setImageDrawable(getDrawable(R.drawable.filled_dot))
                binding.secondCircle.setImageDrawable(getDrawable(R.drawable.unfilled_dot))
                binding.thirdCircle.setImageDrawable(getDrawable(R.drawable.unfilled_dot))
            }
        }
    }


    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment =
            OnboardingFragment()
    }
}