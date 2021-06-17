package com.example.app_compras

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class FragmentResetPassword : Fragment() {

    private lateinit var auth: FirebaseAuth
    lateinit var loginFragment: LoginFragment
    lateinit var login_button: TextView
    lateinit var submitReset: Button
    lateinit var resetEmail: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_reset_password, container, false)

        auth = FirebaseAuth.getInstance()
        loginFragment = LoginFragment()
        login_button = view.findViewById(R.id.goToLoginButton)
        submitReset = view.findViewById(R.id.submitReset)
        resetEmail = view.findViewById(R.id.resetEmail)


        submitReset.setOnClickListener {
            if (resetEmail.text.trim().toString().isNotEmpty())
            {
                auth.sendPasswordResetEmail(resetEmail.text.trim().toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            mudarFragment(loginFragment)
                            Toast.makeText(activity, "A Reset Password Link Was Sent To Your Email", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(activity, "Failed"+ (task.exception?.message ?: "error"), Toast.LENGTH_LONG).show()
                        }
                    }
            } else {
                Toast.makeText(activity, "Please Fill the Required Field!", Toast.LENGTH_LONG).show()
            }
        }


      login_button.setOnClickListener {
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