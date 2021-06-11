package com.txusmslabs.templateapp.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.txusmslabs.data.source.LocalDataSource
import com.txusmslabs.templateapp.CoroutinesTestRule
import com.txusmslabs.templateapp.FakeLocalDataSource
import com.txusmslabs.templateapp.defaultFakeMovies
import com.txusmslabs.templateapp.initMockedDi
import com.txusmslabs.testshared.mockedMovie
import com.txusmslabs.usecases.FindMovieById
import com.txusmslabs.usecases.ToggleMovieFavorite
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class DetailIntegrationTests : KoinTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var vm: DetailViewModel
    private lateinit var localDataSource: FakeLocalDataSource

    @Before
    fun setUp() {
        val vmModule = module {
            factory { (id: Int) -> DetailViewModel(id, get(), get(), get()) }
            factory { FindMovieById(get()) }
            factory { ToggleMovieFavorite(get()) }
        }

        initMockedDi(vmModule)
        vm = get { parametersOf(1) }

        localDataSource = get<LocalDataSource>() as FakeLocalDataSource
        localDataSource.movies = defaultFakeMovies
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `observing StateFlow finds the movie`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            vm.findMovie()
            val s = vm.uiState.first()

            assertEquals(mockedMovie.copy(1), s.movie)
        }

    @Test
    fun `favorite is updated in local data source`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            vm.findMovie()
            vm.onFavoriteClicked()

            assertTrue(localDataSource.findById(1)?.favorite == true)
        }
}