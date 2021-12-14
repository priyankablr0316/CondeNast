package com.assessment.condenast.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.assessment.condenast.R
import com.assessment.condenast.util.ConnectivityManager
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

/**
 * Common functionality is added like it monitors internet connection and displays right message to user.
 * Should be used by all the activities.
 */
abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity() {

    private var connectivityCount = 0

    private lateinit var binding: B

    @get:LayoutRes
    protected abstract val layout: Int

    @Inject
    lateinit var connectivityManager: ConnectivityManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layout)
        binding.lifecycleOwner = this
    }

    override fun onStart() {
        super.onStart()
        attachListeners()
    }

    private fun attachListeners() {
        connectivityManager.observe(this) {
            connectivityCount++
            if (connectivityCount != 1) {
                showNetworkMessage(it)
            }
        }
    }

    private fun showNetworkMessage(isConnected: Boolean) {
        if (isConnected) {
            Snackbar.make(
                binding.root,
                R.string.network_connection_online,
                Snackbar.LENGTH_LONG
            ).show()
        } else {
            Snackbar.make(
                binding.root,
                R.string.network_connection_offline,
                Snackbar.LENGTH_LONG
            ).show()
        }
    }
}