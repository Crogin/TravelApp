package com.example.outtakeapp.Activities.testActivities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.outtakeapp.Activities.BaseActivity
import com.example.outtakeapp.R
import com.example.outtakeapp.databinding.ActivityPhoneBinding
import com.example.outtakeapp.databinding.ActivityPhotoBinding
import java.io.File

class PhotoActivity : BaseActivity() {
    lateinit var binding: ActivityPhotoBinding
    lateinit var imageUri: Uri
    lateinit var outPutImage: File
    val fromAlbum  = 2
    private lateinit var takePictureLauncher: ActivityResultLauncher<Intent>
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // 注册ActivityResultLauncher来处理拍照结果
        takePictureLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(imageUri))
                binding.image.setImageBitmap(bitmap)
            }
        }

        binding.photo.setOnClickListener {
            outPutImage = File(externalCacheDir, "output_image.jpg")
            if (outPutImage.exists()) {
                outPutImage.delete()
            }
            outPutImage.createNewFile()
            imageUri =
                FileProvider.getUriForFile(this, "com.example.outtakeapp.fileprovider", outPutImage)
            val intent = Intent("android.media.action.IMAGE_CAPTURE")
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            takePictureLauncher.launch(intent)
        }
        
        binding.btnClick.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent, fromAlbum)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            fromAlbum -> {
                if (resultCode == RESULT_OK && data != null){
                    val uri = data.data
                    if (uri != null) {
                        contentResolver.openFileDescriptor(uri, "r")?.use {
                            val fileDescriptor = it.fileDescriptor
                            val bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
                            binding.image.setImageBitmap(bitmap)
                        }
                    }
                }

            }
        }
    }
}