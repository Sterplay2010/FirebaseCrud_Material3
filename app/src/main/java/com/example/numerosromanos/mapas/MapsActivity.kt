package com.example.numerosromanos.mapas

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.example.numerosromanos.R
import com.example.numerosromanos.databinding.ActivityMapsBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import java.util.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.button.setOnClickListener {
            //searchDirection(binding.editText.text.toString())
            //calculateDistance()
            //18.861288132371495, -99.20953083608204
            openNavigation(LatLng(18.82940853860673,-99.23281985874216),LatLng(18.861288132371495,-99.20953083608204))
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        mMap.isMyLocationEnabled = true
        cameraListener()
    }

    fun cameraListener(){
        mMap.setOnCameraIdleListener {
            var lat = mMap.cameraPosition.target.latitude
            var lon = mMap.cameraPosition.target.longitude
            println("MapsLog ${lat} + ${lon}")
            getDirection(lat, lon)
        }
    }

    fun searchDirection(direction:String){
        try {
            var latLng = LatLng(0.0,0.0)
            var geocoder = Geocoder(this)
            var directions = geocoder.getFromLocationName(direction,1)
            if (!directions.isNullOrEmpty()){
                var direccion = directions[0]
                latLng = LatLng(direccion.latitude,direccion.longitude)
                mMap.addMarker(MarkerOptions().position(latLng).title("Ubicación"))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10f))
            }
        }catch (e:Exception){
            Snackbar.make(binding.root,"Ocurrio un error",Snackbar.LENGTH_SHORT).show()
        }
    }

    fun getDirection(lat:Double,lon:Double){
       try {
           var geocoder = Geocoder(this, Locale.getDefault())
           var directions = geocoder.getFromLocation(lat,lon,1)
           if (directions.size>0){
               var country = directions[0].countryName
               if (country!=null){
                   println("MapsLog Pais $country")
               }
               var state = directions[0].adminArea
               if (state!=null) println("MapsLog Pais $state")
               // Municipio
               var locale = directions[0].locale
               if (locale!=null) println("MapsLog Pais $locale")
               // Colonia
               var colonia = directions[0].subLocality
               if (colonia!=null) println("MapsLog Colonia $colonia")
               // Calle
               var calle = directions[0].thoroughfare
               if (calle!=null) println("MapsLog Calle $calle")
               // Numero
               var numero = directions[0].subThoroughfare
               if (numero!=null) println("MapsLog Numero $numero")
               // Codigo postal
               var postal = directions[0].postalCode
               if (postal!=null) println("MapsLog postal $postal")
               // Direccion completa
               var dir = directions[0].getAddressLine(0)
               if (dir!=null) println("MapsLog Direccion Completa $dir")
           }
       }catch (e:Exception){
           Snackbar.make(binding.root,"Ocurrio un error",Snackbar.LENGTH_SHORT).show()
       }
    }

    fun calculateDistance(){
        var loc1 = Location("")
        loc1.latitude = 18.82940853860673
        loc1.longitude =  -99.23281985874216
        var loc2 = Location("")
        loc2.latitude = 18.00
        loc2.longitude = -19.00
        var distance = loc1.distanceTo(loc2)
        println("MapsLog distancia $distance")
    }

    fun openNavigation(origin:LatLng,destiny:LatLng){
        //http://maps.google.com/maps?saddr=location1&daddr=location2
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=${origin.latitude},${origin.longitude}&daddr=${destiny.latitude},${destiny.longitude}"))
        startActivity(intent)
    }
}