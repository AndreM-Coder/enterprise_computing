package com.example.app_compras

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_reset_password.*

class ResetPassword : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)


        auth = FirebaseAuth.getInstance()

        getSupportActionBar()?.setTitle("Reset Password")

        submitReset.setOnClickListener {
            if (resetEmail.text.trim().toString().isNotEmpty())
            {
                auth.sendPasswordResetEmail(resetEmail.text.trim().toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this, Login::class.java)
                            startActivity(intent)
                            Toast.makeText(this, "A Reset Password Link Was Sent To Your Email", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this, "Failed"+ (task.exception?.message ?: "error" ), Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Please Fill the Required Field!", Toast.LENGTH_SHORT).show()
            }
        }

        goToLoginButton3.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        //esconder menu
        //this.getSupportActionBar()?.hide();

    }
}