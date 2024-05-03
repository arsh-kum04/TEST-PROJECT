**Folder Name: NotesApp_Internshala**

**File Name: ExampleInstrumentedTest.java**

// Importing the necessary Android testing libraries.
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert.*;

// Specifying the test runner as JUnit4.
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    // This test method is annotated with @Test to indicate that it should be run as a test.
    fun useAppContext() {
        // Get the application context using InstrumentationRegistry.getInstrumentation().targetContext.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        // Assert that the package name of the application under test is correct using assertEquals().
        assertEquals("com.example.notesapp_internshala", appContext.packageName)
    }
}