package com.example.caraccessories;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    EditText Username, Password;
    Button Login;
    CheckBox checkBox;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeViews();

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

        boolean rememberMe = prefs.getBoolean("rememberme", false);
        String saveEmail = prefs.getString("rememberEmail", null);
        String savePass = prefs.getString("rememberPassword", null);

        if (rememberMe && saveEmail != null && savePass != null) {
            Username.setText(saveEmail);
            Password.setText(savePass);
            checkBox.setChecked(true);
        }

        Login.setOnClickListener(v ->{
            String email = Username.getText().toString().trim().toLowerCase();
            String password = Password.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(this,"Please fill all fields ", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!prefs.contains(email)) {
                    Toast.makeText(this,"Email not found", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    // i use JSONObject this than for loop
                    JSONObject userData = new JSONObject(prefs.getString(email, ""));
                    if (correctPassword(userData, password)) {
                        editor.remove("rememberme");
                        editor.remove("rememberEmail");
                        editor.remove("rememberPassword");

                        if (checkBox.isChecked()) {
                            editor.putBoolean("rememberme", true);
                            editor.putString("rememberEmail", email);
                            editor.putString("rememberPassword", Password.getText().toString().trim());
                        }

                        editor.apply();

                        Toast.makeText(this,"Welcome to our APP", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, Shoppingbage.class)); // Fixed class name
                        finish();
                    }
                } catch (JSONException e) {
                    Toast.makeText(this,"Error reading your data", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            });
    }

    private void initializeViews() {
        Username = findViewById(R.id.Name);
        Password = findViewById(R.id.pass);
        checkBox = findViewById(R.id.chk);
        Login = findViewById(R.id.Sign);
    }
    private boolean correctPassword(JSONObject data, String pass) throws JSONException {
        String realPass = data.getString("pass");
        if (!pass.equals(realPass)) {
            Toast.makeText(this,"Wrong password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}