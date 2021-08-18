package com.camilobaquero.mytaxitest

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.lifecycleScope
import com.camilobaquero.mytaxitest.data.FleetTypeEnum
import com.camilobaquero.mytaxitest.data.VehicleModel
import com.camilobaquero.mytaxitest.databinding.ActivityMapsBinding
import com.camilobaquero.mytaxitest.ui.MainViewModel
import com.camilobaquero.mytaxitest.ui.components.CarsList
import com.camilobaquero.mytaxitest.util.Constants
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private val mainViewModel: MainViewModel by viewModels()
    private var isMapReady: Boolean = false

    private val markerColorAzure by lazy {
        BitmapDescriptorFactory
            .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
    }

    private val markerColorYellow by lazy {
        BitmapDescriptorFactory
            .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)
    }

    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Cars list composable
        val carsList = findViewById<ComposeView>(R.id.carsList)
        carsList.setContent {
            CarsList(mainViewModel)
        }

        // Observers
        mainViewModel.vehicles.observe(this) { list ->
            list?.let {
                if (isMapReady) {
                    it.forEach { vehicle ->
                        addVehicleMarkerToMap(vehicle)
                    }
                }
            }
        }

        mainViewModel.selectedVehicle.observe(this) { vehicleModel ->
            vehicleModel?.let {
                if (isMapReady) {
                    lifecycleScope.launch {
                        delay(500)
                        animateCameraToSelectedVehicle(vehicleModel)
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.list_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_list -> {
                mainViewModel.onShowListClicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addVehicleMarkerToMap(vehicle: VehicleModel) {
        val markerColor = when (vehicle.fleetType) {
            FleetTypeEnum.POOLING -> markerColorAzure
            FleetTypeEnum.TAXI -> markerColorYellow
        }
        mMap.addMarker(
            MarkerOptions().position(
                LatLng(vehicle.coordinate.latitude, vehicle.coordinate.longitude)
            ).title(vehicle.id.toString()).icon(markerColor)
        )
    }

    private fun animateCameraToSelectedVehicle(vehicle: VehicleModel) {
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(vehicle.coordinate.latitude, vehicle.coordinate.longitude),
                15f
            )
        )
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        isMapReady = true

        val hamburg = LatLngBounds(
            LatLng(Constants.P2LAT, Constants.P1LON),
            LatLng(Constants.P1LAT, Constants.P2LON)
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(hamburg, 8))
    }
}
