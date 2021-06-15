package com.example.app_compras

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
import com.google.firebase.firestore.FirebaseFirestore

class RegisterFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    val mAuth = FirebaseAuth.getInstance()
    val Auth = FirebaseFirestore.getInstance().collection("Users")
    lateinit var loginFragment: LoginFragment
    lateinit var goToLoginButton2: TextView
    lateinit var submitRegisterButton: Button
    lateinit var registerEmail: EditText
    lateinit var passwordRegister: EditText
    lateinit var rePasswordRegister: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_register, container, false)

        registerEmail = view.findViewById(R.id.registerEmail)
        passwordRegister = view.findViewById(R.id.passwordRegister)
        rePasswordRegister = view.findViewById(R.id.rePasswordRegister)
        loginFragment = LoginFragment()
        auth = FirebaseAuth.getInstance()
        goToLoginButton2 = view.findViewById(R.id.goToLoginButton2)

        //mudar a view
        goToLoginButton2.setOnClickListener {
            mudarFragment(loginFragment)
        }

        submitRegisterButton = view.findViewById(R.id.submitRegisterButton)
        //funçao para validar inputs
        submitRegisterButton.setOnClickListener {
            val email = registerEmail.text.trim().toString()
            val password = passwordRegister.text.trim().toString()
            val repeatPassword = rePasswordRegister.text.trim().toString()
            if (email.isNotEmpty() && password.isNotEmpty() && repeatPassword.isNotEmpty()) {
                if (password == repeatPassword) {
                    if(passwordRegister.length() >= 6) {
                        // Após registar vai para activity login
                        // Log.e("Action", "Login text correct")
                        Log.d("DASDASDA", email)
                        Log.d("DASDASDA", password)
                        Log.d("DASDASDA", repeatPassword)
                        //Toast.makeText(this, "Sucesso", Toast.LENGTH_LONG).show()
//                        registerUser(email, password)
                    } else {
                        Toast.makeText(activity, "Password should be at Least 6 Characters Long!", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(activity, "Password should be equal!", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(activity, "Check if All The Fields Are Filled!", Toast.LENGTH_LONG).show()
            }
        }

        return view
    }


    // função para efetivar o registo
    private fun registerUser(email: String, password: String) {
        activity?.let {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(it) { task ->
                if (task.isSuccessful) {

                    val user = HashMap<String, Any>()
                    user["email"] = email
                    val userRef = Auth
                    val uid = mAuth.uid.toString()
                    userRef.document(uid).set(user).addOnCompleteListener {
                        when {
                            it.isSuccessful -> {Toast.makeText(activity,"Sign Up Successful",Toast.LENGTH_SHORT).show()
                                mAuth.signOut()
                                mudarFragment(loginFragment)
                            }
                            else -> {
                                Toast.makeText(activity,"Sign Up Unsuccessful",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }


                    Toast.makeText(activity, "Sign Up Successfully", Toast.LENGTH_LONG).show()
                    //Log.e("Task Message", "Registo Feito com Sucesso")
                } else {
                    //Log.e("Task Message", "Failed" + task.exception)
                    Toast.makeText(activity, "Failed"+ (task.exception?.message ?: "error" ), Toast.LENGTH_SHORT).show()
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