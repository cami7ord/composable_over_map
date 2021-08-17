package com.camilobaquero.mytaxitest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.platform.ComposeView
import com.camilobaquero.mytaxitest.data.VehicleModel
import com.camilobaquero.mytaxitest.databinding.ActivityMapsBinding
import com.camilobaquero.mytaxitest.ui.components.CarsList
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Cars list
        val carsList = findViewById<ComposeView>(R.id.carsList)
        carsList.setContent {
            CarsList(
                cars = listOf(
                    VehicleModel(
                        "Vehicle 1",
                        "https://p.kindpng.com/picc/s/179-1793936_clip-art-carro-de-cor-carro-png-transparent.png"
                    ),
                    VehicleModel(
                        "Vehicle 2",
                        "https://p.kindpng.com/picc/s/179-1793936_clip-art-carro-de-cor-carro-png-transparent.png"
                    ),
                    VehicleModel(
                        "Vehicle 3",
                        "https://p.kindpng.com/picc/s/179-1793936_clip-art-carro-de-cor-carro-png-transparent.png"
                    ),
                )
            )
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}