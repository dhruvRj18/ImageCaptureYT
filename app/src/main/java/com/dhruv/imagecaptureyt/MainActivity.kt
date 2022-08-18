package com.dhruv.imagecaptureyt

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import com.dhruv.imagecaptureyt.databinding.ActivityMainBinding
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : AppCompatActivity() ,EasyPermissions.PermissionCallbacks{

    companion object{
        const val  CAMERA_PERMISSION_REQUEST_CODE = 100
        const val IMAGE_CAPTURE_REQUEST_CODE = 101
    }

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestCameraPermission()

        binding.btnCapture.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, IMAGE_CAPTURE_REQUEST_CODE)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_CAPTURE_REQUEST_CODE && resultCode == RESULT_OK) {
         val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.imgView.setImageBitmap(imageBitmap)
        }
    }



    private fun requestCameraPermission(){
        if(hasCameraPermission()){
            return
        }

        EasyPermissions.requestPermissions(
            this,
            "you need camera permission to use this app",
            CAMERA_PERMISSION_REQUEST_CODE,
            android.Manifest.permission.CAMERA
        )

    }

    private fun hasCameraPermission()=
        EasyPermissions.hasPermissions(
            this,
            android.Manifest.permission.CAMERA
        )

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>){
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this,perms)){
            AppSettingsDialog.Builder(this).build().show()
        }else{
            requestCameraPermission()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult((requestCode),permissions,grantResults,this)
    }


}