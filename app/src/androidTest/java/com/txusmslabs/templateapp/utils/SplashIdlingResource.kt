package com.txusmslabs.templateapp.utils

import android.os.Handler
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.IdlingResource
import com.txusmslabs.templateapp.R
import java.util.*

class SplashIdlingResource : IdlingResource {

    // list of registered callbacks
    private val idlingCallbacks = mutableListOf<IdlingResource.ResourceCallback>()

    // give it a unique id to workaround an espresso bug where you cannot register/unregister
    // an idling resource w/ the same name.
    private val id = UUID.randomUUID().toString()

    // holds whether isIdle is called and the result was false. We track this to avoid calling
    // onTransitionToIdle callbacks if Espresso never thought we were idle in the first place.
    private var wasNotIdle = false

    lateinit var navController: NavController

    override fun getName() = "SplashIdle $id"

    override fun isIdleNow(): Boolean {

        val idle =
            navController.currentDestination?.id != R.id.splashFragment

        @Suppress("LiftReturnOrAssignment")
        if (idle) {
            if (wasNotIdle) {
                // notify observers to avoid espresso race detector
                idlingCallbacks.forEach { it.onTransitionToIdle() }
            }
            wasNotIdle = false
        } else {
            wasNotIdle = true
            // check next frame
            Handler().postDelayed({
                isIdleNow
            }, 500)
        }
        return idle
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback) {
        idlingCallbacks.add(callback)
    }
}

/**
 * Sets the activity from an [ActivityScenario] to be used from [SplashIdlingResource].
 */
fun SplashIdlingResource.monitorNavController(
    activityScenario: ActivityScenario<out FragmentActivity>
) {
    activityScenario.onActivity {
        this.navController = it.findNavController(R.id.nav_host_fragment)
    }
}