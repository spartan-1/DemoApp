package com.pnr.demoapp.ui.screens.countryinfo.activities

import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.pnr.demoapp.ApiResponseHelper
import com.pnr.demoapp.R
import com.pnr.demoapp.restclient.util.Constants
import com.squareup.okhttp.mockwebserver.MockResponse
import com.squareup.okhttp.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.core.AllOf.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * CountryInfoActivityTest
 */
@RunWith(AndroidJUnit4::class)
class CountryInfoActivityTest {

    @get:Rule
    val countryInfoActivityTestRule: ActivityTestRule<CountryInfoActivity> =
        ActivityTestRule(CountryInfoActivity::class.java, true, false)

    private lateinit var mockWebService: MockWebServer

    @Before
    fun setUp() {
        mockWebService = MockWebServer()
        mockWebService.start()
        Constants.BASE_URL = mockWebService.url("/").toString()
    }

    @After
    fun tearDown() {
        mockWebService.shutdown()
    }

    /**
     * Test success scenario
     */
    @Test
    @Throws(Exception::class)
    fun testSuccessScenario() {
        mockWebService.enqueue(
            MockResponse().setResponseCode(Constants.HTTP_OK).setBody(
                ApiResponseHelper()
                    .getMockResponseFromFile(InstrumentationRegistry.getInstrumentation().context, "success_200.json")
            )
        )
        countryInfoActivityTestRule.launchActivity(Intent())
        onView(
            allOf<View>(
                instanceOf<View>(TextView::class.java),
                withParent(withResourceName("toolbar"))
            )
        )
            .check(matches(withText("About Canada")))
    }

    /**
     * Test failure scenario
     */
    @Test
    @Throws(Exception::class)
    fun testNoDataErrorMessage() {
        mockWebService.enqueue(
            MockResponse().setResponseCode(Constants.HTTP_NOT_FOUND).setBody(
                ApiResponseHelper()
                    .getMockResponseFromFile(InstrumentationRegistry.getInstrumentation().context, "not_found_404.json")
            )
        )
        countryInfoActivityTestRule.launchActivity(Intent())
        onView(withText(R.string.error_empty_recyclerview)).check(matches(isDisplayed()))
    }

}