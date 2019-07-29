package com.pnr.demoapp.ui.screens.imageviewer

import android.view.View
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.pnr.demoapp.R
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * ImageViewerActivityTest
 */
@RunWith(AndroidJUnit4::class)
class ImageViewerActivityTest {
    @get:Rule
    val countryInfoActivityTestRule: ActivityTestRule<ImageViewerActivity> =
        ActivityTestRule(ImageViewerActivity::class.java, true, true)

    /**
     * Test to verify launch of full screen image
     */
    @Test
    fun testLaunchFullScreenImage() {
        val view: View = countryInfoActivityTestRule.activity.findViewById(R.id.image_full_screen)
        assertNotNull(view)
    }

}