package com.dicoding.jetpack.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.dicoding.jetpack.di.Injection
import com.dicoding.jetpack.model.Daerah
import com.example.jetpack.ui.ViewModelFactory
import com.dicoding.jetpack.ui.common.UiState
import com.dicoding.jetpack.ui.navigation.Screen


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
    navController: NavController
) {
    // State for the search query
    val searchQuery = remember { mutableStateOf("") }

    // Observe UI state from the ViewModel
    val uiState = viewModel.uiState.collectAsState(initial = UiState.Loading).value

    // Column for organizing content
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        // Search TextField
        TextField(
            value = searchQuery.value,
            onValueChange = {
                searchQuery.value = it
                viewModel.searchDaerah(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, top = 16.dp, end = 10.dp)
                .clip(RoundedCornerShape(20.dp)), // Adjust the corner radius as needed
            placeholder = {
                Text(text = "Cari Daerah")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { viewModel.searchDaerah(searchQuery.value) }
            )
        )

        // Display content based on UI state
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllSeries()
            }
            is UiState.Success -> {
                // Use weight to allocate space proportionally
                HomeContent(
                    orderReward = uiState.data,
                    modifier = Modifier.weight(1f),
                    navigateToDetail = navigateToDetail,
                )
            }
            is UiState.Error -> {
                // Handle error state
            }
        }
        //tambahkan data

    }
    Box(
        contentAlignment = Alignment.BottomEnd,

        modifier = Modifier
            .size(height = 900.dp, width = 400.dp)
            .padding(start = 100.dp, end = 8.dp))
    {
        Button(
            onClick = {
                navController.navigate(Screen.Add.route)
            },
            modifier = Modifier
                .padding(start = 200.dp)
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 10.dp)
                .size(70.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null, Modifier.size(30.dp))


            }
        }
    }
}


@Composable
fun HomeContent(
    orderReward: List<Daerah>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
) {
    // Column for organizing content
    Column(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        // LazyColumn for displaying a list
        LazyColumn {
            items(orderReward) { data ->
                // Card for each item
                Card(
                    modifier = Modifier
                        .clickable { navigateToDetail(data.id) }
                        .padding(10.dp)
                        .fillMaxWidth(),
                    elevation = 4.dp
                ) {
                    // Column for item content
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        // Image
                        Image(
                            painter = rememberImagePainter(data.image),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(shape = RoundedCornerShape(8.dp))
                        )

                        // Spacer with padding
                        Spacer(modifier = Modifier.height(8.dp))

                        // Text content with padding
                        Text(
                            text = data.title,
                            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(8.dp)
                        )
                        Text(
                            text = "Rate: ${data.rate}",
                            style = TextStyle(fontSize = 14.sp),
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }

                // Add a separator except for the last item
                if (data != orderReward.last()) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}


