package com.example.app_compras

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_main.*



class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        goToRegisterButton2.setOnClickListener {

            val intent = Intent(this,Register::class.java)
            startActivity(intent)
        }

        submitLoginButton.setOnClickListener {
            if (emailLogin.text.trim().toString().isNotEmpty() || passwordLogin.text.trim().toString().isNotEmpty()) {
                // Log.e("Action", "Login text correct")
                //Toast.makeText(this, "Sucesso", Toast.LENGTH_LONG).show()

                loginUser(emailLogin.text.trim().toString(), passwordLogin.text.trim().toString())
            } else {
                Toast.makeText(this, "Please! Fill the Form Correctly", Toast.LENGTH_LONG).show()
            }
        }

        btn_forgotPassword.setOnClickListener {
            val intent = Intent(this, ResetPassword::class.java)
            startActivity(intent)
        }

        getSupportActionBar()?.setTitle("Login")
    }


    fun loginUser(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this)
        { task ->
            if(task.isSuccessful) {
                //if login feito com sucesso vai para user dashboard
                //Log.e("Task Message", "Success");
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, "Success!!", Toast.LENGTH_LONG).show()
            } else {
                //else (login tem erro)
                //Log.e( "Task Message", "Failed"+task.exception.getMessage());
                Toast.makeText(
                    this,
                    "The Inserted Data does Not Match our Records",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}