package com.shaffer.ad340assignments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.shaffer.ad340assignments.models.MatchItem;
import com.shaffer.ad340assignments.viewmodels.MatchesViewModel;

public class SecondActivity extends AppCompatActivity implements MatchesContentFragment.OnListFragmentInteractionListener {

    private static final String TAG = SecondActivity.class.getSimpleName();
    private MatchesViewModel viewModel;
    private Adapter adapter;
    private String name;
    private String age;
    private String occupation;
    private String description;
    private String profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        assert b != null;
        if (b.containsKey("name")) {
            name = b.getString("name");
        }
        if (b.containsKey("age")) {
            age = b.getString("age");
        }
        if (b.containsKey("occupation")) {
            occupation = b.getString("occupation");
        }
        if (b.containsKey("description")) {
            description = b.getString("description");
        }
        setProfile();
        setContentView(R.layout.activity_second);
        // create the view model
        viewModel = new MatchesViewModel();
        // get fragment manager from adapter
        adapter = new Adapter(getSupportFragmentManager());
        // Adding Toolbar to screen
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Setting ViewPager for each Tabs
        ViewPager viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        // Set Tabs inside Toolbar
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        Log.i(TAG, "onCreate()");
    }

    @Override
    public void onListFragmentInteraction(MatchItem item) {
        if (!item.liked) {
            item.liked = true;
        } else {
            item.liked = false;
        }
        viewModel.updateMatchItem(item);
    }

    @Override
    protected void onPause() {
        viewModel.clear();
        super.onPause();
        Log.i(TAG, "onPause()");
    }

    // Sets the profile string
    private void setProfile() {
        StringBuilder msg = new StringBuilder("YOUR PROFILE\n");
        msg.append("Name: ");
        msg.append(name);
        msg.append("\n");
        msg.append("Age: ");
        msg.append(age);
        msg.append("\n");
        msg.append("Occupation: ");
        msg.append(occupation);
        msg.append("\n");
        msg.append("Description: ");
        msg.append(description);
        msg.append("\n");
        profile = msg.toString();
    }

    // Gets the profile string
    public String getProfile() {
        if (profile != null) {
            return profile;
        } else {
            return "This isn't working";
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey("profile")) {
            profile = savedInstanceState.getString("profile");
        }
        Log.i(TAG, "onRestoreInstanceState()");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("profile", profile);
        Log.i(TAG, "onSaveInstanceState()");
    }

    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {
        // create fragments
        ProfileContentFragment profileContentFragment = new ProfileContentFragment();
        MatchesContentFragment matchesContentFragment = new MatchesContentFragment();
        SettingsContentFragment settingsContentFragment = new SettingsContentFragment();
        // add profile data to profileContentFragment
        Bundle bundle = new Bundle();
        bundle.putString("profile", getProfile());
        profileContentFragment.setArguments(bundle);
        // add fragments to adapter and set tab names
        adapter.addFragment(profileContentFragment, "Profile"); // could addFragment with bundle here adapter.addFragment(new ProfileTabFragment(), "Profile", getIntent().getExtras());
        adapter.addFragment(matchesContentFragment, "Matches");
        adapter.addFragment(settingsContentFragment, "Settings");
        viewPager.setAdapter(adapter);
        Log.i(TAG, "setupViewPager()");
    }

    public void goToMain(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SecondActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }

}
