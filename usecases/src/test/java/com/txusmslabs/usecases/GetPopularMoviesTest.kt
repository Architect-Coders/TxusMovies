package com.txusmslabs.usecases

import com.nhaarman.mockitokotlin2.whenever
import com.txusmslabs.data.functional.Either
import com.txusmslabs.data.repository.MoviesRepository
import com.txusmslabs.testshared.mockedMovie
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetPopularMoviesTest {

    @Mock
    lateinit var moviesRepository: MoviesRepository

    lateinit var getPopularMovies: GetPopularMovies

    @Before
    fun setUp() {
        getPopularMovies = GetPopularMovies(moviesRepository)
    }

    @Test
    fun `invoke calls movies repository`() {
        runBlocking {

            val movies = listOf(mockedMovie.copy(id = 1))
            whenever(moviesRepository.suspendPopularMovies()).thenReturn(Either.Right(movies))

            val result = getPopularMovies.invoke()

            Assert.assertEquals(movies, result.getRight())
        }
    }

}