package com.example.woordup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.woordup.data.repository.DictionaryRepository
import com.example.woordup.data.repository.UserPreferencesRepository
import com.example.woordup.data.repository.WordRepositoryImpl
import com.example.woordup.network.RetrofitInstance
import com.example.woordup.ui.WoordUpApp
import com.example.woordup.ui.theme.DeepGreen
import com.example.woordup.ui.theme.WoordUPTheme
import com.example.woordup.viewmodel.WordViewModel
import com.example.woordup.viewmodel.WordViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        val auth = com.google.firebase.auth.FirebaseAuth.getInstance()
        val firestore = com.google.firebase.firestore.FirebaseFirestore.getInstance()
        
        val wordRepository = WordRepositoryImpl(firestore, auth)
        val dictionaryApi = RetrofitInstance.api
        val dictionaryRepository = DictionaryRepository(dictionaryApi)
        val userPreferencesRepository = UserPreferencesRepository(applicationContext)
        
        val viewModelFactory = WordViewModelFactory(
            wordRepository,
            dictionaryRepository,
            userPreferencesRepository
        )
        
        val viewModel = ViewModelProvider(this, viewModelFactory)[WordViewModel::class.java]
        
        setContent {
            var isAuthReady by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(false) }

            androidx.compose.runtime.LaunchedEffect(Unit) {
                if (auth.currentUser == null) {
                    auth.signInAnonymously().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                           viewModel.loadWords() 
                           isAuthReady = true 
                        } else {
                           android.util.Log.e("MainActivity", "Anonymous sign-in failed", task.exception)
                           isAuthReady = true 
                        }
                    }
                } else {
                    isAuthReady = true
                }
            }

            WoordUPTheme {
                if (isAuthReady) {
                    WoordUpApp(viewModel = viewModel)
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator(
                                color = DeepGreen
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "WoordUP",
                                style = MaterialTheme.typography.titleLarge,
                                color = DeepGreen
                            )
                        }
                    }
                }
            }
        }
    }
}