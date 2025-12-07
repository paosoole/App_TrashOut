@file:OptIn(ExperimentalPermissionsApi::class)

package cl.trashout.ev2_phonetruck.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

import cl.trashout.ev2_phonetruck.R
import cl.trashout.ev2_phonetruck.model.UserLocationModel
import cl.trashout.ev2_phonetruck.ui.components.barras.TopBar
import cl.trashout.ev2_phonetruck.ui.components.buttoms.ButtonLogut
import cl.trashout.ev2_phonetruck.ui.components.buttoms.RecordToggle_2
import cl.trashout.ev2_phonetruck.ui.navigation.AppScreens
import cl.trashout.ev2_phonetruck.viewModel.MainViewModel

// =========================================================
// TRACKING SCREEN
// =========================================================

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@SuppressLint("MissingPermission")
@Composable
fun TrackingScreen(
    navController: NavController
) {
    val viewModel: MainViewModel = viewModel()

    val locationPermission =
        rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)

    // Pedimos permiso solo una vez
    LaunchedEffect(Unit) {
        locationPermission.launchPermissionRequest()
    }

    // Iniciar GPS solo cuando permiso está OK
    LaunchedEffect(locationPermission.status.isGranted) {
        if (locationPermission.status.isGranted) {
            viewModel.startLocationUpdates()
        }
    }

    val userLocation by viewModel.location.collectAsState()
    val routePoints by viewModel.routePoints.collectAsState()

    // No cargar mapa todavía si no hay datos completos
    if (!locationPermission.status.isGranted || userLocation == null) {
        Scaffold(
            topBar = { TopBar() }
        ) { padding ->
            Column(
                modifier = Modifier.padding(padding).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(10.dp))
                Text("Cargando ubicación…")
            }
        }
        return
    }

    // Si todo está OK → cargar vista completa
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
                    textAlign = TextAlign.Center
                )
            }
        }
    ) { innerPadding ->
        TrackingContent(
            viewModel = viewModel,
            userLocation = userLocation!!,
            routePoints = routePoints,
            locationPermission = locationPermission,
            innerPadding = innerPadding,
            navController = navController
        )
    }
}

// =========================================================
// TRACKING CONTENT
// =========================================================

@Composable
fun TrackingContent(
    viewModel: MainViewModel,
    userLocation: UserLocationModel,
    routePoints: List<LatLng>,
    locationPermission: PermissionState,
    innerPadding: PaddingValues,
    navController: NavController
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
                    if (isRecording) viewModel.stopRecording()
                    else viewModel.startRecording()
                }
            )
        }

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

        ButtonLogut(
            onClick = {
                navController.navigate(AppScreens.LoginScreen.route) {
                    popUpTo(AppScreens.LoginScreen.route) { inclusive = true }
                }
            }
        )
    }
}

// =========================================================
// MAPA
// =========================================================

@Composable
fun LocationMap(
    userLocation: UserLocationModel,
    routePoints: List<LatLng>,
    modifier: Modifier = Modifier
) {
    val cameraPositionState = rememberCameraPositionState()

    // Animar cámara cuando cambia ubicación
    LaunchedEffect(userLocation) {
        val pos = LatLng(userLocation.latitude, userLocation.longitude)
        cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(pos, 16f))
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

        val pos = LatLng(userLocation.latitude, userLocation.longitude)

        Marker(
            state = MarkerState(position = pos),
            title = "Tu ubicación actual",
            icon = BitmapDescriptorFactory.fromResource(R.drawable.camion_m2)
        )
    }
}

// =========================================================
// UI SIMPLE
// =========================================================

@Composable
fun LocationInfoContent(userLocation: UserLocationModel) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
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