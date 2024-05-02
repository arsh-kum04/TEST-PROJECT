**Folder Name: NotesApp_Internshala**

**File Name: ExampleInstrumentedTest.java**

// Import the necessary libraries
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
// Run the test using JUnit4
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    // Test the application context
    fun useAppContext() {
        // Get the context of the app under test
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        // Assert that the package name of the app under test is correct
        assertEquals("com.example.notesapp_internshala", appContext.packageName)
    }
}