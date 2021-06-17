package com.example.app_compras

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_user.*

class userFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    lateinit var recuperarPassword: Button
    lateinit var registerFragment: RegisterFragment
    lateinit var dontHaveAccount: Button
    lateinit var submitLoginButton: TextView
    lateinit var emailLogin: EditText
    lateinit var passwordLogin: EditText
    lateinit var loginFragment: LoginFragment
    lateinit var settingsFragment: SettingsFragment

    lateinit var currentid : String

    lateinit var firstname: TextView
    lateinit var lastname: TextView
    lateinit var fullname: TextView
    lateinit var email: TextView
    lateinit var settings_btn: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_user, container, false)

        //check if user is authenticated
        auth = FirebaseAuth.getInstance()


        loginFragment = LoginFragment()
        registerFragment = RegisterFragment()
        settingsFragment = SettingsFragment()

        firstname = view.findViewById(R.id.firstname)
        lastname = view.findViewById(R.id.lastname)
        fullname = view.findViewById(R.id.fullname)
        email = view.findViewById(R.id.email)
        settings_btn = view.findViewById(R.id.settings_btn)


        val user = auth.currentUser

        if (user != null) {
            currentid = user.uid
            FirebaseFirestore.getInstance().collection("Users").document(currentid)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        firstname.text = task.result?.getString(("firstName")).toString()
                        lastname.text = task.result?.getString(("lastName")).toString()
                        email.text = task.result?.getString(("email")).toString()
                        val first_name = task.result?.getString(("firstName")).toString()
                        val spaceBetweenUs = " "
                        val last_name = task.result?.getString(("lastName")).toString()
                        fullname.text = first_name + spaceBetweenUs + last_name
                    }
                }

        } else {
            // No user is signed in.
            mudarFragment(loginFragment)
        }

        firstname.setOnClickListener {
            Toast.makeText(activity, "First Name Updated", Toast.LENGTH_LONG).show()
        }

        lastname.setOnClickListener {
            Toast.makeText(activity, "Last Name Updated", Toast.LENGTH_LONG).show()
        }


        settings_btn.setOnClickListener {
            mudarFragment(settingsFragment)
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
