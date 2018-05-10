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
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.intent.Intents.intended;

/**
 * MainActivity test for Assignment 4
 * Several of these tests only work in Portrait Screen Orientation
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void hello_goodbyeWithRotate() {
        onView(withId(R.id.textView)).check(matches(withText(R.string.my_name)));
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText(R.string.goodbye)).perform(scrollTo(), click());
        onView(withId(R.id.textView)).check(matches(withText(R.string.arrivederci)));
        TestUtils.rotateScreen(testRule.getActivity());
        onView(withId(R.id.textView)).check(matches(withText(R.string.arrivederci)));
    }

    @Test
    public void inputProperEditText() {
        onView(withId(R.id.nameEditText)).perform(typeText("Benedict Cumberbatch"), closeSoftKeyboard());
        onView(withId(R.id.emailEditText)).perform(typeText("benedict@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.usernameEditText)).perform(typeText("bcumberbatch"), closeSoftKeyboard());
        onView(withId(R.id.occupationEditText)).perform(typeText("Actor"), closeSoftKeyboard());
        onView(withId(R.id.descriptionEditText)).perform(typeText("I like long walks on the beach and holding hands. I am looking for someone who loves bunnies and chocolate. I enjoy underwater basket weaving."), closeSoftKeyboard());
        onView(withId(R.id.in_date)).perform(typeText("07/19/1976"), closeSoftKeyboard());
        onView(withId(R.id.nameEditText)).check(matches(withText("Benedict Cumberbatch")));
        onView(withId(R.id.emailEditText)).check(matches(withText("benedict@gmail.com")));
        onView(withId(R.id.usernameEditText)).check(matches(withText("bcumberbatch")));
        onView(withId(R.id.occupationEditText)).check(matches(withText("Actor")));
        onView(withId(R.id.descriptionEditText)).check(matches(withText("I like long walks on the beach and holding hands. I am looking for someone who loves bunnies and chocolate. I enjoy underwater basket weaving.")));
        onView(withId(R.id.in_date)).check(matches(withText("07/19/1976")));
    }

    @Test
    public void editTextMaintainedAfterRotate() {
        onView(withId(R.id.nameEditText)).perform(typeText("Benedict Cumberbatch"), closeSoftKeyboard());
        onView(withId(R.id.emailEditText)).perform(typeText("benedict@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.usernameEditText)).perform(typeText("bcumberbatch"), closeSoftKeyboard());
        onView(withId(R.id.occupationEditText)).perform(typeText("Actor"), closeSoftKeyboard());
        onView(withId(R.id.descriptionEditText)).perform(typeText("I like long walks on the beach and holding hands. I am looking for someone who loves bunnies and chocolate. I enjoy underwater basket weaving."), closeSoftKeyboard());
        onView(withId(R.id.in_date)).perform(typeText("07/19/1976"), closeSoftKeyboard());
        TestUtils.rotateScreen(testRule.getActivity());
        onView(withId(R.id.nameEditText)).check(matches(withText("Benedict Cumberbatch")));
        onView(withId(R.id.emailEditText)).check(matches(withText("benedict@gmail.com")));
        onView(withId(R.id.usernameEditText)).check(matches(withText("bcumberbatch")));
        onView(withId(R.id.occupationEditText)).check(matches(withText("Actor")));
        onView(withId(R.id.descriptionEditText)).check(matches(withText("I like long walks on the beach and holding hands. I am looking for someone who loves bunnies and chocolate. I enjoy underwater basket weaving.")));
        onView(withId(R.id.in_date)).check(matches(withText("07/19/1976")));
    }

    // check that get expected errors when enter no text into fields
    @Test
    public void getExpectedEditTextErrors() {
        closeSoftKeyboard();
        onView(withId(R.id.submit_btn)).perform(scrollTo(), click());
        onView(withId(R.id.nameEditText)).check(matches(hasErrorText("Please enter your name")));
        onView(withId(R.id.emailEditText)).check(matches(hasErrorText("Please enter a valid email address")));
        onView(withId(R.id.usernameEditText)).check(matches(hasErrorText("Please enter a username at least 5 characters long")));
        onView(withId(R.id.occupationEditText)).check(matches(hasErrorText("Please enter your occupation")));
        onView(withId(R.id.descriptionEditText)).check(matches(hasErrorText("Please enter a description")));
        onView(withId(R.id.in_date)).check(matches(hasErrorText("Please enter a valid date in the MM/DD/YYYY format")));
    }

    @Test
    public void canGoToSecondActivityWithMessage() {
        onView(withId(R.id.nameEditText)).perform(typeText("Benedict Cumberbatch"), closeSoftKeyboard());
        onView(withId(R.id.emailEditText)).perform(typeText("benedict@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.usernameEditText)).perform(typeText("bcumberbatch"), closeSoftKeyboard());
        onView(withId(R.id.occupationEditText)).perform(typeText("Actor"), closeSoftKeyboard());
        onView(withId(R.id.descriptionEditText)).perform(typeText("I like long walks on the beach and holding hands. I am looking for someone who loves bunnies and chocolate. I enjoy underwater basket weaving."), closeSoftKeyboard());
        onView(withId(R.id.in_date)).perform(typeText("07/19/1976"), closeSoftKeyboard());
        Intents.init();
        onView(withId(R.id.submit_btn)).perform(click());
        intended(hasComponent(SecondActivity.class.getName()));
        intended(hasExtra("name", "Benedict Cumberbatch"));
        intended(hasExtra("age", "41"));
        intended(hasExtra("occupation", "Actor"));
        intended(hasExtra("description", "I like long walks on the beach and holding hands. I am looking for someone who loves bunnies and chocolate. I enjoy underwater basket weaving."));
        Intents.release();
    }

    // requires minimum of 5 characters
    @Test
    public void invalidUsername() {
        onView(withId(R.id.usernameEditText)).perform(typeText("mel"), closeSoftKeyboard());
        onView(withId(R.id.submit_btn)).perform(scrollTo(), click());
        onView(withId(R.id.usernameEditText)).check(matches(hasErrorText("Please enter a username at least 5 characters long")));
    }

    // should not accept address missing @ symbol
    @Test
    public void invalidEmail() {
        onView(withId(R.id.emailEditText)).perform(typeText("melsmailhotmail.com"), closeSoftKeyboard());
        onView(withId(R.id.submit_btn)).perform(scrollTo(), click());
        onView(withId(R.id.emailEditText)).check(matches(hasErrorText("Please enter a valid email address")));
    }

    // should get error message in the date fields when someone under 18 attempts to register
    @Test
    public void testUnderEighteen() {
        onView(withId(R.id.nameEditText)).perform(typeText("Peter Parker"), closeSoftKeyboard());
        onView(withId(R.id.emailEditText)).perform(typeText("spiderman@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.usernameEditText)).perform(typeText("spidey"), closeSoftKeyboard());
        onView(withId(R.id.occupationEditText)).perform(typeText("Superhero"), closeSoftKeyboard());
        onView(withId(R.id.descriptionEditText)).perform(typeText("Looking for someone who will be understanding when I flake out on a date to fight crime. Must love science."), closeSoftKeyboard());
        onView(withId(R.id.in_date)).perform(typeText("07/10/2000"), closeSoftKeyboard());
        onView(withId(R.id.submit_btn)).perform(scrollTo(), click());
        onView(withId(R.id.in_date)).check(matches(hasErrorText("Must be 18 or older to register.")));
    }

    // date format must be MM/DD/YYYY
    @Test
    public void invalidDateFormat() {
        onView(withId(R.id.in_date)).perform(typeText("01/01/74"), closeSoftKeyboard());
        onView(withId(R.id.submit_btn)).perform(scrollTo(), click());
        onView(withId(R.id.in_date)).check(matches(hasErrorText("Please enter a valid date in the MM/DD/YYYY format")));
    }

    // checks that TextView showing calculated age is displayed after rotate
    @Test
    public void ageTextViewWithRotate() {
        onView(withId(R.id.nameEditText)).perform(typeText("Benedict Cumberbatch"), closeSoftKeyboard());
        onView(withId(R.id.emailEditText)).perform(typeText("benedict@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.usernameEditText)).perform(typeText("bcumberbatch"), closeSoftKeyboard());
        onView(withId(R.id.occupationEditText)).perform(typeText("Actor"), closeSoftKeyboard());
        onView(withId(R.id.descriptionEditText)).perform(typeText("I like long walks on the beach and holding hands. I am looking for someone who loves bunnies and chocolate. I enjoy underwater basket weaving."), closeSoftKeyboard());
        onView(withId(R.id.in_date)).perform(typeText("07/19/1976"), closeSoftKeyboard());
        onView(withId(R.id.ageTextView)).check(matches(withText(R.string.your_age_is)));
        onView(withId(R.id.confirm_age)).perform(scrollTo(), click());
        onView(withId(R.id.ageTextView)).check(matches(withText("Your age is 41. Please click below to register.")));
        TestUtils.rotateScreen(testRule.getActivity());
        closeSoftKeyboard();
        onView(withId(R.id.ageTextView)).check(matches(withText("Your age is 41. Please click below to register.")));
    }

    // tests datepicker
    @Test
    public void testDatePicker() {
        TestUtils.setDate(R.id.date_btn, 1976, 7, 19);
        onView(withId(R.id.in_date)).check(matches(withText("07/19/1976")));
    }

    // Test the TextView message when someone under 18 hits the Confirm Over 18 button
    @Test
    public void testTextViewUnderEighteen() {
        onView(withId(R.id.nameEditText)).perform(typeText("Peter Parker"), closeSoftKeyboard());
        onView(withId(R.id.emailEditText)).perform(typeText("spiderman@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.usernameEditText)).perform(typeText("spidey"), closeSoftKeyboard());
        onView(withId(R.id.occupationEditText)).perform(typeText("Superhero"), closeSoftKeyboard());
        onView(withId(R.id.descriptionEditText)).perform(typeText("Looking for someone who will be understanding when I flake out on a date to fight crime. Must love science."), closeSoftKeyboard());
        onView(withId(R.id.in_date)).perform(typeText("07/10/2000"), closeSoftKeyboard());
        onView(withId(R.id.ageTextView)).check(matches(withText(R.string.your_age_is)));
        onView(withId(R.id.confirm_age)).perform(scrollTo(), click());
        onView(withId(R.id.ageTextView)).check(matches(withText("Your age is 17. You must be at least 18 to register.")));
    }

}
