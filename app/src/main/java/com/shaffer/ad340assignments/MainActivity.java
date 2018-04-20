package com.shaffer.ad340assignments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.app.DatePickerDialog;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;

/**
 * MainActivity class for Assignment 2
 * @author Melanie Shaffer
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView textView;
    private Button btnDatePicker;
    private EditText editTextUsername;
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextDOB;
    private TextView textViewAge;
    private LocalDate dateOfBirth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        editTextName = findViewById(R.id.nameEditText);
        editTextEmail = findViewById(R.id.emailEditText);
        editTextUsername = findViewById(R.id.usernameEditText);
        textViewAge = findViewById(R.id.ageTextView);
        editTextDOB = findViewById(R.id.in_date);
        btnDatePicker = findViewById(R.id.date_btn);
        findViewById(R.id.confirm_age);
        findViewById(R.id.submit_btn);
        Log.i(TAG, "onCreate()");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.bye_id) {
            textView.setText(R.string.arrivederci);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // validates data entered into forms and if valid, goes to SecondActivity
    public void onSubmit(View view) {
        if (validate()) {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            intent.putExtra("username", editTextUsername.getText().toString());
            startActivity(intent);
        }
    }

    private boolean validate() {
        boolean valid = true;
        String name = editTextName.getText().toString();
        String email = editTextEmail.getText().toString();
        String username = editTextUsername.getText().toString();
        String dob = editTextDOB.getText().toString();
        if (Util.isEmpty(name)) {
            editTextName.setError("Please enter a valid name");
            valid = false;
        }
        if (!Util.isValidEmail(email)) {
            editTextEmail.setError("Please enter a valid email address");
            valid = false;
        }
        if (!Util.isValidUsername(username)) {
            editTextUsername.setError("Please enter a username at least 5 characters long");
            valid = false;
        }
        if (!Util.isDateValid(dob)) {
            editTextDOB.setError("Please enter a valid date in the MM/DD/YYYY format");
            valid = false;
        }
        if (dateOfBirth == null) {
            try {
                DateTimeFormatter f = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                dateOfBirth = LocalDate.parse(editTextDOB.getText(), f);
            } catch(DateTimeParseException ex) {
                editTextDOB.setError("Please enter a valid date in the MM/DD/YYYY format");
                return false;
            }
        }
        if (!overEighteen(dateOfBirth)) {
            editTextDOB.setError(getString(R.string.age_restriction));
            valid = false;
            dateOfBirth = null;
        }
        Log.i(TAG, "validate()");
        return valid;
    }

    private boolean overEighteen(LocalDate dateOfBirth) {
        LocalDate today = LocalDate.now();
        Period period = Period.between(dateOfBirth, today);
        int calculatedAge = period.getYears();
        if (calculatedAge < 18) {
            return false;
        } else {
            return true;
        }
    }

    public void setAge(View view) {
        try {
            DateTimeFormatter f = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            dateOfBirth = LocalDate.parse(editTextDOB.getText(), f);
        } catch(DateTimeParseException ex) {
            editTextDOB.setError("Please enter a valid date in the MM/DD/YYYY format");
        }
        if (dateOfBirth != null) {
            LocalDate today = LocalDate.now();
            Period period = Period.between(dateOfBirth, today);
            int calculatedAge = period.getYears();
            String age = String.valueOf(calculatedAge);
            StringBuilder sb = new StringBuilder("Your age is ");
            sb.append(age);
            if (overEighteen(dateOfBirth)) {
                sb.append(". Please click below to register.");
            } else {
                sb.append(". You must be at least 18 to register.");
            }
            String yourAge = sb.toString();
            textViewAge.setText(yourAge);
        }
    }


    // If DatePicker is used to set date
    public void setTime(View view) {
        if (view == btnDatePicker) {
            // get current date
            final Calendar cal = Calendar.getInstance();
            int currYear = cal.get(Calendar.YEAR);
            int currMonth = cal.get(Calendar.MONTH);
            int currDay = cal.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DatePicker,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int day) {
                            String sMonth = String.valueOf(month + 1);
                            if (sMonth.length() == 1) {
                                sMonth = "0" + sMonth;
                            }
                            String sDay = String.valueOf(day);
                            if (sDay.length() == 1) {
                                sDay = "0" + sDay;
                            }
                            String sYear = String.valueOf(year);
                            editTextDOB.setText(sMonth + "/" + sDay + "/" + sYear);
                            dateOfBirth = LocalDate.of(year, month+1, day);
                        }
                    }, currYear, currMonth, currDay);
            datePickerDialog.show();
        }
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
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey("textView")) {
            textView.setText((String)savedInstanceState.get("textView"));
        }
        if (savedInstanceState.containsKey("age")) {
            textViewAge.setText((String)savedInstanceState.get("age"));
        }
        Log.i(TAG, "onRestoreInstanceState()");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("textView", textView.getText().toString());
        outState.putString("age", textViewAge.getText().toString());
        Log.i(TAG, "onSaveInstanceState()");
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
