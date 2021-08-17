package com.camilobaquero.mytaxitest.api

import com.camilobaquero.mytaxitest.data.VehiclesEntity
import retrofit2.Response
import javax.inject.Inject

interface ApiHelper {
    suspend fun getVehicles(): Response<VehiclesEntity>
}

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
) : ApiHelper {
    override suspend fun getVehicles(): Response<VehiclesEntity> = apiService.getVehicles(
        p1Lat = 53.694865,
        p1Lon = 9.757589,
        p2Lat = 53.394655,
        p2Lon = 10.099891
    )
}
