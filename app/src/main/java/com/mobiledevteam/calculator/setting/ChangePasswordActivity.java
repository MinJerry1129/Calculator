package com.mobiledevteam.calculator.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.mobiledevteam.calculator.R;
import com.mobiledevteam.calculator.Utils.Common;

public class ChangePasswordActivity extends AppCompatActivity {
    private EditText _txtNewPassword;
    private EditText _txtOldPassword;
    private ImageView _imgCancel;
    private ImageView _imgUpdate;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        userId = Common.getInstance().getUserID();
        _txtNewPassword = (EditText)findViewById(R.id.input_npassword);
        _txtOldPassword = (EditText)findViewById(R.id.input_opassword);
        _imgCancel = (ImageView)findViewById(R.id.img_cancel);
        _imgUpdate = (ImageView)findViewById(R.id.img_update);
        setReady();
    }

    private void setReady() {
        _imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(), SettingHomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        _imgUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePassword();
            }
        });
    }

    private void updatePassword() {
        if(!validate()){
            return;
        }
        String opassword = _txtOldPassword.getText().toString();
        String npassword = _txtNewPassword.getText().toString();
        final ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppTheme_Bright_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setCancelable(false);
        progressDialog.show();
        JsonObject json = new JsonObject();
        json.addProperty("id", userId);
        json.addProperty("oldpass", opassword);
        json.addProperty("newpass", npassword);

        try {
            Ion.with(this)
                    .load(Common.getInstance().getBaseURL()+"api/updatePassword")
                    .setJsonObjectBody(json)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            progressDialog.dismiss();
                            Log.d("result::", result.toString());
                            if (result != null) {
                                String result_update = result.get("status").getAsString();
                                if (result_update.equals("wrongpassword")){
                                    _txtOldPassword.setError("Check Original password");
                                }else if (result_update.equals("ok")){
                                    Toast.makeText(getBaseContext(), "Update Success.", Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(getBaseContext(), "Update Fail.", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(getBaseContext(),"Update password Fail!",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }catch(Exception e){
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public boolean validate() {
        boolean valid = true;
        String opassword = _txtOldPassword.getText().toString();
        String npassword = _txtNewPassword.getText().toString();

        if (opassword.isEmpty()) {
            _txtOldPassword.setError("Input Password");
            valid = false;
        } else {
            if(opassword.length() < 6 ){
                _txtOldPassword.setError("Input password more than 6 charactors");
                valid = false;
            }else{
                _txtOldPassword.setError(null);
            }
        }

        if (npassword.isEmpty()) {
            _txtNewPassword.setError("Input Password");
            valid = false;
        } else {
            if(npassword.length() < 6 ){
                _txtNewPassword.setError("Input password more than 6 charactors");
                valid = false;
            }else{
                _txtNewPassword.setError(null);
            }
        }

        return valid;
    }
}