package pl.allegro.follower.view

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Assert.*
import org.junit.Test
import pl.allegro.follower.R

class MainActivityTest{


    @Test
    fun test_should_show_add_activity() {

        val activityScenario:ActivityScenario<MainActivity> = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.fab_add)).perform(click())

        closeSoftKeyboard()

        onView(withId(R.id.editText_url)).check(matches(isDisplayed()))
        pressBack()

        onView(withId(R.id.fab_add)).check(matches(isDisplayed()))

        activityScenario.close()
    }


}