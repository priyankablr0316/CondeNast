package com.assessment.condenast.ui

import com.assessment.condenast.R
import com.assessment.condenast.databinding.ActivityMainBinding
import com.assessment.condenast.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * Single Activity Architecture.
 */
@AndroidEntryPoint
class MainActivity(override val layout: Int = R.layout.activity_main) : BaseActivity<ActivityMainBinding>()
