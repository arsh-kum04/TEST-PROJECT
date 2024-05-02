**Folder Name: acitivity**

**File Name: MainActivity.kt**

```kotlin
// Package declaration
package com.example.notesapp_internshala.acitivity

// Import necessary libraries
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.notesapp_internshala.R
import com.example.notesapp_internshala.fragments.NotesFragment

/**
 * The main activity class that serves as the entry point for the application.
 */
class MainActivity : AppCompatActivity() {

    // Boolean flag to track double back press for exiting the app
    private var backPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        // Call the superclass onCreate method
        super.onCreate(savedInstanceState)

        // Set the layout for the activity
        setContentView(R.layout.activity_main)

        // Initialize the NavController
        val navController: NavController
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Set the navigation graph for the NavController
        navController.setGraph(R.navigation.nav_graph)
    }

    override fun onBackPressed() {
        // Check if the current fragment is NotesFragment
        val currentFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        if (currentFragment is NotesFragment) {

            // If it is NotesFragment, handle back press accordingly

            if (backPressedOnce) {
                // If back button is pressed once, exit the app
                super.onBackPressed()
            } else {
                // Show a toast message and set backPressedOnce to true
                Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show()
                backPressedOnce = true

                // Reset the backPressedOnce flag after 2 seconds
                Handler().postDelayed({
                    backPressedOnce = false
                }, 2000)
            }
        } else {
            // If it is not NotesFragment, proceed with the default back press behavior
            super.onBackPressed()
        }
    }
}
```