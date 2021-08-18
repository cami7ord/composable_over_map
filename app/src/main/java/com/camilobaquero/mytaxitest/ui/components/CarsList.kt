package com.camilobaquero.mytaxitest.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.camilobaquero.mytaxitest.R
import com.camilobaquero.mytaxitest.data.FleetTypeEnum
import com.camilobaquero.mytaxitest.data.LocationDirectionEnum
import com.camilobaquero.mytaxitest.data.LocationDirectionEnum.*
import com.camilobaquero.mytaxitest.data.VehicleModel
import com.camilobaquero.mytaxitest.util.Constants


@Composable
fun CarsList(
    cars: List<VehicleModel>
) {
    Card(
        elevation = 10.dp,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        val listState = rememberLazyListState()

        LazyColumn(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.White), state = listState
        ) {
            items(cars) { car ->
                CarListItem(car)
            }
        }
    }
}

@Composable
fun CarListItem(vehicle: VehicleModel) {
    Row(verticalAlignment = Alignment.CenterVertically) {

        val image = when (vehicle.fleetType) {
            FleetTypeEnum.POOLING -> Constants.POOL_IMAGE_URL
            FleetTypeEnum.TAXI -> Constants.TAXI_IMAGE_URL
        }

        Image(
            painter = rememberImagePainter(image),
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )

        Spacer(Modifier.width(16.dp))

        val locationDirectionResource = when (computeDirection(vehicle.heading)) {
            UNKNOWN -> stringResource(id = R.string.unknown)
            NORTH -> stringResource(id = R.string.north)
            NORTH_EAST -> stringResource(id = R.string.north_east)
            EAST -> stringResource(id = R.string.east)
            SOUTH_EAST -> stringResource(id = R.string.south_east)
            SOUTH -> stringResource(id = R.string.south)
            SOUTH_WEST -> stringResource(id = R.string.south_west)
            WEST -> stringResource(id = R.string.west)
            NORTH_WEST -> stringResource(id = R.string.north_west)
        }

        Column {
            Text(text = vehicle.id.toString(), style = MaterialTheme.typography.subtitle1)
            Text(text = stringResource(R.string.heading_to, locationDirectionResource))
        }
    }
}

private fun computeDirection(heading: Double): LocationDirectionEnum {
    val delta = 22.5
    return when {
        ((heading >= 0 && heading < delta) || (heading < 0 && heading >= -delta)) -> NORTH
        (heading >= delta && heading < 90 - delta) -> NORTH_EAST
        (heading >= 90 - delta && heading < 90 + delta) -> EAST
        (heading >= 90 + delta && heading < 180 - delta) -> SOUTH_EAST
        (heading >= 180 - delta || heading <= -180 + delta) -> SOUTH
        (heading >= -180 + delta && heading < -90 - delta) -> SOUTH_WEST
        (heading >= -90 - delta && heading < -90 + delta) -> WEST
        (heading >= -90 + delta && heading < -delta) -> NORTH_WEST
        else -> UNKNOWN
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    CarListItem(
//        vehicle = VehicleModel(
//
//        )
//    )
//}
