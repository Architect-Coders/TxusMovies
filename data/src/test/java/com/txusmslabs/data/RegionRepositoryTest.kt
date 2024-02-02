package com.txusmslabs.data

import com.nhaarman.mockitokotlin2.whenever
import com.txusmslabs.data.repository.PermissionChecker
import com.txusmslabs.data.repository.PermissionChecker.Permission.COARSE_LOCATION
import com.txusmslabs.data.repository.RegionRepository
import com.txusmslabs.data.source.LocationDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.time.ZonedDateTime
import android.Manifest.permission

import jdk.nashorn.internal.objects.NativeRegExp.source
import java.time.ZoneId
import android.Manifest.permission

import jdk.nashorn.internal.objects.NativeRegExp.source
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import android.Manifest.permission

import jdk.nashorn.internal.objects.NativeRegExp.source
import java.util.*
import android.Manifest.permission

import jdk.nashorn.internal.objects.NativeRegExp.source





@RunWith(MockitoJUnitRunner::class)
class RegionRepositoryTest {

    @Mock
    lateinit var locationDataSource: LocationDataSource

    @Mock
    lateinit var permissionChecker: PermissionChecker

    private lateinit var regionRepository: RegionRepository

    @Before
    fun setUp() {
        regionRepository = RegionRepository(locationDataSource, permissionChecker)
    }

    @Test
    fun `returns default when coarse permission not granted`() {
        runBlocking {

            whenever(permissionChecker.check(COARSE_LOCATION)).thenReturn(false)

            val region = regionRepository.findLastRegion()

            assertEquals(RegionRepository.DEFAULT_REGION, region)
        }
    }

    @Test
    fun `returns region from location data source when permission granted`() {
        runBlocking {

            whenever(permissionChecker.check(COARSE_LOCATION)).thenReturn(true)
            whenever(locationDataSource.findLastRegion()).thenReturn("ES")

            val region = regionRepository.findLastRegion()

            assertEquals("ES", region)
        }
    }

    @Test
    fun `returns date formatted`() {
        val zoneId: ZoneId = ZoneId.of("Europe/Madrid")
        val zdt: ZonedDateTime = ZonedDateTime.now(zoneId)
        var formatter: DateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
        val locale = Locale("es", "ES")
        formatter = formatter.withLocale(locale)
        val output = zdt.format(formatter)
        assertEquals("fdfd", output)
    }

}