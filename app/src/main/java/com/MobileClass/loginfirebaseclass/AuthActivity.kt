package com.MobileClass.loginfirebaseclass

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.MobileClass.loginfirebaseclass.databinding.ActivityAuthBinding
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("messaje","Firebase integration complete")
        analytics.logEvent("InitScreen",bundle)

        auth = Firebase.auth


        //Setup
        setup()
    }

    private fun setup(){

        title="Authentication"
        binding.signUpButton.setOnClickListener{
            if (binding.eMailEditText.text.isNotEmpty() && binding.passwordEditText.text.isNotEmpty()){
                auth.createUserWithEmailAndPassword(binding.eMailEditText.text.toString(),binding.passwordEditText.text.toString())
                    .addOnCompleteListener{
                        if(it.isSuccessful){
                            showHome(it.result.user?.email ?:"", ProviderType.BASIC)
                        }else{
                            showAlert()
                        }

                    }
            }

        }

        binding.loginButton.setOnClickListener{
            if (binding.eMailEditText.text.isNotEmpty() && binding.passwordEditText.text.isNotEmpty()){
                auth.signInWithEmailAndPassword(binding.eMailEditText.text.toString(),binding.passwordEditText.text.toString())
                    .addOnCompleteListener{
                        if(it.isSuccessful){
                            showHome(it.result.user?.email ?:"", ProviderType.BASIC)
                        }else{
                            showAlert()
                        }

                    }
            }

        }

    }
    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Ha ocurrido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar",null)
        val dialog:  AlertDialog = builder.create()
        dialog.show()
    }
    private fun showHome(email: String, provider: ProviderType){
        val homeIntent = Intent(this, HomeActivity::class.java).apply {
            putExtra("email",email)
            putExtra("provider",provider.name)

        }
        startActivity(homeIntent)
    }

}