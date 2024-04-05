### **Folder Name: com.example.notesapp_internshala.acitivity**

### **File Name: MainActivity.kt**

```kotlin
// Package Declaration
package com.example.notesapp_internshala.acitivity

// Importing required classes
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.notesapp_internshala.R
import com.example.notesapp_internshala.fragments.NotesFragment

// Main Activity Class
class MainActivity : AppCompatActivity() {

    // Declare a variable to keep track of whether the back button has been pressed once
    private var backPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the layout
        setContentView(R.layout.activity_main)

        // Initialize variables
        val navController: NavController
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        navController.setGraph(R.navigation.nav_graph)
    }

    override fun onBackPressed() {
        // Get the current fragment
        val currentFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)

        // Check if the current fragment is NotesFragment
        if (currentFragment is NotesFragment) {
            // Check if the back button has been pressed once
            if (backPressedOnce) {
                // If the back button has been pressed twice, exit the application
                super.onBackPressed()
            } else {
                // If the back button has been pressed once, show a toast message
                Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show()
                // Set the backPressedOnce variable to true
                backPressedOnce = true

                // Create a handler to reset the backPressedOnce variable after a delay
                val handler = Handler()
                handler.postDelayed({ backPressedOnce = false }, 2000)
            }
        } else {
            // If the current fragment is not NotesFragment, proceed with the default behavior
            super.onBackPressed()
        }
    }
}
```