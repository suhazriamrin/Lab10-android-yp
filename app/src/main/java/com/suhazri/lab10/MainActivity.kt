package com.suhazri.lab10

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.suhazri.lab10.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        auth = FirebaseAuth.getInstance() //retrieve Firebase connection
        setContentView(view)

        binding.registerButton.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this){ task ->
                    if(task.isSuccessful){
                        val intent = Intent(this, ProfileActivity::class.java)
                        startActivity(intent)
                    }
                    else {
                        Snackbar.make(binding.root, "Login failed",
                            Snackbar.LENGTH_SHORT).show()
                    }
                }
        }
    }
}