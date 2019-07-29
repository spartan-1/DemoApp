package com.pnr.demoapp

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * ContextTest
 */
@RunWith(AndroidJUnit4::class)
class ContextTest {
    /**
     * Test method to test app context
     */
    @Test
    fun testAppContext() {
        val appContext = ApplicationProvider.getApplicationContext<Context>()
        assertEquals("com.pnr.demoapp", appContext.packageName)
    }
}