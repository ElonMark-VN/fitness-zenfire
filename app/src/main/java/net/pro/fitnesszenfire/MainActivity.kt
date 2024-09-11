package net.pro.fitnesszenfire

import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import net.pro.fitnesszenfire.utils.SetupNavigation
import net.pro.fitnesszenfire.presentation.common.SplashViewModel
import net.pro.fitnesszenfire.presentation.login.LoginViewModel
import net.pro.fitnesszenfire.ui.theme.FitnessZenfireTheme
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var splashViewModel: SplashViewModel

    private lateinit var auth: FirebaseAuth
    private lateinit var loginViewModel: LoginViewModel

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            splashViewModel.isLoading.value
        }


        auth = Firebase.auth
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        setContent {
            FitnessZenfireTheme {
                val screen by splashViewModel.startDestination

                Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                ) {
                    SetupNavigation(
                            startDestination = screen,
                    )
                }
            }
        }


        // Khởi tạo FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Yêu cầu quyền vị trí runtime
        val locationPermissionRequest = registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        false
                ) -> { // Quyền truy cập vị trí chính xác đã được cấp
                    getCurrentLocation()
                }

                permissions.getOrDefault(
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        false
                ) -> { // Quyền truy cập vị trí đại khái đã được cấp
                    getCurrentLocation()
                }

                else -> { // Quyền truy cập vị trí bị từ chối
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }

        locationPermissionRequest.launch(
                arrayOf(
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                )
        )
    }


    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                    this, android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this, android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? -> // Lấy được vị trí
                location?.let {
                    val latitude = it.latitude
                    val longitude = it.longitude
                    Toast.makeText(this, "Lat: $latitude, Lon: $longitude", Toast.LENGTH_SHORT)
                        .show()

                    // Bạn có thể sử dụng Geocoder để chuyển đổi latitude và longitude thành địa chỉ cụ thể
                    val geocoder = Geocoder(this, Locale.getDefault())
                    val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                    val address: String =
                        addresses?.get(0)?.getAddressLine(0) ?: ""  // Lấy địa chỉ đầy đủ
                    val city: String = addresses?.get(0)?.locality ?: ""          // Lấy thành phố
                    val district: String =
                        addresses?.get(0)?.subLocality ?: ""        // Lấy quận/huyện
                    val ward: String =
                        addresses?.get(0)?.subThoroughfare ?: ""        // Lấy phường/xã

                    Toast.makeText(this, "Địa chỉ: $address $city $district $ward", Toast.LENGTH_LONG).show()


                    Toast.makeText(this, "Địa chỉ: $address", Toast.LENGTH_LONG).show()
                }
            }.addOnFailureListener { // Lỗi khi lấy vị trí
                Toast.makeText(this, "Failed to get location", Toast.LENGTH_SHORT).show()
            }
    }


}
