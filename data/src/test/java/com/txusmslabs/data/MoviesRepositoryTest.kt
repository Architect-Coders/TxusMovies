package com.txusmslabs.data

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.txusmslabs.data.functional.Either
import com.txusmslabs.data.repository.MoviesRepository
import com.txusmslabs.data.repository.RegionRepository
import com.txusmslabs.data.source.LocalDataSource
import com.txusmslabs.data.source.RemoteDataSource
import com.txusmslabs.testshared.mockedMovie
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MoviesRepositoryTest {

    @Mock
    private lateinit var localDataSource: LocalDataSource

    @Mock
    private lateinit var remoteDataSource: RemoteDataSource

    @Mock
    private lateinit var regionRepository: RegionRepository

    private lateinit var moviesRepository: MoviesRepository

    private val apiKey = "1a2b3c4d"

    @Before
    fun setUp() {
        moviesRepository =
            MoviesRepository(localDataSource, remoteDataSource, regionRepository, apiKey)
    }

    @Test
    fun `getPopularMovies gets from local data source first`() {
        runBlocking {

            val localMovies = listOf(mockedMovie.copy(1))
            whenever(localDataSource.isEmpty()).thenReturn(false)
            whenever(localDataSource.getPopularMovies()).thenReturn(localMovies)

            val result = moviesRepository.suspendPopularMovies()

            assertEquals(localMovies, result.getRight())
        }
    }

    @Test
    fun `getPopularMovies saves remote data to local`() {
        runBlocking {

            val remoteMovies = listOf(mockedMovie.copy(2))
            whenever(localDataSource.isEmpty()).thenReturn(true)
            whenever(remoteDataSource.getMovies(any(), any())).thenReturn(Either.Right(remoteMovies))
            whenever(regionRepository.findLastRegion()).thenReturn("US")

            moviesRepository.suspendPopularMovies()

            verify(localDataSource).saveMovies(remoteMovies)
        }
    }

    @Test
    fun `findById calls local data source`() {
        runBlocking {

            val movie = mockedMovie.copy(id = 5)
            whenever(localDataSource.findById(5)).thenReturn(movie)

            val result = moviesRepository.findById(5)

            assertEquals(movie, result)
        }
    }

    @Test
    fun `update updates local data source`() {
        runBlocking {

            val movie = mockedMovie.copy(id = 1)
            whenever(localDataSource.update(movie)).thenReturn(true)

            moviesRepository.update(movie)

            verify(localDataSource).update(movie)
        }
    }
}