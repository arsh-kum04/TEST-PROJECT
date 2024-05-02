**Folder Name: fragments**

**File Name: LoginFragment.java**

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
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    
    // Companion object to define a static constant for the request code used in the sign-in intent
    companion object {
        private const val RC_SIGN_IN = 9001;
    }
    
    // Declare a progress dialog to show during authentication
    lateinit var mProgressDialog: Dialog;

    // onCreateView() method to inflate the fragment's layout and initialize its views
    @Override
    public View onCreateView(
        LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState
    ) {
        // Inflate the fragment's layout
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        // Initialize Firebase authentication
        mAuth = FirebaseAuth.getInstance();

        // Configure Google Sign In options
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build();

        // Build a GoogleSignInClient with the specified options
        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso);

        // Find the sign-in button by its ID and set its onClickListener to call the signIn() method
        Button signInBtn = rootView.findViewById(R.id.btnSignIn);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        // Return the inflated view
        return rootView;
    }

    // Method to initiate the sign-in process with Google
    private void signIn() {
        // Configure Google Sign In options
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build();

        // Build a GoogleSignInClient with the specified options
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(requireContext(), gso);
        
        // Create a sign-in intent
        Intent signInIntent = googleSignInClient.getSignInIntent();
        
        // Start the sign-in activity and pass in the request code
        startActivityForResult(signInIntent, RC_SIGN_IN);
        
        // Show a progress dialog
        showProgressDialog();
    }

    // Method to handle the result of the sign-in activity
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Hide the progress dialog
        hideProgressDialog();
        
        // Check if the request code matches the expected one
        if (requestCode == RC_SIGN_IN) {
            // Create a task to get the Google sign-in account from the passed-in intent
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            
            // Try to get the Google sign-in account
            try {
                // Get the Google sign-in account
                GoogleSignInAccount account = task.getResult(ApiException.class);
                
                // Show a progress dialog
                showProgressDialog();
                
                // Attempt to authenticate with Firebase using the Google sign-in account
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Log the error code
                Log.d("TAG", "onActivityResult: " + e.getStatusCode());
                
                // Show a toast message indicating that Google sign-in failed
                Toast.makeText(context, "Google sign in failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Method to authenticate with Firebase using the Google sign-in account
    private void firebaseAuthWithGoogle(String idToken) {
        // Create a Google credential using the ID token
        GoogleAuthProvider.getCredential(idToken, null);

        // Sign in with the Google credential
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Get the Firebase user
                        FirebaseUser user = mAuth.getCurrentUser();

                        // Hide the progress dialog
                        hideProgressDialog();

                        // Show a toast message indicating that the user is signed in
                        Toast.makeText(requireContext(), "Signed in as " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
                        
                        // Save the login state
                        saveLoginState(true);

                        // Navigate to the notes fragment
                        view.findNavController().navigate(R.id.loginFragmentToNotesFragment);
                    } else {
                        // Show a toast message indicating that authentication failed
                        Toast.makeText(context, "Authentication failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }
    
    // Method to save the login state in SharedPreferences
    private void saveLoginState(boolean isLoggedIn) {
        // Get the SharedPreferences instance
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);

        // Get an editor for the SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        
        // Set the "is_user_logged_in" key to the given value
        editor.putBoolean("is_user_logged_in", isLoggedIn);
        
        // Commit the changes to the SharedPreferences
        editor.apply();
    }
    
    // Method to check the login state when the fragment is resumed
    @Override
    public void onResume() {
        super.onResume();
        checkLoginState();
    }

    // Method to check the login state
    private void checkLoginState() {
        // Get the SharedPreferences instance
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);

        // Get the "is_user_logged_in" key from the SharedPreferences
        boolean isUserLoggedIn = sharedPreferences.getBoolean("is_user_logged_in", false);

        // If the user is logged in, navigate to the notes fragment
        if (isUserLoggedIn) {
            view.findNavController().navigate(R.id.loginFragmentToNotesFragment);
        }
    }

    // Method to show a progress dialog
    public void showProgressDialog() {
        // Create a new Dialog instance
        mProgressDialog = new Dialog(requireContext());
        
        // Set the content view for the Dialog
        mProgressDialog.setContentView(R.layout.dialog_progress);
        
        // Show the Dialog
        mProgressDialog.show();
    }

    // Method to hide the progress dialog
    public void hideProgressDialog() {
        // Dismiss the Dialog
        mProgressDialog.dismiss();
    }
}

```