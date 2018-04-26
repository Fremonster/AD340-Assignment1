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
            testIntent.putExtra("name", "Benedict Cumberbatch");
            testIntent.putExtra("age", "41");
            testIntent.putExtra("occupation", "Actor");
            testIntent.putExtra("description", "I like long walks on the beach and holding hands. I am looking for someone who loves bunnies and chocolate. I enjoy underwater basket weaving.");
            return testIntent;
        }
    };

    @Test
    public void setsRightMessageBasedOnIntentExtra() {
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
        String message = msg.toString();
        onView(withId(R.id.textView))
                .check(matches(withText(message)));
    }

    @Test
    public void messageUnchangedAfterRotate() {
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
        String message = msg.toString();
        onView(withId(R.id.textView))
                .check(matches(withText(message)));
        TestUtils.rotateScreen(activityTestRule.getActivity());
        onView(withId(R.id.textView))
                .check(matches(withText(message)));
    }
}