package com.camilobaquero.mytaxitest.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.camilobaquero.mytaxitest.data.VehicleModel
import com.camilobaquero.mytaxitest.ui.MainViewModel

@ExperimentalAnimationApi
@Composable
fun CarsList(
    mainViewModel: MainViewModel
) {
    Card(
        elevation = 10.dp,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        val listState = rememberLazyListState()
        val cars: List<VehicleModel> by mainViewModel.vehicles.observeAsState(emptyList())
        val visible: Boolean by mainViewModel.listVisibility.observeAsState(false)

        AnimatedVisibility(
            visible = visible
        ) {
            LazyColumn(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.White), state = listState
            ) {
                items(cars) { car ->
                    CarListItem(
                        car = car,
                        onCarSelected = { vehicle -> mainViewModel.onVehicleSelected(vehicle) }
                    )
                }
            }
        }
    }
}
