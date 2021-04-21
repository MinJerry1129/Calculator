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
import com.mobiledevteam.calculator.MainActivity;
import com.mobiledevteam.calculator.R;
import com.mobiledevteam.calculator.Utils.Common;
import com.mobiledevteam.calculator.cell.Category;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private Button _btnSign;
    private TextView _txtsignup;
    private EditText _email;
    private EditText _password;
    private String loginStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        _btnSign = (Button)findViewById(R.id.btn_signin);
        _txtsignup = (TextView) findViewById(R.id.txt_signup);
        _email = (EditText) findViewById(R.id.input_email);
        _password = (EditText) findViewById(R.id.input_password);
        setReady();
    }
    private void setReady(){

        
        _btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignIn();
            }
        });
        _txtsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void onSignIn() {
        if(!validate()){
            return;
        }
        String username = _email.getText().toString();
        String password = _password.getText().toString();

        final ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppTheme_Bright_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setCancelable(false);
        progressDialog.show();
        JsonObject json = new JsonObject();
        json.addProperty("username", _email.getText().toString());
        json.addProperty("password", _password.getText().toString());

        try {
            Ion.with(this)
                    .load(Common.getInstance().getBaseURL()+"api/login")
                    .setJsonObjectBody(json)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            progressDialog.dismiss();
                            Log.d("result::", result.toString());
                            if (result != null) {
                                String id = result.get("userid").toString();
                                Log.d("clinic_info::", id);
                                if(id.equals("\"nouser\"")){
                                    Toast.makeText(getBaseContext(),"You are not registered, Please signup",Toast.LENGTH_LONG).show();
                                }else if (id.equals("\"wrongpassword\"")){
                                    Toast.makeText(getBaseContext(),"Please input correct password",Toast.LENGTH_LONG).show();
                                }else{
                                    JsonObject clinic_object = result.getAsJsonObject("userid");
                                    String userid = clinic_object.get("id").getAsString();

                                    Common.getInstance().setUserID(userid);
                                    Common.getInstance().setLogin_status("yes");

                                    loginStatus = "yes" + " " + userid;
                                    writeFile();

                                }
                            } else {
                                Toast.makeText(getBaseContext(),"Sigin Fail!",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }catch(Exception e){
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    private  void writeFile(){
        try {
            FileOutputStream fileOutputStream = openFileOutput("loginstatus.pdm", MODE_PRIVATE);
            fileOutputStream.write(loginStatus.getBytes());
            fileOutputStream.close();
            Intent intent = new Intent(LoginActivity.this, DiscloserActivity.class);
            startActivity(intent);
            finish();
        }catch (FileNotFoundException e){
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean validate() {
        boolean valid = true;
        String email = _email.getText().toString();
        String password = _password.getText().toString();
        if (email.isEmpty()) {
            _email.setError("Input Username");
            valid = false;
        } else {
            _email.setError(null);
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
}