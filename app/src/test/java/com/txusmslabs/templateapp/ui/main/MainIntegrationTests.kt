package com.txusmslabs.templateapp.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.txusmslabs.data.source.LocalDataSource
import com.txusmslabs.templateapp.CoroutinesTestRule
import com.txusmslabs.templateapp.FakeLocalDataSource
import com.txusmslabs.templateapp.defaultFakeMovies
import com.txusmslabs.templateapp.initMockedDi
import com.txusmslabs.testshared.mockedMovie
import com.txusmslabs.usecases.GetPopularMovies
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainIntegrationTests : KoinTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

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
    fun `data is loaded from server when local source is empty`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            vm.onCoarsePermissionRequested()

            val s = vm.uiState.first()
            assertEquals(defaultFakeMovies, s.movies)
        }

    @Test
    fun `data is loaded from local source when available`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val fakeLocalMovies = listOf(mockedMovie.copy(10), mockedMovie.copy(11))
            val localDataSource = get<LocalDataSource>() as FakeLocalDataSource
            localDataSource.movies = fakeLocalMovies

            vm.onCoarsePermissionRequested()

            val s = vm.uiState.first()
            assertEquals(fakeLocalMovies, s.movies)
        }
}