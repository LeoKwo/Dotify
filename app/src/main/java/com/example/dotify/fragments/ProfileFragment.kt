package com.example.dotify.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.transform.CircleCropTransformation
import com.example.dotify.R
import com.example.dotify.databinding.FragmentProfileBinding
import com.example.dotify.managers.AccountManager
import com.example.dotify.models.Account
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    lateinit var accountManager: AccountManager
    lateinit var account: Account

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.accountManager = AccountManager()
        val binding = FragmentProfileBinding.inflate(inflater)
        // coroutine old
//        with(binding) {
//            lifecycleScope.launch {
//                runCatching {
//                    Toast.makeText(activity, "Loading your Account...", Toast.LENGTH_SHORT).show()
//                    account = accountManager.dataRepository.getAccount()
//                    tvProfileUsername.text = account.username
//                    tvProfileFirstName.text = account.firstName
//                    tvProfileLastName.text = account.lastName
//                    tvProfileHasNose.text = account.hasNose.toString()
//                    tvProfilePlatform.text = account.platform.toString()
//                }.onFailure {
//                    Toast.makeText(activity, "Error occurred when fetching your Account information", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//
//        }
        // coroutine new
        loadAccount(binding)
        return binding.root

        //old
//        val binding = FragmentProfileBinding.inflate(inflater)
//        return binding.root
    }

    private fun loadAccount(binding: FragmentProfileBinding) {
        with(binding) {
            lifecycleScope.launch {
                runCatching {
                    Toast.makeText(activity, "Loading your Account...", Toast.LENGTH_SHORT).show()
                    account = accountManager.dataRepository.getAccount()
                    tvProfileUsername.text = account.username
                    tvProfileFirstName.text = account.firstName
                    tvProfileLastName.text = account.lastName
                    tvProfileHasNose.text = account.hasNose.toString()
                    tvProfilePlatform.text = account.platform.toString()
                    ivProfilePicture.load(account.profilePicURL) {
                        crossfade(true)
                        placeholder(R.drawable.iv_account_placeholder)
                    }
                }.onFailure {
                    tvErrorMsg.isInvisible = false
                }
            }
        }
    }
}