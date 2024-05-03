### **Folder Name: com.example.notesapp_internshala.activity**

### **File Name: MainActivity.kt**

**// Package Declaration**

// This is the package declaration. It specifies the package to which this file belongs.
// In this case, the package is com.example.notesapp_internshala.activity.
// This means that this file is part of the activity module of the Notes App application.

**// Importing required classes**

// Here, we are importing the necessary classes from various packages.
// We are importing AppCompatActivity from the androidx.appcompat.app package.
// AppCompatActivity is the base class for activities that provide a Material Design user interface.
// We are also importing Bundle, NavHostFragment, NavGraph, and NotesFragment from various packages.

**// Main Activity Class**

// This is the main activity class. It extends AppCompatActivity, which means it inherits all the functionality of AppCompatActivity.
// This class defines the main activity of our application.

**// Declare a variable to keep track of whether the back button has been pressed once**

// We declare a private variable called backPressedOnce. This variable will be used to track whether the back button has been pressed once or not.
// This is necessary to handle the back button press differently based on whether the current fragment is NotesFragment or not.

**// Override onCreate**

// The onCreate method is the entry point of the activity. It is called when the activity is first created.
// Here, we are setting the layout for the activity, initializing variables, and setting up the navigation controller.

**// Override onBackPressed**

// The onBackPressed method is called when the back button is pressed.
// Here, we are handling the back button press differently based on whether the current fragment is NotesFragment or not.
// If the current fragment is NotesFragment, we show a toast message and set the backPressedOnce variable to true.
// We also create a handler to reset the backPressedOnce variable after a delay of 2 seconds.
// If the current fragment is not NotesFragment, we proceed with the default behavior of super.onBackPressed().

```kotlin
// Package Declaration
package com.example.notesapp_internshala.activity

// Importing required classes
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavGraph
import androidx.navigation.NavHostFragment
import com.example.notesapp_internshala.R
import com.example.notesapp_internshala.databinding.ActivityMainBinding
import com.example.notesapp_internshala.fragments.NotesFragment

class MainActivity : AppCompatActivity() {

    // Declare a variable to keep track of whether the back button has been pressed once
    private var backPressedOnce = false

    // Override onCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize variables
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)

        // Set up the navigation controller
        navController.setGraph(navGraph, intent.extras)
    }

    // Override onBackPressed
    override fun onBackPressed() {
        if (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) is NotesFragment) {
            if (backPressedOnce) {
                super.onBackPressed()
                return
            }

            backPressedOnce = true
            Toast.makeText(this, "Press BACK again to exit", Toast.LENGTH_SHORT).show()

            // Create a handler to reset the backPressedOnce variable after a delay of 2 seconds
            Handler().postDelayed({ backPressedOnce = false }, 2000)
        } else {
            super.onBackPressed()
        }
    }
}
```