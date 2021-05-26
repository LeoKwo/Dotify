package com.example.dotify.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.dotify.R
import com.example.dotify.databinding.FragmentSettingsBinding
import com.example.dotify.managers.SongNotificationManager




class SettingsFragment : Fragment() {
    lateinit var songNotificationManager: SongNotificationManager
    private val navController by lazy { findNavController() }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        songNotificationManager = SongNotificationManager(context.applicationContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSettingsBinding.inflate(inflater)
//        if (myActivity != null) {
//
//        }
//        private val songNotificationManager = SongNotificationManager(activity.applicationContext)
//        songNotificationManager = SongNotificationManager(container.context)
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

            // This was working
//            btNotification.setOnClickListener{
//                songNotificationManager.publishSongNotification()
//            }

            // This was working
//            btNotification.setOnClickListener{
//                songNotificationManager.repetitiveSongNotification()
//            }


            switchNotification.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    songNotificationManager.repetitiveSongNotification()
                } else {
                    songNotificationManager.stopRepetitiveSongNotification()
                }
            }
        }

        return binding.root
    }

}