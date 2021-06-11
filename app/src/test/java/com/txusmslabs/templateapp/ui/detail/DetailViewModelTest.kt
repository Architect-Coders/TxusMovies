package com.txusmslabs.templateapp.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.whenever
import com.txusmslabs.templateapp.CoroutinesTestRule
import com.txusmslabs.testshared.mockedMovie
import com.txusmslabs.usecases.FindMovieById
import com.txusmslabs.usecases.ToggleMovieFavorite
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
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

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class DetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var findMovieById: FindMovieById

    @Mock
    lateinit var toggleMovieFavorite: ToggleMovieFavorite

    private lateinit var vm: DetailViewModel

    @Before
    fun setUp() {

        vm = DetailViewModel(
            1,
            findMovieById,
            toggleMovieFavorite,
            coroutinesTestRule.testDispatcher
        )
    }

    @Test
    fun `Given movie id 1, when open detail, then load details of the movie`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {

            val movie = mockedMovie.copy(id = 1)
            whenever(findMovieById.invoke(1)).thenReturn(movie)

            vm.findMovie()

            val s = vm.uiState.first()
            assertEquals(movie, s.movie)

        }

    @Test
    fun `observing UI State not finds the movie`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            whenever(findMovieById.invoke(1)).thenReturn(null)

            vm.findMovie()
            val s = vm.uiState.first()

            assertTrue(s.notFound)
        }

    @Test
    fun `when favorite clicked, the toggleMovieFavorite use case is invoked`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val movie = mockedMovie.copy(id = 1)
            whenever(findMovieById.invoke(1)).thenReturn(movie)
            whenever(toggleMovieFavorite.invoke(movie)).thenReturn(movie.copy(favorite = !movie.favorite))

            vm.findMovie()
            vm.onFavoriteClicked()

            verify(toggleMovieFavorite).invoke(movie)
        }

    @Test
    fun `when favorite clicked, the favorite flag is true`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val movie = mockedMovie.copy(id = 1)
            whenever(findMovieById.invoke(1)).thenReturn(movie)
            whenever(toggleMovieFavorite.invoke(movie)).thenReturn(movie.copy(favorite = !movie.favorite))

            vm.findMovie()
            vm.onFavoriteClicked()

            val s = vm.uiState.first()
            verify(toggleMovieFavorite).invoke(movie)
            assertTrue(s.movie?.favorite == true)
        }
}