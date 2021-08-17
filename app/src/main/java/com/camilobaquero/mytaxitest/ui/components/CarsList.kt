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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.camilobaquero.mytaxitest.data.VehicleModel


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
        Image(
            painter = rememberImagePainter(vehicle.url),
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )
        Column {
            Text(text = vehicle.name, style = MaterialTheme.typography.subtitle1)
            Text(text = vehicle.name)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CarListItem(
        vehicle = VehicleModel(
            name = "Carro 1",
            "https://p.kindpng.com/picc/s/179-1793936_clip-art-carro-de-cor-carro-png-transparent.png"
        )
    )
}
