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
        loadAccount(binding)
        return binding.root
    }

    private fun loadAccount(binding: FragmentProfileBinding) {
        with(binding) {
            lifecycleScope.launch {
                runCatching {
                    Toast.makeText(activity, "Loading your Account...", Toast.LENGTH_SHORT).show()
                    account = accountManager.dataRepository.getAccount()
                    tvProfileUsername.text = account.username
                    tvProfileFirstName.text = root.context.getString(
                            R.string.profile_first_name_format,
                            account.firstName)
                    tvProfileLastName.text = root.context.getString(
                            R.string.profile_last_name_format,
                            account.lastName)
                    if (account.hasNose) {
                        tvProfileHasNose.text = root.context.getString(
                                R.string.profile_nose_false)
                    } else {
                        tvProfileHasNose.text = root.context.getString(
                                R.string.profile_nose_true)
                    }
                    tvProfilePlatform.text = root.context.getString(
                            R.string.profile_platform_format,
                            account.platform.toString())
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