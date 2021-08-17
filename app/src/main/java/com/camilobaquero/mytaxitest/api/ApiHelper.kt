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
    override suspend fun getVehicles(): Response<VehiclesEntity> = apiService.getVehicles()
}
