package com.czech.rapport.ui.onboarding

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.czech.rapport.utils.Constants.PREFERENCES.HAS_USER_SEEN_ONBOARDING
import com.czech.rapport.utils.Constants.PREFERENCES.IS_USER_LOGGED_IN
import com.czech.rapport.utils.Constants.PREFERENCES.IS_USER_LOGGED_OUT
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
): ViewModel() {

    var hasUserSeenOnboarding: Boolean
        get() = sharedPreferences.getBoolean(HAS_USER_SEEN_ONBOARDING, false)
        set(value) = sharedPreferences.edit().putBoolean(HAS_USER_SEEN_ONBOARDING, value).apply()

    var isUserLoggedIn: Boolean
        get() = sharedPreferences.getBoolean(IS_USER_LOGGED_IN, false)
        set(value) = sharedPreferences.edit().putBoolean(IS_USER_LOGGED_IN, value).apply()

}