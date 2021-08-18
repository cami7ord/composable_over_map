package com.camilobaquero.mytaxitest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.camilobaquero.mytaxitest.data.VehicleModel
import com.camilobaquero.mytaxitest.repository.VehiclesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val vehiclesRepository: VehiclesRepository
) : ViewModel() {

    private val _vehicles = MutableLiveData<List<VehicleModel>>()
    val vehicles: LiveData<List<VehicleModel>> = _vehicles

    private val _selectedVehicle = MutableLiveData<VehicleModel?>()
    val selectedVehicle: LiveData<VehicleModel?> = _selectedVehicle

    val listVisibility: LiveData<Boolean> = map(selectedVehicle) { selectedVehicle ->
        selectedVehicle == null
    }

    init {
        getVehicles()
    }

    private fun getVehicles() = viewModelScope.launch {
        vehiclesRepository.getVehicles().let {
            it.body()?.data?.let { vehiclesList ->
                _vehicles.postValue(vehiclesList)
                _selectedVehicle.postValue(null)
            }
        }
    }

    fun onVehicleSelected(vehicle: VehicleModel) {
        _selectedVehicle.postValue(vehicle)
    }

    fun onShowListClicked() {
        _selectedVehicle.postValue(null)
    }

}
