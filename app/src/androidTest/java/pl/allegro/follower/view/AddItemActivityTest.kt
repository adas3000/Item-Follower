package pl.allegro.follower.view

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
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
}