package com.mobiledevteam.calculator.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.mobiledevteam.calculator.R;
import com.mobiledevteam.calculator.Utils.Common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {
    private Button _btnSignup;
    private TextView _txtsignin;
    private EditText _email;
    private EditText _password;
    private EditText _username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        _btnSignup = (Button)findViewById(R.id.btn_signup);
        _txtsignin = (TextView) findViewById(R.id.txt_signin);
        _email = (EditText) findViewById(R.id.input_email);
        _username = (EditText) findViewById(R.id.input_username);
        _password = (EditText) findViewById(R.id.input_password);
        setReady();
    }
    private void setReady(){
        _btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignup();
            }
        });
        _txtsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void onSignup() {
        if(!validate()){
            return;
        }
        String email = _email.getText().toString();
        String password = _password.getText().toString();
        String username = _username.getText().toString();

        final ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppTheme_Bright_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setCancelable(false);
        progressDialog.show();

        // TODO: Implement your own signup logic here.
        JsonObject json = new JsonObject();
        json.addProperty("email",email);
        json.addProperty("username",username);
        json.addProperty("password",password);

        try {
            Ion.with(this)
                    .load(Common.getInstance().getBaseURL()+"api/signup")
                    .setJsonObjectBody(json)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            progressDialog.dismiss();
                            Log.d("result::", result.toString());
                            if (result != null) {
                                String status = result.get("status").getAsString();
                                if (status.equals("ok")) {
                                    Toast.makeText(getBaseContext(),"Signup Success, please login!", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else if (status.equals("existemail")) {
                                    Toast.makeText(getBaseContext(),"Your email already exist, Please check again", Toast.LENGTH_LONG).show();
                                }else if (status.equals("existuser")) {
                                    Toast.makeText(getBaseContext(),"Username already exist, Please check again", Toast.LENGTH_LONG).show();
                                } else if (status.equals("fail")) {
                                    Toast.makeText(getBaseContext(),"Fail signup", Toast.LENGTH_LONG).show();
                                }
                            } else {
                            }
                        }
                    });
        }catch(Exception e){
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    public boolean validate() {
        boolean valid = true;
        String email = _email.getText().toString();
        String password = _password.getText().toString();
        String username = _username.getText().toString();
        if (username.isEmpty()) {
            _username.setError("Input Firstname");
            valid = false;
        } else {
            _username.setError(null);
        }
        if (email.isEmpty()) {
            _email.setError("Input Email");
            valid = false;
        } else {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,10}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()){
                _email.setError(null);
            }else{
                _email.setError("Input Email");
                valid = false;
            }
        }

        if (password.isEmpty()) {
            _password.setError("Input Password");
            valid = false;
        } else {
            if(password.length() < 6 ){
                _password.setError("Input password more than 6 charactors");
                valid = false;
            }else{
                _password.setError(null);
            }

        }
        return valid;
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}