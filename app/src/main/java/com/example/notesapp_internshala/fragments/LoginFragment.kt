**Folder Name: fragments**

**File Name: LoginFragment.java**

**Line-by-line documented Code:**
```java
// Package declaration for the fragment class
package com.example.notesapp_internshala.fragments;

// Import necessary Android and Google libraries
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.navigation.findNavController;
import com.example.notesapp_internshala.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

// Class declaration for the LoginFragment
public class LoginFragment extends Fragment {

    // Declare member variables for Google sign-in and Firebase authentication
    private GoogleSignInClient mGoogleSignInClient; // Member variable to store the GoogleSignInClient
    private FirebaseAuth mAuth; // Member variable to store the FirebaseAuth instance

    // Companion object to define a static constant for the request code used in the sign-in intent
    companion object {
        private const val RC_SIGN_IN = 9001; // Constant to store the request code for the sign-in intent
    }

    // Declare a progress dialog to show during authentication
    lateinit var mProgressDialog: Dialog; // Member variable to store the progress dialog

    // onCreateView() method to inflate the fragment's layout and initialize its views
    @Override
    public View onCreateView(
        LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState
    ) {
        // Inflate the fragment's layout
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        // Initialize Firebase authentication
        mAuth = FirebaseAuth.getInstance(); // Initialize the FirebaseAuth instance

        // Configure Google Sign In options
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // Request an ID token from the GoogleSignInOptions
            .requestEmail() // Request an email address from the GoogleSignInOptions
            .build(); // Build the GoogleSignInOptions

        // Build a GoogleSignInClient with the specified options
        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso); // Build the GoogleSignInClient with the specified options

        // Find the sign-in button by its ID and set its onClickListener to call the signIn() method
        Button signInBtn = rootView.findViewById(R.id.btnSignIn); // Find the sign-in button by its ID
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(); // Call the signIn() method when the sign-in button is clicked
            }
        });

        // Return the inflated view
        return rootView; // Return the inflated view
    }

    // Method to initiate the sign-in process with Google
    private void signIn() {
        // Configure Google Sign In options
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // Request an ID token from the GoogleSignInOptions
            .requestEmail() // Request an email address from the GoogleSignInOptions
            .build(); // Build the GoogleSignInOptions

        // Build a GoogleSignInClient with the specified options
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(requireContext(), gso); // Build the GoogleSignInClient with the specified options

        // Create a sign-in intent
        Intent signInIntent = googleSignInClient.getSignInIntent(); // Create a sign-in intent using the GoogleSignInClient

        // Start the sign-in activity and pass in the request code
        startActivityForResult(signInIntent, RC_SIGN_IN); // Start the sign-in activity and pass in the request code

        // Show a progress dialog
        showProgressDialog(); // Show a progress dialog while the sign-in process is underway
    }

    // Method to handle the result of the sign-in activity
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Hide the progress dialog
        hideProgressDialog(); // Hide the progress dialog after the sign-in activity has finished

        // Check if the request code matches the expected one
        if (requestCode == RC_SIGN_IN) { // Check if the request code matches the expected one
            // Create a task to get the Google sign-in account from the passed-in intent
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data); // Create a task to get the Google sign-in account from the intent

            // Try to get the Google sign-in account
            try {
                // Get the Google sign-in account
                GoogleSignInAccount account = task.getResult(ApiException.class); // Get the Google sign-in account from the task

                // Show a progress dialog
                showProgressDialog(); // Show a progress dialog while the authentication process is underway

                // Attempt to authenticate with Firebase using the Google sign-in account
                firebaseAuthWithGoogle(account.getIdToken()); // Attempt to authenticate with Firebase using the Google sign-in account's ID token
            } catch (ApiException e) {
                // Log the error code
                Log.d("TAG", "onActivityResult: " + e.getStatusCode()); // Log the error code

                // Show a toast message indicating that Google sign-in failed
                Toast.makeText(context, "Google sign in failed: " + e.getMessage(), Toast.LENGTH_SHORT).show(); // Show a toast message indicating that Google sign-in failed
            }
        }
    }

    // Method to authenticate with Firebase using the Google sign-in account
    private void firebaseAuthWithGoogle(String idToken) {
        // Create a Google credential using the ID token
        GoogleAuthProvider.getCredential(idToken, null); // Create a Google credential using the ID token

        // Sign in with the Google credential