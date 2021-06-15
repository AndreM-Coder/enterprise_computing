package com.example.app_compras

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth

class userFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        //check if user is authenticated
        auth = FirebaseAuth.getInstance()
        var user = auth.currentUser
        return if (user != null) {
            //return that user is currently logged
            inflater.inflate(R.layout.fragment_user, container, false)
        } else {
            // No user is signed in.
            inflater.inflate(R.layout.fragment_login, container, false)

        }
    }

}