package com.example.woordup

import android.app.Application
import com.google.firebase.FirebaseApp

class WoordUpApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}
