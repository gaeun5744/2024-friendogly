package com.woowacourse.friendogly.presentation.ui

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.woowacourse.friendogly.NavigationGraphDirections
import com.woowacourse.friendogly.R
import com.woowacourse.friendogly.databinding.ActivityMainBinding
import com.woowacourse.friendogly.presentation.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private var waitTime = 0L

    override fun initCreateView() {
        initNavController()
    }

    private fun initNavController() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment, R.id.woofFragment, R.id.chatFragment, R.id.myPageFragment -> showBottomNav()
                else -> hideBottomNav()
            }
        }
        binding.bottomNavi.setupWithNavController(navController)
        binding.bottomNavi.setOnItemReselectedListener {}
    }

    private fun showBottomNav() {
        binding.bottomNavi.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        binding.bottomNavi.visibility = View.GONE
    }

    override fun onBackPressed() {
        try {
            if (onBackPressedDispatcher.hasEnabledCallbacks()) {
                super.onBackPressed()
            } else {
                when (navController.currentDestination?.id) {
                    R.id.homeFragment -> {
                        if (System.currentTimeMillis() - waitTime >= 1500) {
                            waitTime = System.currentTimeMillis()
                            showToastMessage(getString(R.string.on_back_pressed_Message))
                        } else {
                            finishAffinity()
                        }
                    }

                    null -> super.onBackPressed()
                    else -> navController.navigate(NavigationGraphDirections.actionHomeFragment())
                }
            }
        } catch (e: Exception) {
            showToastMessage(e.message.toString())
        }
    }
}
