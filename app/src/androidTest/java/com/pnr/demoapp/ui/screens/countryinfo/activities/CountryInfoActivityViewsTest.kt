package com.pnr.demoapp.ui.screens.countryinfo.activities

import android.view.View
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.pnr.demoapp.R
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * CountryInfoActivityViewsTest
 */
@RunWith(AndroidJUnit4::class)
class CountryInfoActivityViewsTest {

    @get:Rule
    val countryInfoActivityTestRule: ActivityTestRule<CountryInfoActivity> =
        ActivityTestRule(CountryInfoActivity::class.java, false, true)

    /**
     * Test launch of activity
     */
    @Test
    fun testLaunch() {
        val view: View = countryInfoActivityTestRule.activity.findViewById(R.id.activity_country_info_constraint_layout)
        assertNotNull(view)
    }

    /**
     * Test toolbar view
     */
    @Test
    fun testToolBarView() {
        val view: View = countryInfoActivityTestRule.activity.findViewById(R.id.toolbar)
        assertNotNull(view)
    }

    /**
     * Test data container view
     */
    @Test
    fun testDataContainerView() {
        val view: View = countryInfoActivityTestRule.activity.findViewById(R.id.content_layout)
        assertNotNull(view)
    }

}