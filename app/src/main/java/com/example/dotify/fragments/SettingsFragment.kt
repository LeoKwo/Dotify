package com.example.dotify.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.dotify.R
import com.example.dotify.databinding.FragmentSettingsBinding



class SettingsFragment : Fragment() {

    private val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSettingsBinding.inflate(inflater)
        with(binding) {

            btProfile.setOnClickListener{
                navController.navigate(R.id.profileFragment)
            }

            btAbout.setOnClickListener{
                navController.navigate(R.id.aboutFragment)
            }

            btStats.setOnClickListener{
                navController.navigate(R.id.statsFragment)
            }
        }

        return binding.root
    }

}