package com.camilobaquero.mytaxitest.api

import com.camilobaquero.mytaxitest.data.VehiclesEntity
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("vehicles")
    suspend fun getVehicles(): Response<VehiclesEntity>
}
