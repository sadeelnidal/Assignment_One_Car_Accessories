package com.example.caraccessories;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUp extends AppCompatActivity {

    EditText FirstName;
    EditText LastName;
    EditText Mail;
    RadioGroup Gender;
    RadioButton Male;
    RadioButton Female;
    EditText Password;
    Button signUp;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initializeViews();

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

        signUp.setOnClickListener(v -> {
            String firstName = FirstName.getText().toString().trim();
            String lastName = LastName.getText().toString().trim();
            String mail = Mail.getText().toString().trim().toLowerCase();
            String password = Password.getText().toString().trim();
            int selectedGenderId = Gender.getCheckedRadioButtonId();

            if (firstName.isEmpty() || lastName.isEmpty() || mail.isEmpty() || password.isEmpty() || selectedGenderId == -1) {
                Toast.makeText(this, "THERE IS EMPTY FIELD", Toast.LENGTH_SHORT).show();
                return;
            }

            if (prefs.contains(mail)) {
                Toast.makeText(this, "Email already Used!", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton selectedGender = findViewById(selectedGenderId);
            String gender = selectedGender.getText().toString();

            // Create user data as JSON
            JSONObject obj = new JSONObject();
            try {
                obj.put("first", firstName);
                obj.put("last", lastName);
                obj.put("gender", gender);
                obj.put("pass", password);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            editor.putString(mail, obj.toString());
            editor.apply();

            Toast.makeText(this, "Sign Up Successful!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SignUp.this, Shoppingbage.class));
            finish();
        });

    }

    private void initializeViews() {
        FirstName = findViewById(R.id.Name);
        LastName = findViewById(R.id.Last);
        Mail = findViewById(R.id.xx);
        Gender = findViewById(R.id.Gender);
        Male = findViewById(R.id.Male);
        Female = findViewById(R.id.female);
        Password = findViewById(R.id.pass);
        signUp = findViewById(R.id.Sign);
    }
}
