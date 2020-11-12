package com.txusmslabs.templateapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.txusmslabs.templateapp.R
import com.txusmslabs.templateapp.databinding.ActivityNavHostBinding
import com.txusmslabs.templateapp.ui.common.EventObserver
import com.txusmslabs.templateapp.ui.common.SharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class NavHostActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private val sharedViewModel: SharedViewModel by viewModel()
    private lateinit var binding: ActivityNavHostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavHostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar as Toolbar)
        setupNavigation()

        sharedViewModel.navigateToLogin.observe(this, EventObserver {
            findNavController(R.id.nav_host_fragment).navigate(
                R.id.nav_graph,
                null,
                NavOptions.Builder().setPopUpTo(R.id.nav_graph, true).build()
            )
        })

    }

    override fun onSupportNavigateUp() =
        findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)

    private fun setupNavigation() {
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return

        // Set up Action Bar
        val navController = host.navController

        appBarConfiguration = AppBarConfiguration(setOf(R.id.mainFragment))

        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (listOf(R.id.detailFragment, R.id.splashFragment).contains(destination.id))
                binding.toolbar.visibility = View.GONE
            else
                binding.toolbar.visibility = View.VISIBLE
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        findNavController(R.id.nav_host_fragment).handleDeepLink(intent)
    }
}
