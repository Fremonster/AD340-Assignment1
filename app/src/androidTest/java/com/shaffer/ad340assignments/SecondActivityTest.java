package com.shaffer.ad340assignments;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class SecondActivityTest {

    @Rule
    public ActivityTestRule<SecondActivity> activityTestRule
            = new ActivityTestRule<SecondActivity>(SecondActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Intent testIntent = new Intent();
            testIntent.putExtra("username", "melsmail");
            return testIntent;
        }
    };

    @Test
    public void setsRightMessageBasedOnIntentExtra() {
        onView(withId(R.id.textView))
                .check(matches(withText("Thanks for Signing Up\nmelsmail!")));
    }

    @Test
    public void messageUnchangedAfterRotate() {
        onView(withId(R.id.textView))
                .check(matches(withText("Thanks for Signing Up\nmelsmail!")));
        TestUtils.rotateScreen(activityTestRule.getActivity());
        onView(withId(R.id.textView))
                .check(matches(withText("Thanks for Signing Up\nmelsmail!")));
    }
}