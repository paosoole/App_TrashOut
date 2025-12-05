@file:OptIn(ExperimentalPermissionsApi::class)
package cl.trashout.ev2_phonetruck.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import cl.trashout.ev2_phonetruck.model.UserLocationModel
import cl.trashout.ev2_phonetruck.ui.navigation.AppScreens
import com.google.accompanist.permissions.PermissionState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.maps.android.compose.*
import cl.trashout.ev2_phonetruck.viewModel.MainViewModel
import com.google.accompanist.permissions.isGranted
import com.google.maps.android.compose.MapProperties
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.LatLng
import cl.trashout.ev2_phonetruck.ui.components.barras.TopBar
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import cl.trashout.ev2_phonetruck.R
import cl.trashout.ev2_phonetruck.ui.components.buttoms.ButtonLogut
import cl.trashout.ev2_phonetruck.ui.components.buttoms.RecordToggle
import cl.trashout.ev2_phonetruck.ui.components.buttoms.RecordToggle_2


@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@SuppressLint("MissingPermission")
@Composable
fun TrackingScreen(navController: NavController,
                   viewModel: MainViewModel = viewModel()
) {
    val locationPermission = rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)

    // Pedimos permiso al entrar
    LaunchedEffect(Unit) {
        locationPermission.launchPermissionRequest()
        viewModel.startLocationUpdates()
    }

    val userLocation by viewModel.location.collectAsState()
    val routePoints by viewModel.routePoints.collectAsState()

    Scaffold(
        topBar = { TopBar() },
        bottomBar = {
            BottomAppBar(
                containerColor = Color(0xFF00BCD4),
                contentColor = Color.White
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Servicio de Recolección",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.startLocationUpdates() },
                containerColor = Color(0xFF00BCD4),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.LocationOn, contentDescription = "Actualizar ubicación")
            }
        }
    ) { innerPadding ->
        // Pasar navController al TrackingContent
        TrackingContent(
            viewModel = viewModel,
            userLocation = userLocation,
            routePoints = routePoints,
            locationPermission = locationPermission,
            innerPadding = innerPadding,
            navController = navController  // ← Agregar este parámetro
        )
        ButtonLogut(
            onClick = {
                // Aquí va la navegación
                navController.navigate(AppScreens.LoginScreen.route) {
                    popUpTo(AppScreens.LoginScreen.route) { inclusive = true }
                }
            }
        )
    }
}




@Composable
fun TrackingContent(
    viewModel: MainViewModel,
    userLocation: UserLocationModel?,
    routePoints: List<LatLng>,
    locationPermission: PermissionState,
    innerPadding: PaddingValues,
    navController: NavController  // Recibir navController
) {
    val isRecording by viewModel.isRecording.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Ubicación actual:",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.weight(1f))

            RecordToggle_2(
                isRecording = isRecording,
                onToggle = {
                    if (isRecording) {
                        viewModel.stopRecording()
                    } else {
                        viewModel.startRecording()
                    }
                }
            )
        }
        if (locationPermission.status.isGranted) {
            // Permiso otorgado
            if (userLocation == null) {
                LoadingLocationContent()
            } else {
                LocationInfoContent(userLocation)
                Spacer(modifier = Modifier.height(16.dp))

                LocationMap(
                    userLocation = userLocation,
                    routePoints = routePoints,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // BOTÓN DE LOGOUT
                ButtonLogut(
                    onClick = {
                        navController.navigate(AppScreens.LoginScreen.route) {
                            popUpTo(AppScreens.LoginScreen.route) { inclusive = true }
                        }
                    },
                    modifier = Modifier

                )
            }
        } else {
            // Permiso no otorgado
            PermissionDeniedContent(locationPermission)
        }
    }
}


@Composable
fun LocationMap(
    userLocation: UserLocationModel?,
    routePoints: List<LatLng>,
    modifier: Modifier = Modifier
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(userLocation) {
        userLocation?.let { location ->
            val currentLatLng = LatLng(location.latitude, location.longitude)
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngZoom(currentLatLng, 16f)
            )
        }
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        properties = MapProperties(isMyLocationEnabled = true)
    ) {
        if (routePoints.isNotEmpty()) {
            Polyline(
                points = routePoints,
                color = Color.Blue,
                width = 8f
            )
        }

        userLocation?.let { location ->
            val currentLatLng = LatLng(location.latitude, location.longitude)
            Marker(
                state = MarkerState(position = currentLatLng),
                title = "Tu ubicación actual",
                icon = BitmapDescriptorFactory.fromResource(R.drawable.camion_m2)
            )
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionDeniedContent(locationPermission: PermissionState) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Permiso de ubicación no otorgado")
        Spacer(Modifier.height(10.dp))
        Button(onClick = { locationPermission.launchPermissionRequest() }) {
            Text("Solicitar permiso de ubicación")
        }
    }
}

@Composable
fun LoadingLocationContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        Spacer(modifier = Modifier.height(8.dp))
        Text("Obteniendo ubicación...")
    }
}

@Composable
fun LocationInfoContent(userLocation: UserLocationModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Latitud: ${userLocation.latitude}")
        Text("Longitud: ${userLocation.longitude}")
    }
}




//// Preview para testing
//@OptIn(ExperimentalPermissionsApi::class)
//@Composable
//fun PreviewTrackingScreen() {
//    val mockLocationPermission = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
//
//    TrackingContent(
//        userLocation = UserLocation(-33.4489, -70.6693),
//        routePoints = emptyList(),
//        locationPermission = mockLocationPermission,
//        innerPadding = PaddingValues()
//
//    )
//}