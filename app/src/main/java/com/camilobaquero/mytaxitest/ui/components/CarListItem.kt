package com.camilobaquero.mytaxitest.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.camilobaquero.mytaxitest.R
import com.camilobaquero.mytaxitest.data.CoordinateEntity
import com.camilobaquero.mytaxitest.data.FleetTypeEnum
import com.camilobaquero.mytaxitest.data.LocationDirectionEnum
import com.camilobaquero.mytaxitest.data.VehicleModel
import com.camilobaquero.mytaxitest.util.Constants

@Composable
fun CarListItem(
    car: VehicleModel,
    onCarSelected: (VehicleModel) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onCarSelected(car)
            }
    ) {

        val image = when (car.fleetType) {
            FleetTypeEnum.POOLING -> Constants.POOL_IMAGE_URL
            FleetTypeEnum.TAXI -> Constants.TAXI_IMAGE_URL
        }

        Image(
            painter = rememberImagePainter(image),
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )

        val locationDirectionResource = when (computeDirection(car.heading)) {
            LocationDirectionEnum.UNKNOWN -> stringResource(id = R.string.unknown)
            LocationDirectionEnum.NORTH -> stringResource(id = R.string.north)
            LocationDirectionEnum.NORTH_EAST -> stringResource(id = R.string.north_east)
            LocationDirectionEnum.EAST -> stringResource(id = R.string.east)
            LocationDirectionEnum.SOUTH_EAST -> stringResource(id = R.string.south_east)
            LocationDirectionEnum.SOUTH -> stringResource(id = R.string.south)
            LocationDirectionEnum.SOUTH_WEST -> stringResource(id = R.string.south_west)
            LocationDirectionEnum.WEST -> stringResource(id = R.string.west)
            LocationDirectionEnum.NORTH_WEST -> stringResource(id = R.string.north_west)
        }

        Column {
            Text(text = car.id.toString(), style = MaterialTheme.typography.subtitle1)
            Text(text = stringResource(R.string.heading_to, locationDirectionResource))
        }
    }
}

private fun computeDirection(heading: Double): LocationDirectionEnum {
    val delta = 22.5
    return when {
        ((heading >= 0 && heading < delta) || (heading < 0 && heading >= -delta)) -> LocationDirectionEnum.NORTH
        (heading >= delta && heading < 90 - delta) -> LocationDirectionEnum.NORTH_EAST
        (heading >= 90 - delta && heading < 90 + delta) -> LocationDirectionEnum.EAST
        (heading >= 90 + delta && heading < 180 - delta) -> LocationDirectionEnum.SOUTH_EAST
        (heading >= 180 - delta || heading <= -180 + delta) -> LocationDirectionEnum.SOUTH
        (heading >= -180 + delta && heading < -90 - delta) -> LocationDirectionEnum.SOUTH_WEST
        (heading >= -90 - delta && heading < -90 + delta) -> LocationDirectionEnum.WEST
        (heading >= -90 + delta && heading < -delta) -> LocationDirectionEnum.NORTH_WEST
        else -> LocationDirectionEnum.UNKNOWN
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CarListItem(
        car = VehicleModel(
            id = 592481,
            coordinate = CoordinateEntity(53.833, 10.678),
            fleetType = FleetTypeEnum.TAXI,
            heading = 324.090196139077
        ),
        onCarSelected = {}
    )
}
