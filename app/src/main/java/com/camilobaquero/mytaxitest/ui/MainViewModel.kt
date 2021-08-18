package com.camilobaquero.mytaxitest.ui

import androidx.lifecycle.*
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

    private val _listVisibility = MutableLiveData<Boolean>()
    val listVisibility = MediatorLiveData<Boolean>()

    init {

        listVisibility.addSource(_selectedVehicle) { selectedVehicle ->
            listVisibility.postValue(selectedVehicle == null)
        }

        listVisibility.addSource(_listVisibility) { visible ->
            listVisibility.postValue(visible)
        }

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
        _listVisibility.postValue(true)
    }

    fun onMapClicked() {
        _listVisibility.postValue(false)
    }

}
