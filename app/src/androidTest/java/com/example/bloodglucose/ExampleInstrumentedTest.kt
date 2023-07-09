package com.example.bloodglucose

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.espresso.assertion.ViewAssertions.matches
import org.hamcrest.Matchers.containsString
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule()
    val activity = ActivityScenarioRule(GlucoseActivity::class.java)

    @Test
    fun enter_glucose_level() {
        onView(withId(R.id.glucose_level_edit_text))
            .perform(typeText("70"), closeSoftKeyboard())
        onView(withId(R.id.button_submit_glucose))
            .perform(click())
        onView(withId(R.id.button_glucose_back))
            .perform(click())
        onView(withId(R.id.button_sugar_level_profile))
            .perform(click())
    }
}