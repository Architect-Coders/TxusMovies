package com.txusmslabs.templateapp.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.whenever
import com.txusmslabs.data.functional.Either
import com.txusmslabs.templateapp.CoroutinesTestRule
import com.txusmslabs.templateapp.ui.common.Event
import com.txusmslabs.testshared.mockedMovie
import com.txusmslabs.usecases.GetPopularMovies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var getPopularMovies: GetPopularMovies

    @Mock
    lateinit var observerPermission: Observer<Event<Unit>>

    private lateinit var vm: MainViewModel

    @Before
    fun setUp() {
        vm = MainViewModel(getPopularMovies, Dispatchers.Unconfined)
    }

    @Test
    fun `observing LiveData launches location permission request`() {

        vm.requestLocationPermission.observeForever(observerPermission)

        verify(observerPermission).onChanged(Event(Unit))
    }

    @Test
    fun `after requesting the permission, loading is shown`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {

            val movies = listOf(mockedMovie.copy(id = 1))
            whenever(getPopularMovies.invoke()).thenReturn(Either.Right(movies))

            val states = arrayListOf<MainViewState>()
            val job = launch {
                vm.uiState.toList(states) //now it should work
            }

            vm.onCoarsePermissionRequested()

            assertTrue(states.size > 0)
            assertTrue(states[1].loading)

            job.cancel()
        }

    @Test
    fun `after requesting the permission, getPopularMovies is called`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {

            val movies = listOf(mockedMovie.copy(id = 1))
            whenever(getPopularMovies.invoke()).thenReturn(Either.Right(movies))

            vm.onCoarsePermissionRequested()

            val s = vm.uiState.first()
            assertEquals(movies, s.movies)
        }
}