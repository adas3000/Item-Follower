package pl.allegro.follower.view

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.junit.Test
import pl.allegro.follower.R
import pl.allegro.follower.view.ui.AddItemActivity

class AddItemActivityTest{




    @Test
    fun test_should_show_save_menu_option() {

        val activityScenario = ActivityScenario.launch(AddItemActivity::class.java)
        onView(withId(R.id.menu_action_save)).check(matches(isDisplayed()))
        activityScenario.close()
    }

    @Test
    fun test_should_add_add_go_back_to_main_activity() {
        val activityScenario = ActivityScenario.launch(AddItemActivity::class.java)
        onView(withId(R.id.editText_url)).perform(clearText(),typeText(
            "https://allegro.pl/oferta/macbook-pro-retina-13-i5-2-3ghz-8gb-128gb-2017-8306236078?reco_id=f1d704fc-5880-11ea-b039-246e96321930&sid=4ff40c840cdcf178556a07ba43c6f454bd9bac0cb20532ba39776a525078097b"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.menu_action_save)).perform(click())
        onView(withId(R.id.fab_add)).check(matches(isDisplayed()))
        activityScenario.close()
    }
}