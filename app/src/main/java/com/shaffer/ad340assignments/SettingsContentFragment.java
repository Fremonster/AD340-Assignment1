package com.shaffer.ad340assignments;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.shaffer.ad340assignments.entity.Settings;

import java.lang.ref.WeakReference;
import java.sql.Time;
import java.util.List;

/**
 * Settings
 */
public class SettingsContentFragment extends Fragment {

    private static String TAG = SettingsContentFragment.class.getSimpleName();
    TimePicker tp;
    EditText maxDistanceEditText;
    EditText genderEditText;
    EditText minAgeEditText;
    EditText maxAgeEditText;
    TextView dailyMatchTime;
    TextView ageRangeTextView;
    boolean isPublic;
    TextView settingsTextView;
    RadioGroup radioGroup;
    RadioButton radioPublic;
    RadioButton radioPrivate;
    Button settingsButton;
    String time;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG, "onAttach()");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        time = "";
        View rootView =  inflater.inflate(R.layout.item_settings, container, false);
        dailyMatchTime = rootView.findViewById(R.id.dailyMatchTime);
        tp = rootView.findViewById(R.id.timePicker);
        tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener(){
                @Override
                public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                    time = view.getHour() + ":" + view.getMinute();
                }
            });
        settingsTextView = rootView.findViewById(R.id.settingsTextView);
        maxDistanceEditText = rootView.findViewById(R.id.maxDistanceEditText);
        genderEditText = rootView.findViewById(R.id.genderEditText);
        isPublic = false;
        radioGroup = rootView.findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_public:
                        isPublic = true;
                        break;
                    case R.id.radio_private:
                        isPublic = false;
                        break;
                }
            }
        });
        radioPublic = rootView.findViewById(R.id.radio_public);
        radioPrivate = rootView.findViewById(R.id.radio_private);
        ageRangeTextView= rootView.findViewById(R.id.ageRangeTextView);
        minAgeEditText = rootView.findViewById(R.id.minAgeEditText);
        maxAgeEditText = rootView.findViewById(R.id.maxAgeEditText);
        settingsButton = rootView.findViewById(R.id.settings_btn);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateDatabase(v);
            }
        });
        new GetSettingsTask(this.getActivity(), this, 1).execute();
        return rootView;
    }

    public void updateDatabase(View view) {
        boolean valid = true;
        Settings userSettings = new Settings();
        int userId = 1; // only 1 user
        String maxDistance = this.maxDistanceEditText.getText().toString();
        if (!Util.isNumeric(maxDistance)) {
            valid = false;
            maxDistanceEditText.setError("Please enter a valid number");
        }
        String gender = this.genderEditText.getText().toString();
        String minAge = this.minAgeEditText.getText().toString();
        if (Util.isNumeric(minAge)) {
            int minAgeNum = Integer.valueOf(minAge);
            if (minAgeNum < 18) {
                valid = false;
                minAgeEditText.setError("The minimum age is 18");
            }
        } else {
            valid = false;
            minAgeEditText.setError("Please enter a valid number");
        }
        String maxAge = this.maxAgeEditText.getText().toString();
        if (!Util.isNumeric(maxAge)) {
            valid = false;
            maxAgeEditText.setError("Please enter a valid number");
        }
        if (valid) {
            userSettings.setId(userId);
            userSettings.setDailyMatchReminderTime(time);
            userSettings.setMaxDistance(maxDistance);
            userSettings.setGender(gender);
            userSettings.setMinAge(minAge);
            userSettings.setMaxAge(maxAge);
            userSettings.setPublic(isPublic);
            new UpdateSettingsTask(this.getActivity(), this, userSettings).execute();
            Toast.makeText(view.getContext(),"Settings saved", Toast.LENGTH_LONG).show();
        }
    }

    // inserts or updates settings
    private static class UpdateSettingsTask extends AsyncTask<Void, Void, Settings> {

        private WeakReference<Activity> weakActivity;
        private Settings settings;
        private WeakReference<SettingsContentFragment> weakFragment;
        private SettingsContentFragment fragment;

        public UpdateSettingsTask(Activity activity, SettingsContentFragment fragment, Settings settings) {
            weakActivity = new WeakReference<>(activity);
            this.settings = settings;
            this.weakFragment = new WeakReference<>(fragment);
        }

        @Override
        protected Settings doInBackground(Void... voids) {
            Activity activity = weakActivity.get();
            if(activity == null) {
                return null;
            }

            AppDatabase db = AppDatabaseSingleton.getDatabase(activity.getApplicationContext());

            db.settingsDao().insertAll(settings);
            return settings;
        }

        @Override
        protected void onPostExecute(Settings settings) {
            this.fragment = weakFragment.get();
            if(settings == null || fragment == null) {
                return;
            }
            fragment.genderEditText.setText(settings.getGender());
            boolean isPublic = settings.isPublic();
            if (isPublic) {
                fragment.radioPublic.toggle();
            } else {
                fragment.radioPrivate.toggle();
            }
            fragment.maxDistanceEditText.setText(settings.getMaxDistance());
            fragment.minAgeEditText.setText(settings.getMinAge());
            fragment.maxAgeEditText.setText(settings.getMaxAge());
        }
    }

    private static class GetSettingsTask extends AsyncTask<Void, Void, Settings> {

        private WeakReference<Activity> weakActivity;
        private WeakReference<SettingsContentFragment> weakFragment;
        private SettingsContentFragment fragment;
        private int userId;

        public GetSettingsTask(Activity activity, SettingsContentFragment fragment, int userId) {
            weakActivity = new WeakReference<>(activity);
            this.userId = userId;
            this.weakFragment = new WeakReference<>(fragment);
        }

        @Override
        protected Settings doInBackground(Void... voids) {
            Activity activity = weakActivity.get();
            if(activity == null) {
                return null;
            }

            AppDatabase db = AppDatabaseSingleton.getDatabase(activity.getApplicationContext());
            int[] ids = { userId };

            List<Settings> settings = db.settingsDao().loadAllByIds(ids);

            if(settings.size() <= 0 || settings.get(0) == null) {
                return null;
            }
            return settings.get(0);
        }

        @Override
        protected void onPostExecute(Settings settings) {
            this.fragment = weakFragment.get();
            if(settings == null || fragment == null) {
                return;
            }
            String time = settings.getDailyMatchReminderTime();
            String[] hourAndMins = time.split(":");
            fragment.tp.setHour(Integer.valueOf(hourAndMins[0]));
            fragment.tp.setMinute(Integer.valueOf(hourAndMins[1]));
            fragment.genderEditText.setText(settings.getGender());
            boolean isPublic = settings.isPublic();
            if (isPublic) {
                fragment.radioPublic.toggle();
            } else {
                fragment.radioPrivate.toggle();
            }
            fragment.maxDistanceEditText.setText(settings.getMaxDistance());
            fragment.minAgeEditText.setText(settings.getMinAge());
            fragment.maxAgeEditText.setText(settings.getMaxAge());
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated()");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG,"onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach()");
    }
}