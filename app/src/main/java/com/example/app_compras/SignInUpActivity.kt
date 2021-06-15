package com.example.app_compras

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignInUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val mRegisterFragment: RegisterFragment = RegisterFragment()
    private val mLoginFragment: LoginFragment = LoginFragment()
    lateinit var recuperarPassword: Button
    lateinit var registerFragment: RegisterFragment
    lateinit var dontHaveAccount: Button
    lateinit var submitLoginButton: TextView
    lateinit var emailLogin: EditText
    lateinit var passwordLogin: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        registerFragment = RegisterFragment()
        auth = FirebaseAuth.getInstance()

        recuperarPassword = findViewById(R.id.btn_forgotPassword)
        dontHaveAccount = findViewById(R.id.goToRegisterButton2)
        submitLoginButton = findViewById(R.id.submitLoginButton)
        emailLogin = findViewById(R.id.emailLogin)
        passwordLogin = findViewById(R.id.passwordLogin)

        dontHaveAccount.setOnClickListener {
            Toast.makeText(
                baseContext, "Please!",
                Toast.LENGTH_SHORT
            ).show()
            Log.d("dasdasdas", "CHGEUIE")

        }

        submitLoginButton.setOnClickListener {
            val email = emailLogin.text.trim().toString()
            val pass = passwordLogin.text.trim().toString()
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                // Log.e("Action", "Login text correct")
                //Toast.makeText(this, "Sucesso", Toast.LENGTH_LONG).show()

                loginUser(email, pass)
            } else {
                Toast.makeText(
                    baseContext, "Please! Fill the Form Correctly",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }



        recuperarPassword.setOnClickListener {
//            mudarFragment()
        }

    }



    private fun loginUser(email: String, password: String){

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this)
            { task ->
                if(task.isSuccessful) {
                    //if login feito com sucesso vai para user dashboard
                    //Log.e("Task Message", "Success");
                    val user = auth.currentUser
                    Toast.makeText(
                        baseContext, "Login failed. Try again later",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(user)
                } else {
                    //else (login tem erro)
                    //Log.e( "Task Message", "Failed"+task.exception.getMessage());
                    Toast.makeText(
                        baseContext,
                        "The Inserted Data does Not Match our Records",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
        }


    private fun updateUI (currentUser: FirebaseUser?){
        if (currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }
    }



}