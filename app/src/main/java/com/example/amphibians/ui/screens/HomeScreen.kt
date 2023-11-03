package com.example.amphibians.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.amphibians.R
import com.example.amphibians.model.Amphibian

@Composable
fun HomeScreen(amphibiansUiState: AmphibiansUiState, updateAmphibians: () -> Unit, modifier: Modifier = Modifier) {

    when (amphibiansUiState) {

        AmphibiansUiState.Error -> ErrorScreen(updateAmphibians, modifier = Modifier.fillMaxSize())

        AmphibiansUiState.Loading -> LoadingScreen(modifier = Modifier.fillMaxSize())

        is AmphibiansUiState.Success -> AmphibiansInfoScreen(amphibians = amphibiansUiState.amphibians)
    }

}

@Composable
fun AmphibiansInfoScreen(amphibians: List<Amphibian>, modifier: Modifier = Modifier) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 8.dp),

        modifier = modifier) {
        items(amphibians) {
            AmphibianInfoCard(it, modifier=Modifier.padding(8.dp))
        }
    }
}

@Composable
fun AmphibianInfoCard(amphibian: Amphibian, modifier: Modifier = Modifier) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = CutCornerShape(4.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {

            Text(
                text = "${amphibian.name} (${amphibian.type})",
                maxLines = 1,
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)

            )

            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(amphibian.imgSrc)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_connection_error),
                placeholder = painterResource(id = R.drawable.loading_img),

                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Text(
                text = amphibian.description,
                modifier = Modifier.padding(8.dp))

        }
    }
}

@Composable
fun ErrorScreen(updateAmphibians: ()-> Unit, modifier: Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error),
            contentDescription = null
        )
        Text(
            text = stringResource(id = R.string.loading_error),
            modifier = Modifier.padding(16.dp)
        )
        Button(
            onClick = { updateAmphibians },
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                Icons.Default.Refresh,
                contentDescription = stringResource(id = R.string.retry),
                modifier = Modifier.padding(end=4.dp)
            )
            Text(text = stringResource(id = R.string.retry))
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier) {
    CircularProgressIndicator(
        modifier = Modifier.size(50.dp),
        color = MaterialTheme.colorScheme.primary
    )
}

@Preview
@Composable
fun HomeScreenPreview() {
    val mockData=List(10){Amphibian(name = "$it", imgSrc = "", type = "road", description = "description")}
    AmphibiansInfoScreen(amphibians = mockData)
}
@Preview
@Composable
fun AmphibianCardPreview() {
    AmphibianInfoCard(
        amphibian = Amphibian(
            name = "Great Basin Spadefoot",
            description = "This toad spends most of its life underground due to the arid desert conditions in which it lives. Spadefoot toads earn the name because of their hind legs which are wedged to aid in digging. They are typically grey, green, or brown with dark spots.",
            imgSrc = "https://developer.android.com/codelabs/basic-android-kotlin-compose-amphibians-app/img/great-basin-spadefoot.png",
            type = "Toad"
        )
    )
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    ErrorScreen(updateAmphibians = {}, modifier = Modifier)
}

@Preview
@Composable
fun LoadingScreenPreview() {
    LoadingScreen(modifier = Modifier)
}