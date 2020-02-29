package com.txusmslabs.templateapp.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.whenever
import com.txusmslabs.domain.Movie
import com.txusmslabs.testshared.mockedMovie
import com.txusmslabs.usecases.FindMovieById
import com.txusmslabs.usecases.ToggleMovieFavorite
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
class DetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var findMovieById: FindMovieById

    @Mock
    lateinit var toggleMovieFavorite: ToggleMovieFavorite

    @Mock
    lateinit var observerMovie: Observer<Movie>

    @Mock
    lateinit var observerNotFoundMovie: Observer<Boolean>

    private lateinit var vm: DetailViewModel

    @Before
    fun setUp() {
        vm = DetailViewModel(1, findMovieById, toggleMovieFavorite, Dispatchers.Unconfined)
    }

    @Test
    fun `observing LiveData finds the movie`() {

        runBlocking {
            val movie = mockedMovie.copy(id = 1)
            whenever(findMovieById.invoke(1)).thenReturn(movie)

            vm.movie.observeForever(observerMovie)

            verify(observerMovie).onChanged(movie)
        }
    }

    @Test
    fun `observing LiveData not finds the movie`() {
        runBlocking {
            whenever(findMovieById.invoke(1)).thenReturn(null)

            vm.movie.observeForever(observerMovie)
            vm.notFound.observeForever(observerNotFoundMovie)

            verify(observerNotFoundMovie).onChanged(true)
        }
    }

    @Test
    fun `when favorite clicked, the toggleMovieFavorite use case is invoked`() {
        runBlocking {
            val movie = mockedMovie.copy(id = 1)
            whenever(findMovieById.invoke(1)).thenReturn(movie)
            whenever(toggleMovieFavorite.invoke(movie)).thenReturn(movie.copy(favorite = !movie.favorite))
            vm.movie.observeForever(observerMovie)

            vm.onFavoriteClicked()

            verify(toggleMovieFavorite).invoke(movie)
        }
    }
}