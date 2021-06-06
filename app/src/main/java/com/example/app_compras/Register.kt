package com.example.app_compras

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        getSupportActionBar()?.setTitle("Register")

        //mudar a view
        goToLoginButton2.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        //funçao para validar inputs
        submitRegisterButton.setOnClickListener {
            if (registerEmail.text.trim().toString().isNotEmpty() || passwordRegister.text.trim().toString().isNotEmpty() || rePasswordRegister.text.trim().toString().isNotEmpty().equals(passwordRegister))
            {
                if(passwordRegister.length() >= 6) {
                    // Após registar vai para activity login
                    // Log.e("Action", "Login text correct")
                    //Toast.makeText(this, "Sucesso", Toast.LENGTH_LONG).show()
                    registerUser(registerEmail.text.trim().toString(), passwordRegister.text.trim().toString())
                }
                else {
                    Toast.makeText(this, "Password Should be at Least 6 Characters Long!", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Check if All The Fields Are Filled!", Toast.LENGTH_LONG).show()
            }
        }

    }


    // função para efetivar o registo
    fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this)
        { task ->
            if (task.isSuccessful)
            {
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                Toast.makeText(this, "Sign Up Successfully", Toast.LENGTH_LONG).show()
                //Log.e("Task Message", "Registo Feito com Sucesso")
            } else {
                //Log.e("Task Message", "Failed" + task.exception)
                Toast.makeText(this, "Failed"+ (task.exception?.message ?: "error" ), Toast.LENGTH_SHORT).show()
            }
        }
    }

}