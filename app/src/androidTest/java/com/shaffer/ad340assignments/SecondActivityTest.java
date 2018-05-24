package com.shaffer.ad340assignments;

import android.content.Intent;
import android.os.SystemClock;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.action.ViewActions.click;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;

public class SecondActivityTest {

    @Rule
    public ActivityTestRule<SecondActivity> activityTestRule
            = new ActivityTestRule<SecondActivity>(SecondActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Intent testIntent = new Intent();
            testIntent.putExtra("name", "Benedict Cumberbatch");
            testIntent.putExtra("age", "41");
            testIntent.putExtra("occupation", "Actor");
            testIntent.putExtra("description", "I like long walks on the beach and holding hands. I am looking for someone who loves bunnies and chocolate. I enjoy underwater basket weaving.");
            return testIntent;
        }
    };

    @Test
    public void testProfileTab() {
        Matcher<View> matcher = allOf(withText("Profile"),
                isDescendantOfA(withId(R.id.tabs)));
        onView(matcher).perform(click());
        SystemClock.sleep(800);
        String profile =  getProfile();
        onView(withId(R.id.textView))
                .check(matches(withText(profile)));
    }

    @Test
    public void canUpdateWhenInputNewSettingsIntoSettingsTab() {
        Matcher<View> matcher = allOf(withText("Settings"),
                isDescendantOfA(withId(R.id.tabs)));
        onView(matcher).perform(click());
        SystemClock.sleep(800);
        TestUtils.setTime(R.id.timePicker, 12, 30);
        onView(withId(R.id.maxDistanceEditText)).perform(clearText(), typeText("20"), closeSoftKeyboard());
        onView(withId(R.id.genderEditText)).perform(clearText(), typeText("female"), closeSoftKeyboard());
        onView(withId(R.id.minAgeEditText)).perform(clearText(), typeText("30"), closeSoftKeyboard());
        onView(withId(R.id.maxAgeEditText)).perform(clearText(), typeText("35"), closeSoftKeyboard());
        onView(withId(R.id.settingsTextView)).check(matches(withText(R.string.your_settings)));
        onView(withId(R.id.dailyMatchTime)).check(matches(withText(R.string.daily_match_reminder_time)));
        onView(withId(R.id.distanceFromUserTextView)).check(matches(withText(R.string.max_distance_away_in_miles)));
        onView(withId(R.id.ageRangeTextView)).check(matches(withText(R.string.age_range)));
        onView(withId(R.id.maxDistanceEditText)).check(matches(withText("20")));
        onView(withId(R.id.genderEditText)).check(matches(withText("female")));
        onView(withId(R.id.minAgeEditText)).check(matches(withText("30")));
        onView(withId(R.id.maxAgeEditText)).check(matches(withText("35")));
    }

    @Test
    public void inputProperEditTextIntoSettingsTabAndSaveSettings() {
        Matcher<View> matcher = allOf(withText("Settings"),
                isDescendantOfA(withId(R.id.tabs)));
        onView(matcher).perform(click());
        SystemClock.sleep(800);
        onView(withId(R.id.maxDistanceEditText)).perform(clearText(), typeText("20"), closeSoftKeyboard());
        onView(withId(R.id.genderEditText)).perform(clearText(), typeText("female"), closeSoftKeyboard());
        onView(withId(R.id.minAgeEditText)).perform(clearText(), typeText("30"), closeSoftKeyboard());
        onView(withId(R.id.maxAgeEditText)).perform(clearText(), typeText("35"), closeSoftKeyboard());
        onView(withId(R.id.settings_btn)).perform(scrollTo(), click());
        onView(withId(R.id.settingsTextView)).check(matches(withText(R.string.your_settings)));
        onView(withId(R.id.dailyMatchTime)).check(matches(withText(R.string.daily_match_reminder_time)));
        onView(withId(R.id.distanceFromUserTextView)).check(matches(withText(R.string.max_distance_away_in_miles)));
        onView(withId(R.id.ageRangeTextView)).check(matches(withText(R.string.age_range)));
        onView(withId(R.id.maxDistanceEditText)).check(matches(withText("20")));
        onView(withId(R.id.genderEditText)).check(matches(withText("female")));
        onView(withId(R.id.minAgeEditText)).check(matches(withText("30")));
        onView(withId(R.id.maxAgeEditText)).check(matches(withText("35")));
    }

    @Test
    public void inputNonNumericDistanceIntoSettingsTab() {
        Matcher<View> matcher = allOf(withText("Settings"),
                isDescendantOfA(withId(R.id.tabs)));
        onView(matcher).perform(click());
        SystemClock.sleep(800);
        onView(withId(R.id.maxDistanceEditText)).perform(clearText(), typeText("Twenty"), closeSoftKeyboard());
        onView(withId(R.id.genderEditText)).perform(clearText(), typeText("female"), closeSoftKeyboard());
        onView(withId(R.id.minAgeEditText)).perform(clearText(), typeText("30"), closeSoftKeyboard());
        onView(withId(R.id.maxAgeEditText)).perform(clearText(), typeText("35"), closeSoftKeyboard());
        onView(withId(R.id.settings_btn)).perform(scrollTo(), click());
        onView(withId(R.id.maxDistanceEditText)).check(matches(hasErrorText("Please enter a valid number")));
    }

    @Test
    public void inputNonNumericMinAgeIntoSettingsTab() {
        Matcher<View> matcher = allOf(withText("Settings"),
                isDescendantOfA(withId(R.id.tabs)));
        onView(matcher).perform(click());
        SystemClock.sleep(800);
        onView(withId(R.id.maxDistanceEditText)).perform(clearText(), typeText("20"), closeSoftKeyboard());
        onView(withId(R.id.genderEditText)).perform(clearText(), typeText("female"), closeSoftKeyboard());
        onView(withId(R.id.minAgeEditText)).perform(clearText(), typeText("Thirty"), closeSoftKeyboard());
        onView(withId(R.id.maxAgeEditText)).perform(clearText(), typeText("35"), closeSoftKeyboard());
        onView(withId(R.id.settings_btn)).perform(scrollTo(), click());
        onView(withId(R.id.minAgeEditText)).check(matches(hasErrorText("Please enter a valid number")));
    }

    @Test
    public void inputNonNumericMinAgeUnderEighteenIntoSettingsTab() {
        Matcher<View> matcher = allOf(withText("Settings"),
                isDescendantOfA(withId(R.id.tabs)));
        onView(matcher).perform(click());
        SystemClock.sleep(800);
        onView(withId(R.id.maxDistanceEditText)).perform(clearText(), typeText("20"), closeSoftKeyboard());
        onView(withId(R.id.genderEditText)).perform(clearText(), typeText("female"), closeSoftKeyboard());
        onView(withId(R.id.minAgeEditText)).perform(clearText(), typeText("16"), closeSoftKeyboard());
        onView(withId(R.id.maxAgeEditText)).perform(clearText(), typeText("35"), closeSoftKeyboard());
        onView(withId(R.id.settings_btn)).perform(scrollTo(), click());
        onView(withId(R.id.minAgeEditText)).check(matches(hasErrorText("The minimum age is 18")));
    }

    @Test
    public void inputNonNumericMaxAgeIntoSettingsTab() {
        Matcher<View> matcher = allOf(withText("Settings"),
                isDescendantOfA(withId(R.id.tabs)));
        onView(matcher).perform(click());
        SystemClock.sleep(800);
        onView(withId(R.id.maxDistanceEditText)).perform(clearText(), typeText("20"), closeSoftKeyboard());
        onView(withId(R.id.genderEditText)).perform(clearText(), typeText("female"), closeSoftKeyboard());
        onView(withId(R.id.minAgeEditText)).perform(clearText(), typeText("30"), closeSoftKeyboard());
        onView(withId(R.id.maxAgeEditText)).perform(clearText(), typeText("Thirty-five"), closeSoftKeyboard());
        onView(withId(R.id.settings_btn)).perform(scrollTo(), click());
        onView(withId(R.id.maxAgeEditText)).check(matches(hasErrorText("Please enter a valid number")));
    }

    @Test
    public void testMatchesTab() {
        Matcher<View> matcher = allOf(withText("Matches"),
                isDescendantOfA(withId(R.id.tabs)));
        onView(matcher).perform(click());
        SystemClock.sleep(800);
        onView(withId(R.id.my_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.my_recycler_view)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.favorite_button)));
        onView(withText("You liked Cool Guy Mike")).inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    private static class MyViewAction {

        public static ViewAction clickChildViewWithId(final int id) {
            return new ViewAction() {
                @Override
                public Matcher<View> getConstraints() {
                    return null;
                }

                @Override
                public String getDescription() {
                    return "Click on a child view with specified id.";
                }

                @Override
                public void perform(UiController uiController, View view) {
                    View v = view.findViewById(id);
                    v.performClick();
                }
            };
        }

    }

    private String getProfile() {
        StringBuilder msg = new StringBuilder("YOUR PROFILE\n");
        msg.append("Name: ");
        msg.append("Benedict Cumberbatch");
        msg.append("\n");
        msg.append("Age: ");
        msg.append("41");
        msg.append("\n");
        msg.append("Occupation: ");
        msg.append("Actor");
        msg.append("\n");
        msg.append("Description: ");
        msg.append("I like long walks on the beach and holding hands. I am looking for someone who loves bunnies and chocolate. I enjoy underwater basket weaving.");
        msg.append("\n");
        return msg.toString();
    }

    @Test
    public void messageUnchangedAfterRotate() {
        Matcher<View> matcher = allOf(withText("Profile"),
                isDescendantOfA(withId(R.id.tabs)));
        onView(matcher).perform(click());
        SystemClock.sleep(800);
        String profile =  getProfile();
        onView(withId(R.id.textView))
                .check(matches(withText(profile)));
        TestUtils.rotateScreen(activityTestRule.getActivity());
        onView(withId(R.id.textView))
                .check(matches(withText(profile)));
    }
}