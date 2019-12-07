package pl.pollub.trenzoneapp

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented account_details_activity, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under account_details_activity.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("pl.pollub.trenzoneapp", appContext.packageName)
    }
}
