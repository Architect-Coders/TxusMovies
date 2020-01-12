package com.txusmslabs.data.source

interface LocationDataSource {
    suspend fun findLastRegion(): String?
}