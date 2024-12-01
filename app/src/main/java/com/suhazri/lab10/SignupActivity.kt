package com.suhazri.lab10

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.suhazri.lab10.databinding.ActivityMainBinding
import com.suhazri.lab10.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        val view = binding.root
        auth = FirebaseAuth.getInstance() //retrieve Firebase Auth
        db = FirebaseFirestore.getInstance() //retrieve Firebase Firestore
        setContentView(view)

        binding.regRegisterButton.setOnClickListener {
            val email = binding.regEmailEditText.text.toString()
            val password = binding.regPasswordEditText.text.toString()
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){ task ->
                    if(task.isSuccessful){
                        val newPerson = hashMapOf(
                            "name" to binding.regNameEditText.text.toString().trim(),
                            "city" to binding.regCityEditText.text.toString().trim(),
                            "country" to binding.regCountryEditText.text.toString().trim(),
                            "phone" to binding.regPhoneEditText.text.toString().trim(),
                            "email" to binding.regEmailEditText.text.toString().trim()
                        )

                        db.collection("users")
                            .document(auth.currentUser!!.uid)
                            .set(newPerson)

                        Snackbar.make(binding.root, "Registration successful",
                            Snackbar.LENGTH_SHORT).show()
                        finish()
                    }
                    else {
                        Snackbar.make(binding.root, "Registration failed",
                            Snackbar.LENGTH_SHORT).show()
                    }
                }
        }
    }
}