### **Folder Name: com.example.notesapp_internshala.activity**

### **File Name: MainActivity.kt**

**// Package Declaration**

// This is the package declaration. 
// In this project, we are using a package named com.example.notesapp_internshala.activity, 
// which means this file is part of the activity module. 
// You can think of a package as a folder in a file system, 
// where you can organize your Kotlin files into different categories.

// **// Importing required classes**

// Here, we are importing the necessary classes. 
// We are importing AppCompatActivity from the androidx.appcompat.app package. 
// AppCompatActivity is the base class for activities that provide a Material Design user interface. 
// We are also importing Bundle, NavHostFragment, NavGraph, and NotesFragment from various packages.

// **// Main Activity Class**

// This is the main activity class. 
// It extends AppCompatActivity, which means it inherits all the functionality of AppCompatActivity. 
// This class defines the main activity of our application.

**// Declare a variable to keep track of whether the back button has been pressed once**

// We declare a private variable called backPressedOnce. 
// This variable will be used to track whether the back button has been pressed once or not.

**// Override onCreate**

// The onCreate method is the entry point of the activity. 
// It is called when the activity is first created. 
// Here, we are setting the layout for the activity, initializing variables, and setting up the navigation controller.

// **// Override onBackPressed**

// The onBackPressed method is called when the back button is pressed. 
// Here, we are handling the back button press differently based on whether the current fragment is NotesFragment or not. 
// If the current fragment is NotesFragment, we show a toast message and set the backPressedOnce variable to true. 
// We also create a handler to reset the backPressedOnce variable after a delay of 2 seconds. 
// If the current fragment is not NotesFragment, we proceed with the default behavior of super.onBackPressed().