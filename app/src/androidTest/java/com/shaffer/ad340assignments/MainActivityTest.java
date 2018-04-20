package com.shaffer.ad340assignments;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.intent.Intents.intended;

/**
 * MainActivity test for Assignment 2
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void hello_goodbyeWithRotate() {
        onView(withId(R.id.textView)).check(matches(withText(R.string.my_name)));
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText(R.string.goodbye)).perform(click());
        onView(withId(R.id.textView)).check(matches(withText(R.string.arrivederci)));
        TestUtils.rotateScreen(testRule.getActivity());
        onView(withId(R.id.textView)).check(matches(withText(R.string.arrivederci)));
    }

    @Test
    public void editTextMaintainedAfterRotate() {
        onView(withId(R.id.nameEditText)).perform(typeText("Melanie Shaffer"), closeSoftKeyboard());
        onView(withId(R.id.emailEditText)).perform(typeText("melsmail@hotmail.com"), closeSoftKeyboard());
        onView(withId(R.id.usernameEditText)).perform(typeText("melsmail"), closeSoftKeyboard());
        onView(withId(R.id.in_date)).perform(typeText("01/01/1974"), closeSoftKeyboard());
        TestUtils.rotateScreen(testRule.getActivity());
        onView(withId(R.id.nameEditText)).check(matches(withText("Melanie Shaffer")));
        onView(withId(R.id.emailEditText)).check(matches(withText("melsmail@hotmail.com")));
        onView(withId(R.id.usernameEditText)).check(matches(withText("melsmail")));
        onView(withId(R.id.in_date)).check(matches(withText("01/01/1974")));
    }

    @Test
    public void getExpectedEditTextErrors() {
        onView(withId(R.id.nameEditText)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.emailEditText)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.usernameEditText)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.in_date)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.submit_btn)).perform(click());
        onView(withId(R.id.nameEditText)).check(matches(hasErrorText("Please enter a valid name")));
        onView(withId(R.id.emailEditText)).check(matches(hasErrorText("Please enter a valid email address")));
        onView(withId(R.id.usernameEditText)).check(matches(hasErrorText("Please enter a username at least 5 characters long")));
        onView(withId(R.id.in_date)).check(matches(hasErrorText("Please enter a valid date in the MM/DD/YYYY format")));
    }

    public void inputProperEditText() {
        onView(withId(R.id.nameEditText)).perform(typeText("Melanie Shaffer"));
        onView(withId(R.id.emailEditText)).perform(typeText("melsmail@hotmail.com"));
        onView(withId(R.id.usernameEditText)).perform(typeText("melsmail"));
        onView(withId(R.id.in_date)).perform(typeText("01/01/1974"));
        onView(withId(R.id.nameEditText)).check(matches(withText("Melanie Shaffer")));
        onView(withId(R.id.emailEditText)).check(matches(withText("melsmail@hotmail.com")));
        onView(withId(R.id.usernameEditText)).check(matches(withText("melsmail")));
        onView(withId(R.id.in_date)).check(matches(withText("01/01/1974")));
    }

    @Test
    public void canGoToSecondActivityWithMessage() {
        onView(withId(R.id.nameEditText)).perform(typeText("Melanie Shaffer"), closeSoftKeyboard());
        onView(withId(R.id.emailEditText)).perform(typeText("melsmail@hotmail.com"), closeSoftKeyboard());
        onView(withId(R.id.usernameEditText)).perform(typeText("melsmail"), closeSoftKeyboard());
        onView(withId(R.id.in_date)).perform(typeText("01/01/1974"), closeSoftKeyboard());
        Intents.init();
        onView(withId(R.id.submit_btn)).perform(click());
        intended(hasComponent(SecondActivity.class.getName()));
        intended(hasExtra("username", "melsmail"));
        Intents.release();
    }


    // should not accept address missing @ symbol
    @Test
    public void invalidEmail() {
        onView(withId(R.id.emailEditText)).perform(typeText("melsmailhotmail.com"), closeSoftKeyboard());
        onView(withId(R.id.submit_btn)).perform(click());
        onView(withId(R.id.emailEditText)).check(matches(hasErrorText("Please enter a valid email address")));
    }

    // requires minimum of 5 characters
    @Test
    public void invalidUsername() {
        onView(withId(R.id.usernameEditText)).perform(typeText("mel"), closeSoftKeyboard());
        onView(withId(R.id.submit_btn)).perform(click());
        onView(withId(R.id.usernameEditText)).check(matches(hasErrorText("Please enter a username at least 5 characters long")));
    }

    // date format must be MM/DD/YYYY
    @Test
    public void invalidDateFormat() {
        onView(withId(R.id.in_date)).perform(typeText("01/01/74"), closeSoftKeyboard());
        onView(withId(R.id.submit_btn)).perform(click());
        onView(withId(R.id.in_date)).check(matches(hasErrorText("Please enter a valid date in the MM/DD/YYYY format")));
    }

    @Test
    public void ageTextViewWithRotate() {
        onView(withId(R.id.in_date)).perform(typeText("10/08/1974"), closeSoftKeyboard());
        onView(withId(R.id.ageTextView)).check(matches(withText(R.string.your_age_is)));
        onView(withId(R.id.confirm_age)).perform(click());
        onView(withId(R.id.ageTextView)).check(matches(withText("Your age is 43. Please click below to register.")));
        TestUtils.rotateScreen(testRule.getActivity());
    }


}
