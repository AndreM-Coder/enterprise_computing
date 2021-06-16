package com.example.app_compras

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    lateinit var recuperarPassword: TextView
    lateinit var registerFragment: RegisterFragment
    lateinit var homeFragment: homeFragment
    lateinit var dontHaveAccount: TextView
    lateinit var submitLoginButton: TextView
    lateinit var emailLogin: EditText
    lateinit var passwordLogin: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_login, container, false)

        registerFragment = RegisterFragment()
        homeFragment = homeFragment()
        auth = FirebaseAuth.getInstance()

        recuperarPassword = view.findViewById(R.id.btn_forgotPassword)
        dontHaveAccount = view.findViewById(R.id.goToRegisterButton2)
        submitLoginButton = view.findViewById(R.id.submitLoginButton)
        emailLogin = view.findViewById(R.id.emailLogin)
        passwordLogin = view.findViewById(R.id.passwordLogin)

        dontHaveAccount.setOnClickListener {
            mudarFragment(registerFragment)
        }

        submitLoginButton.setOnClickListener {
            val email = emailLogin.text.trim().toString()
            val pass = passwordLogin.text.trim().toString()
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                loginUser(email, pass)
            } else {
                Toast.makeText(activity, "Check if All The Fields Are Filled!", Toast.LENGTH_LONG).show()
            }
        }

        recuperarPassword.setOnClickListener {
//            mudarFragment()
            Toast.makeText(activity, "Unavailable right now", Toast.LENGTH_LONG).show()
        }

        return view
    }


    private fun loginUser(email: String, password: String){
        activity?.let {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(it) { task ->
                if(task.isSuccessful) {
                    //if login feito com sucesso vai para user dashboard
                    //Log.e("Task Message", "Success");
                    mudarFragment(homeFragment)
                    Toast.makeText(activity, "Logged in sucessfull", Toast.LENGTH_LONG).show()
                } else {
                    //else (login tem erro)
                    //Log.e( "Task Message", "Failed"+task.exception.getMessage());
                    Toast.makeText(activity, "The Inserted Data does Not Match our Records",Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun mudarFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.fl_wrapper, fragment)
            .commit()
    }

}