**Folder Name: NotesApp_Internshala**

**File Name: ExampleInstrumentedTest.java**

// Importing the necessary Android testing libraries.
// This line imports the Android testing platform's app module.
import androidx.test.platform.app.InstrumentationRegistry;
// This line imports the Android testing extension for JUnit runners.
import androidx.test.ext.junit.runners.AndroidJUnit4;
// This line imports the JUnit library for writing test cases.
import org.junit.Test;
// This line imports the JUnit runner for running test cases.
import org.junit.runner.RunWith;
// This line imports the JUnit assertion library for verifying test results.
import org.junit.Assert.*;

// Specifying the test runner as JUnit4.
// This line specifies that the test runner to be used is JUnit4.
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    // This test method is annotated with @Test to indicate that it should be run as a test.
    fun useAppContext() {
        // Get the application context using InstrumentationRegistry.getInstrumentation().targetContext.
        // This line gets the application context using the InstrumentationRegistry.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        // Assert that the package name of the application under test is correct using assertEquals().
        // This line asserts that the package name of the application under test is correct.
        assertEquals("com.example.notesapp_internshala", appContext.packageName)
    }
}