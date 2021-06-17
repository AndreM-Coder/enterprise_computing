package com.example.app_compras

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_user.*

class SettingsFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    lateinit var loginFragment: LoginFragment
    lateinit var logOut:LinearLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_user_options, container, false)

        //check if user is authenticated
        auth = FirebaseAuth.getInstance()
        loginFragment = LoginFragment()
        logOut = view.findViewById(R.id.logOut)


        val user = auth.currentUser

        logOut.setOnClickListener {
            if (user != null) {
                FirebaseAuth.getInstance().signOut();
                mudarFragment(loginFragment)
                Toast.makeText(activity, "Success!", Toast.LENGTH_LONG).show()
            } else {
                // No user is signed in.
                mudarFragment(loginFragment)
            }
        }

        return view

    }

    private fun mudarFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.fl_wrapper, fragment)
            .commit()
    }



}
