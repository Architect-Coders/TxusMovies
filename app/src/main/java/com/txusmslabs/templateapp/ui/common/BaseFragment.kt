package com.txusmslabs.templateapp.ui.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import org.koin.androidx.scope.ScopeFragment
import kotlin.coroutines.CoroutineContext

abstract class BaseFragment: ScopeFragment(), CoroutineScope {

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
}