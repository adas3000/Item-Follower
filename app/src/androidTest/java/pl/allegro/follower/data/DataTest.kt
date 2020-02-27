package pl.allegro.follower.data

import org.junit.Test
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import pl.allegro.follower.R
import pl.allegro.follower.view.ui.MainActivity



class DataTest {


    val itemUrlList= listOf<String>(
        "https://allegro.pl/oferta/hp-probook-4740s-i7-qm-8gb-512ssd-w10pro-gwar-8998947521",
        "https://allegro.pl/oferta/lenovo-ideapad-s340-15-i3-8gb-256ssd-fhd-touch-w10-8760523652",
        "https://allegro.pl/oferta/samsung-np3530ec-i5-2x2-5ghz-6gb-15-500gb-cb83-8995073951"
    )



    @Test
    fun test_add_data() {
        val activityScenario:ActivityScenario<MainActivity> = ActivityScenario.launch(
            MainActivity::class.java)

        var itemId = 0

        while(itemId<itemUrlList.size){
        onView(withId(R.id.fab_add)).perform(click())
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Name"))

        closeSoftKeyboard()
        onView(withId(R.id.editText_url)).perform(ViewActions.typeText(itemUrlList[itemId++]))

        closeSoftKeyboard()

        onView(withId(R.id.menu_action_save)).perform(click())
        Thread.sleep(500)
        }
    }
}