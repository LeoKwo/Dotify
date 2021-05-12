package com.example.dotify.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dotify.BuildConfig
import com.example.dotify.R
import com.example.dotify.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAboutBinding.inflate(inflater)
        with(binding) {
            val context = requireContext()
            tvVersion.text = context.getString(R.string.about_version_format, BuildConfig.VERSION_NAME)
        }
        return binding.root
    }
}