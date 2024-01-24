package com.example.jetpack.ui.screen.add

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dicoding.jetpack.R
import com.dicoding.jetpack.ui.navigation.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AddScreen(
    onAddClick: (judul: String, deskripsi: String, rate: String) -> Unit,
    onImagePick: (uri: String) -> Unit,
    navController: NavController
) {

    val coroutineScope = rememberCoroutineScope()

    var isSnackbarVisible by remember { mutableStateOf(false) }
    var judul by remember { mutableStateOf("") }
    var isJudulError by remember { mutableStateOf(false) }
    var deskripsi by remember { mutableStateOf("") }
    var isDeskripsiError by remember { mutableStateOf(false) }
    var rate by remember { mutableStateOf("") }
    var isRateError by remember { mutableStateOf(false) }
    val imageUri by remember { mutableStateOf<String?>(null) }
    val imageLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                onImagePick(it.toString())
            }
        }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clickable {
                        navController.popBackStack()
                    }
            )
            Spacer(modifier = Modifier.width(16.dp)) // Spacer untuk memberikan jarak antara Icon dan Text
            Text(
                text = "Tambahkan Daerah",
                style = TextStyle(
                    fontSize = 20.sp, // Atur ukuran teks sesuai kebutuhan
                    fontWeight = FontWeight.Bold // Atur gaya teks sesuai kebutuhan
                )
            )
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(Color.LightGray)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
    ) {

        // Input untuk judul
        TextField(
            value = judul,
            onValueChange = {
                judul = it
                isJudulError = it.isEmpty()
            },
            label = {
                Text("Judul *", color = if (isJudulError) Color.Red else LocalContentColor.current)
            },
            isError = isJudulError,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp, bottom = 16.dp),
            colors = TextFieldDefaults.textFieldColors(
                textColor = LocalContentColor.current.copy(alpha = ContentAlpha.high),
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Red
            )
        )

        if (isJudulError) {
            Text(
                text = "Judul harus diisi",
                color = Color.Red,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                style = MaterialTheme.typography.caption
            )
        }


        TextField(
            value = deskripsi,
            onValueChange = {
                deskripsi = it
                isDeskripsiError = it.isEmpty()
            },
            label = {
                Text("Deskripsi *", color = if (isDeskripsiError) Color.Red else LocalContentColor.current)
            },
            isError = isDeskripsiError,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = TextFieldDefaults.textFieldColors(
                textColor = LocalContentColor.current.copy(alpha = ContentAlpha.high),
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Red
            )
        )

        if (isDeskripsiError) {
            Text(
                text = "Deskripsi harus diisi",
                color = Color.Red,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                style = MaterialTheme.typography.caption
            )
        }

// Input untuk rate
        TextField(
            value = rate,
            onValueChange = {
                rate = it
                isRateError = it.isEmpty()
            },
            label = {
                Text("Rate *", color = if (isRateError) Color.Red else LocalContentColor.current)
            },
            isError = isRateError,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = TextFieldDefaults.textFieldColors(
                textColor = LocalContentColor.current.copy(alpha = ContentAlpha.high),
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Red
            )
        )

        if (isRateError) {
            Text(
                text = "Rate harus diisi",
                color = Color.Red,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                style = MaterialTheme.typography.caption
            )
        }

        // Pilih gambar
        Text(text = "Pilih gambar", modifier = Modifier.padding(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.5f)
                .background(MaterialTheme.colors.background)
                .clickable {
                    imageLauncher.launch("image/*")
                }
                .clip(MaterialTheme.shapes.medium)
        ) {
            if (imageUri != null) {
                Image(
                    painter = painterResource(id = R.drawable.nitiya),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(MaterialTheme.shapes.medium)
                )
            } else {
                Icon(
                    imageVector = Icons.Default.AccountBox,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }
        }

        // Tombol Tambahkan
        Button(
            onClick = {

                val isJudulValid = judul.isNotEmpty()
                val isDeskripsiValid = deskripsi.isNotEmpty()
                val isRateValid = rate.isNotEmpty()

                if (isJudulValid && isDeskripsiValid && isRateValid) {
                    navController.navigate(Screen.Home.route)

                    isSnackbarVisible = true

                    coroutineScope.launch {
                        delay(3000)
                        isSnackbarVisible = false
                    }
                } else {
                    isSnackbarVisible = true

                    coroutineScope.launch {
                        delay(3000)
                        isSnackbarVisible = false
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(text = "Tambahkan")
        }

        if (isSnackbarVisible) {
            Snackbar(
                modifier = Modifier.padding(16.dp),
                action = {
                    // Add an action if needed
                }
            ) {
                // Modify the text based on the validation result
                if (judul.isNotEmpty() && deskripsi.isNotEmpty() && rate.isNotEmpty()) {
                    Text(text = "Data ditambahkan!")
                } else {
                    Text(text = "Harap isi semua kolom yang wajib diisi.")
                }
            }
        }

    }
}

