package com.txusmslabs.templateapp.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.GrantPermissionRule
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.txusmslabs.templateapp.R
import com.txusmslabs.templateapp.framework.data.server.TheMovieDb
import com.txusmslabs.templateapp.utils.*
import okhttp3.mockwebserver.MockResponse
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.get

class UiTest : KoinTest {

    @get:Rule
    val mockWebServerRule = MockWebServerRule()

    @get:Rule
    val grantPermissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(
            "android.permission.ACCESS_COARSE_LOCATION"
        )

    private val dataBindingIdlingResource = DataBindingIdlingResource()
    private val splashIdlingResource = SplashIdlingResource()

    @Before
    fun setUp() {
        mockWebServerRule.server.enqueue(
            MockResponse().fromJson(
                ApplicationProvider.getApplicationContext(),
                "popularmovies.json"
            )
        )

        val resource = OkHttp3IdlingResource.create("OkHttp", get<TheMovieDb>().okHttpClient)
        IdlingRegistry.getInstance().register(resource)
    }

    /**
     * Idling resources tell Espresso that the app is idle or busy. This is needed when operations
     * are not scheduled in the main Looper (for example when executed on a different thread).
     */
    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
        IdlingRegistry.getInstance().register(splashIdlingResource)
    }

    /**
     * Unregister your Idling Resource so it can be garbage collected and does not leak any memory.
     */
    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
        IdlingRegistry.getInstance().unregister(splashIdlingResource)
    }

    @Test
    fun clickAMovieNavigatesToDetail() {
        val activityScenario = ActivityScenario.launch(NavHostActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)
        splashIdlingResource.monitorNavController(activityScenario)

        Thread.sleep(1000)
        onView(withId(R.id.recycler)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                4,
                click()
            )
        )

        onView(withId(R.id.movieDetailToolbar))
            .check(matches(hasDescendant(withText("Spider-Man: Far from Home"))))

        // When using ActivityScenario.launch, always call close()
        activityScenario.close()
    }
}
