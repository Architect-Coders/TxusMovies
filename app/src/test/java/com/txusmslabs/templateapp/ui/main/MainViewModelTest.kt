package com.txusmslabs.templateapp.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.refEq
import com.nhaarman.mockitokotlin2.whenever
import com.txusmslabs.domain.Movie
import com.txusmslabs.templateapp.ui.common.Event
import com.txusmslabs.testshared.mockedMovie
import com.txusmslabs.usecases.GetPopularMovies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule() // Para test LiveData

    @Mock
    lateinit var getPopularMovies: GetPopularMovies

    @Mock
    lateinit var observerPermission: Observer<Event<Unit>>

    @Mock
    lateinit var observerLoading: Observer<Boolean>

    @Mock
    lateinit var observerMovies: Observer<List<Movie>>

    private lateinit var vm: MainViewModel

    @Before
    fun setUp() {
        vm = MainViewModel(getPopularMovies, Dispatchers.Unconfined)
    }

    @Test
    fun `observing LiveData launches location permission request`() {

        vm.requestLocationPermission.observeForever(observerPermission)

        verify(observerPermission).onChanged(refEq(Event(Unit)))
    }

    @Test
    fun `after requesting the permission, loading is shown`() {
        runBlocking {

            val movies = listOf(mockedMovie.copy(id = 1))
            whenever(getPopularMovies.invoke()).thenReturn(movies)
            vm.loading.observeForever(observerLoading)

            vm.onCoarsePermissionRequested()

            verify(observerLoading).onChanged(true)
        }
    }

    @Test
    fun `after requesting the permission, getPopularMovies is called`() {

        runBlocking {
            val movies = listOf(mockedMovie.copy(id = 1))
            whenever(getPopularMovies.invoke()).thenReturn(movies)

            vm.movies.observeForever(observerMovies)

            vm.onCoarsePermissionRequested()

            verify(observerMovies).onChanged(movies)
        }
    }
}