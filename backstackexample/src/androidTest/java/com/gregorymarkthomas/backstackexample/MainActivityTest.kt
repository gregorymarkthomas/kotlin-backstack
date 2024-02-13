package com.gregorymarkthomas.backstackexample

import android.content.pm.ActivityInfo
import android.os.Handler
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.gregorymarkthomas.backstack.view.BackstackActivity
import com.gregorymarkthomas.backstackexample.view.AView
import com.gregorymarkthomas.backstackexample.view.BView
import com.gregorymarkthomas.backstackexample.view.CView
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    val TAG = "MainActivityTest"

    @Test fun showsDefaultView() {
        launchActivity<MainActivity>().use { scenario ->
            scenario.onActivity { activity: BackstackActivity -> run {
                val bsActivity: BackstackActivity = activity
                assertEquals(1, bsActivity.getCurrentViewClasses().size)
                assertEquals(AView::class, bsActivity.getMostRecentView()::class)
            } }
        }
    }

    @Test fun newViewsAreAdded() {
        var backstack: List<String>
        launchActivity<MainActivity>().use { scenario ->

            scenario.onActivity { activity: BackstackActivity -> run {
                val bsActivity: BackstackActivity = activity
                bsActivity.goTo(BView())
                assertEquals(BView::class, bsActivity.getMostRecentView()::class)
                bsActivity.goTo(CView())
                assertEquals(CView::class, bsActivity.getMostRecentView()::class)

                backstack = bsActivity.getCurrentViewClasses()
                assertEquals(3, backstack.size)
                assertEquals(CView::class, bsActivity.getMostRecentView()::class)

                bsActivity.goTo(BView())
                backstack = bsActivity.getCurrentViewClasses()
                assertEquals(2, backstack.size)
                assertEquals(BView::class, bsActivity.getMostRecentView()::class)

                bsActivity.goTo(AView())
                backstack = bsActivity.getCurrentViewClasses()
                assertEquals(1, backstack.size)
                assertEquals(AView::class, bsActivity.getMostRecentView()::class)
            } }

            // ... UI interactions
        }
    }

    /**
     * NOTE: if MainActivity is to call onCreate() upon every orientation change (under normal app
     * operation), then this test should FAIL as MainActivity's backstack object is recreated
     * in onCreate().
     * This test PASSES, though, and it seems like Activity is not recreated upon every orientation
     * change (i.e. something enabled just for testing that works like
     * 'android:configChanges="orientation|screenSize|screenLayout|keyboardHidden"').
     */
    @Test fun viewsRemainsAfterOrientationChange() {
        var backstack: List<String>
        launchActivity<MainActivity>().use { scenario ->

            scenario.onActivity { activity: BackstackActivity -> run {
                val bsActivity: BackstackActivity = activity
                bsActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                bsActivity.goTo(BView())
                bsActivity.goTo(CView())
                backstack = bsActivity.getCurrentViewClasses()
                assertEquals(3, backstack.size)
                assertEquals(CView::class, bsActivity.getMostRecentView()::class)

                bsActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                while(bsActivity.requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    // Do nothing
                }
                backstack = bsActivity.getCurrentViewClasses()
                assertEquals(3, backstack.size)
                assertEquals("CView", backstack[2])
                assertEquals("BView", backstack[1])
                assertEquals("AView", backstack[0])

                bsActivity.goTo(BView())
                backstack = bsActivity.getCurrentViewClasses()
                assertEquals(2, backstack.size)
                assertEquals("BView", backstack[1])
                assertEquals("AView", backstack[0])

                bsActivity.goTo(AView())
                backstack = bsActivity.getCurrentViewClasses()
                assertEquals(1, backstack.size)
                assertEquals(AView::class, bsActivity.getMostRecentView()::class)
                assertEquals("AView", backstack[0])
            } }
        }
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.gregorymarkthomas.backstackexample", appContext.packageName)
    }
}