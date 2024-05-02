---
**Folder Name:** fragments
**File Name:** LoginFragment.kt

```kotlin
// This is LoginFragment class which is responsible for login functionality for the app.
class LoginFragment : Fragment() {

    // Declare the variables
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mProgressDialog: Dialog

    companion object {
        private const val RC_SIGN_IN = 9001
    }

    // Override onCreateView to inflate the fragment's layout and initialize views
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_login, container, false)

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()

        // Configure Google Sign In options
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        // Create a GoogleSignInClient to initiate the sign-in flow
        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        // Get the sign-in button from the layout
        val signInBtn: Button = rootView.findViewById(R.id.btnSignIn)

        // Set up a click listener for the sign-in button
        signInBtn.setOnClickListener {
            // Launch the sign-in flow
            signIn()
        }

        // Return the inflated view
        return rootView
    }

    // Initiate the sign-in flow
    private fun signIn() {
        // Configure Google Sign In options
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        // Create a GoogleSignInClient to initiate the sign-in flow
        val googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        // Create a sign-in intent
        val signInIntent = googleSignInClient.signInIntent

        // Start the sign-in activity for result
        startActivityForResult(signInIntent, RC_SIGN_IN)

        // Show a progress dialog while the sign-in flow is in progress
        showProgressDialog()
    }

    // Handle the result of the sign-in activity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Dismiss the progress dialog
        hideProgressDialog()

        // Check if the request code matches the sign-in request
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Get the account credentials
                val account = task.getResult(ApiException::class.java)
                showProgressDialog()
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Sign in failed
                Log.d("TAG", "onActivityResult: ${e.statusCode}")
                Toast.makeText(context, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Exchange the Google ID token for a Firebase credential
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val user = mAuth.currentUser
                    hideProgressDialog()
                    Toast.makeText(requireContext(), "Signed in as ${user?.displayName}", Toast.LENGTH_SHORT).show()
                    saveLoginState(true)
                    view?.findNavController()?.navigate(R.id.loginFragmentToNotesFragment)
                } else {
                    Toast.makeText(context, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    // Save the login state in SharedPreferences
    private fun saveLoginState(isLoggedIn: Boolean) {
        // Access SharedPreferences
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        // Save the login state
        val editor = sharedPreferences.edit()
        editor.putBoolean("is_user_logged_in", isLoggedIn)
        editor.apply()
    }

    // Override onResume to check the login state
    override fun onResume() {
        super.onResume()

        // Check the login state
        checkLoginState()
    }

    // Check the login state and navigate to NotesFragment if already logged in
    private fun checkLoginState() {
        // Access SharedPreferences
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        // Retrieve the login state
        val isUserLoggedIn = sharedPreferences.getBoolean("is_user_logged_in", false)

        if (isUserLoggedIn) {
            view?.findNavController()?.navigate(R.id.loginFragmentToNotesFragment)
        }
    }

    // Show a progress dialog
    fun showProgressDialog() {
        mProgressDialog = Dialog(requireContext())
        mProgressDialog.setContentView(R.layout.dialog_progress)

        mProgressDialog.show()
    }

    // Hide the progress dialog
    fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }
}
```