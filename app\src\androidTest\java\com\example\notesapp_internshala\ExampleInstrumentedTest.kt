**Folder Name: NotesApp_Internshala**

**File Name: ExampleInstrumentedTest.java**

```
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
@RunWith(AndroidJUnit4::class) // Run the test using JUnit4
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.notesapp_internshala", appContext.packageName) // Assert that the package name of the app under test is correct
    }
}
```