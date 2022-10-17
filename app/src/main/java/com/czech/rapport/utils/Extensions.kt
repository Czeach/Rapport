package com.czech.rapport.utils

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

fun View.hide(onlyInvisible: Boolean = false) {
    this.visibility = if (onlyInvisible) View.INVISIBLE else View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.disableView() {
    animate()
        .alpha(0.5f).duration = 300
    isClickable = false
    isEnabled = false
    isFocusable = false
}

fun View.enableView() {
    animate()
        .alpha(1f).duration = 300
    isClickable = true
    isFocusable = true
    isEnabled = true
}

fun Fragment.launchFragment(direction: NavDirections) = try {
    findNavController().navigate(direction)
} catch (e: Exception) {
    Log.e("NAVIGATION_ERROR", e.toString())
}

fun Fragment.launchFragment(@IdRes destination: Int, args: Bundle? = null) = try {
    findNavController().navigate(destination, args)
} catch (e: Exception) {
    Log.e("NAVIGATION_ERROR", e.toString())
}

fun Activity.showShortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun hideKeyboard(activity: Activity?, view: View? = null) {
    if (activity == null) {
        return
    }
    val currentView = activity.currentFocus ?: view ?: return
    val inputMethodManager = activity.getSystemService(
        Activity.INPUT_METHOD_SERVICE
    ) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(
        currentView.windowToken, 0
    )
}