package com.MobileClass.loginfirebaseclass

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.MobileClass.loginfirebaseclass.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth

enum class ProviderType {
    BASIC
}


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Setup
        val bundle = intent.extras
        val email = "Welcome: " + bundle?.getString("email")
        val message = "Time to travel! We have recommendations for you"
        setup(email?:"",message?:"")
    }

    private fun setup(email: String, message: String){

        title = "Home Page"
        binding.eMailTextView.setText(email)
        binding.messageTextView.setText(message)

        binding.logOutButton.setOnClickListener{

            FirebaseAuth.getInstance().signOut()
            onBackPressed()

        }

    }
}