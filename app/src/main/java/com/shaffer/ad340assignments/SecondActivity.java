package com.shaffer.ad340assignments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private static final String TAG = SecondActivity.class.getSimpleName();
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        textView = findViewById(R.id.textView);
        StringBuilder msg = new StringBuilder("YOUR PROFILE\n");
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        assert b != null;
        if (b.containsKey("name")) {
            String name = b.getString("name");
            msg.append("Name: ");
            msg.append(name);
            msg.append("\n");
        }
        if (b.containsKey("age")) {
            String age = b.getString("age");
            msg.append("Age: ");
            msg.append(age);
            msg.append("\n");
        }
        if (b.containsKey("occupation")) {
            String occupation = b.getString("occupation");
            msg.append("Occupation: ");
            msg.append(occupation);
            msg.append("\n");
        }
        if (b.containsKey("description")) {
            String description = b.getString("description");
            msg.append("Description: ");
            msg.append(description);
            msg.append("\n");
        }
        textView.setText(msg);
        Log.i(TAG, "onCreate()");
    }

    public void goToMain(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SecondActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
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
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
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
