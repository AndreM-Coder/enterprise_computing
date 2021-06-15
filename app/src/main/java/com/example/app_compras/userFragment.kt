package com.example.app_compras

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class userFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    lateinit var recuperarPassword: Button
    lateinit var registerFragment: RegisterFragment
    lateinit var dontHaveAccount: Button
    lateinit var submitLoginButton: TextView
    lateinit var emailLogin: EditText
    lateinit var passwordLogin: EditText


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        //check if user is authenticated
        auth = FirebaseAuth.getInstance()
        var user = auth.currentUser
        return if (user != null) {
            //return that user is currently logged
            inflater.inflate(R.layout.fragment_user, container, false)
        } else {
            // No user is signed in.
            inflater.inflate(R.layout.activity_login, container, false)

        }

    }


}