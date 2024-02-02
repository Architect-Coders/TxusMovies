package com.txusmslabs.templateapp.ui.common

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import org.koin.androidx.scope.ScopeFragment
import kotlin.coroutines.CoroutineContext

abstract class BaseFragment: ScopeFragment(), CoroutineScope {

    private val navController: NavController by lazy { Navigation.findNavController(requireView()) }
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    open fun initFlows() {

    }

    override fun onStart() {
        super.onStart()
        job = SupervisorJob()
        initFlows()
    }

    override fun onStop() {
        job.cancel()
        super.onStop()
    }

    fun navigate(navDirections: NavDirections) {
        navController.navigate(navDirections)
    }

    fun navigate(resId: Int, args: Bundle? = null) {
        navController.navigate(resId, args)
    }
}