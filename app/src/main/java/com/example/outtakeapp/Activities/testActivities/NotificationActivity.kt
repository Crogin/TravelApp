package com.example.outtakeapp.Activities.testActivities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.outtakeapp.Activities.BaseActivity
import com.example.outtakeapp.R
import com.example.outtakeapp.databinding.ActivityNotificationBinding

class NotificationActivity : BaseActivity() {
    lateinit var binding: ActivityNotificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}