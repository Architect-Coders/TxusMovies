package com.txusmslabs.templateapp.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.txusmslabs.data.source.LocalDataSource
import com.txusmslabs.domain.Movie
import com.txusmslabs.templateapp.FakeLocalDataSource
import com.txusmslabs.templateapp.defaultFakeMovies
import com.txusmslabs.templateapp.initMockedDi
import com.txusmslabs.testshared.mockedMovie
import com.txusmslabs.usecases.FindMovieById
import com.txusmslabs.usecases.ToggleMovieFavorite
import kotlinx.coroutines.runBlocking
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
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailIntegrationTests: KoinTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<Movie>

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
    fun `observing LiveData finds the movie`() {
        vm.movie.observeForever(observer)

        verify(observer).onChanged(mockedMovie.copy(1))
    }

    @Test
    fun `favorite is updated in local data source`() {
        vm.movie.observeForever(observer)

        vm.onFavoriteClicked()

        runBlocking {
            assertTrue(localDataSource.findById(1).favorite)
        }
    }
}