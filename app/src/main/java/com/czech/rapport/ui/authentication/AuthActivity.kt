package com.czech.rapport.ui.authentication

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.czech.rapport.databinding.AuthActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : FragmentActivity() {

    private lateinit var binding: AuthActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AuthActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}