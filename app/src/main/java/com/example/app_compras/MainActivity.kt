package com.example.app_compras

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    lateinit var homeFragment: homeFragment
    lateinit var promotionsFragment: promotionsFragment
    lateinit var searchFragment: searchFragment
    lateinit var cartFragment: cartFragment
    lateinit var userFragment: userFragment
    private var backPressedTime: Long = 0


    private lateinit var navController: NavController

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        homeFragment = homeFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fl_wrapper, homeFragment)
            .commit()

        setupNavigation(bottomNavigationView)


    }

    private fun setupNavigation(bottomNavigationView: BottomNavigationView) {
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    homeFragment = homeFragment()
                    mudarFragment(homeFragment)
                }
                R.id.promotionsFragment -> {
                    promotionsFragment = promotionsFragment()
                    mudarFragment(promotionsFragment)
                }
                R.id.searchFragment -> {
                    searchFragment = searchFragment()
                    mudarFragment(searchFragment)
                }
                R.id.cartFragment -> {
                    cartFragment = cartFragment()
                    mudarFragment(cartFragment)
                }
                R.id.userFragment -> {
                    userFragment = userFragment()
                    mudarFragment(userFragment)
                }

            }
            true
        }
    }

    private fun mudarFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fl_wrapper, fragment)
            .commit()
    }

    override fun onBackPressed() {
        when {
            supportFragmentManager.backStackEntryCount > 0 -> {
                supportFragmentManager.popBackStack()
            }
            backPressedTime + 2000 > System.currentTimeMillis() -> {
                super.onBackPressed()
                return
            }
            else -> {
                Toast.makeText(this, "Prima BACK novamente para sair.", Toast.LENGTH_SHORT).show()
            }
        }
        backPressedTime = System.currentTimeMillis()
    }
}