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
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class userFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    lateinit var recuperarPassword: Button
    lateinit var registerFragment: RegisterFragment
    lateinit var dontHaveAccount: Button
    lateinit var submitLoginButton: TextView
    lateinit var emailLogin: EditText
    lateinit var passwordLogin: EditText
    lateinit var loginFragment: LoginFragment

    lateinit var currentid : String

    lateinit var firstname: TextView
    lateinit var lastname: TextView
    lateinit var firstname2: TextView
    lateinit var lastname2: TextView
    lateinit var email: TextView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_user, container, false)

        //check if user is authenticated
        auth = FirebaseAuth.getInstance()


        loginFragment = LoginFragment()
        registerFragment = RegisterFragment()

        firstname = view.findViewById(R.id.firstname)
        lastname = view.findViewById(R.id.lastname)
        firstname2 = view.findViewById(R.id.firstname2)
        lastname2 = view.findViewById(R.id.lastname2)
        email = view.findViewById(R.id.email)

        val user = auth.currentUser

        if (user != null) {
            currentid = user.uid
            FirebaseFirestore.getInstance().collection("Users").document(currentid)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        firstname.text = task.result?.getString(("firstName")).toString()
                        lastname.text = task.result?.getString(("lastName")).toString()
                        firstname2.text = task.result?.getString(("firstName")).toString()
                        lastname2.text = task.result?.getString(("lastName")).toString()
                        email.text = task.result?.getString(("email")).toString()

                    }
                }

        } else {
            // No user is signed in.
            mudarFragment(loginFragment)
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