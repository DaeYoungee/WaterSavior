package com.example.watersavior.screen.onboarding

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.example.watersavior.R
import com.example.watersavior.screen.home.MainActivity

class OnBoardActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OnBoardScreen(onClick = { moveOnMain() })
        }
    }

    fun moveOnMain() {
        val intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}