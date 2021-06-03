package com.txusmslabs.templateapp.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.txusmslabs.data.source.LocalDataSource
import com.txusmslabs.domain.Movie
import com.txusmslabs.templateapp.FakeLocalDataSource
import com.txusmslabs.templateapp.defaultFakeMovies
import com.txusmslabs.templateapp.initMockedDi
import com.txusmslabs.testshared.mockedMovie
import com.txusmslabs.usecases.GetPopularMovies
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class MainIntegrationTests: KoinTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var observerMovies: Observer<List<Movie>>

    private lateinit var vm: MainViewModel

    @Before
    fun setUp() {
        val vmModule = module {
            factory { MainViewModel(get(), get()) }
            factory { GetPopularMovies(get()) }
        }

        initMockedDi(vmModule)
        vm = get()
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `data is loaded from server when local source is empty`() {
        vm.movies.observeForever(observerMovies)

        vm.onCoarsePermissionRequested()

        verify(observerMovies).onChanged(defaultFakeMovies)
    }

    @Test
    fun `data is loaded from local source when available`() {
        val fakeLocalMovies = listOf(mockedMovie.copy(10), mockedMovie.copy(11))
        val localDataSource = get<LocalDataSource>() as FakeLocalDataSource
        localDataSource.movies = fakeLocalMovies
        vm.movies.observeForever(observerMovies)

        vm.onCoarsePermissionRequested()

        verify(observerMovies).onChanged(fakeLocalMovies)
    }
}