package com.example.studyapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.studyapp.activities.SplashActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ActivityInputOutputTest {

    @Rule
    public ActivityScenarioRule mActivityRule = new ActivityScenarioRule<>(
            SplashActivity.class);
/*
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.studyapp",
                appContext.getPackageName());
    }
*/
    @Test
    public void loginTest() {
        onView(withId(R.id.splashButton)).perform(click());
        onView(withId(R.id.testButton)).perform(click());

        onView(withId(R.id.loginEmailInput)).perform(
                typeText("ColeHorvat@gmail.com"));
        onView(withId(R.id.loginPassInput)).perform(
                typeText("Timbit10"));

        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.create_button)).check(matches(isDisplayed()));
    }

}
