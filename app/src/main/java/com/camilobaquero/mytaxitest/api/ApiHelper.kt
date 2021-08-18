package com.camilobaquero.mytaxitest.api

import com.camilobaquero.mytaxitest.data.VehiclesEntity
import com.camilobaquero.mytaxitest.util.Constants
import retrofit2.Response
import javax.inject.Inject

interface ApiHelper {
    suspend fun getVehicles(): Response<VehiclesEntity>
}

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
) : ApiHelper {
    override suspend fun getVehicles(): Response<VehiclesEntity> = apiService.getVehicles(
        p1Lat = Constants.P1LAT,
        p1Lon = Constants.P1LON,
        p2Lat = Constants.P2LAT,
        p2Lon = Constants.P2LON
    )
}
