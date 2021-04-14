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

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private Button _btnSign;
    private TextView _txtsignup;
    private EditText _email;
    private EditText _password;

    private ArrayList<Category> mIncomeCategory = new ArrayList<>();
    private ArrayList<Category> mLiabilityCategory = new ArrayList<>();
    private ArrayList<String> mRepeatCategort = new ArrayList<>();

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
        mIncomeCategory.add(new Category("0","Salary",R.drawable.salary));
        mIncomeCategory.add(new Category("1","Business Profit",R.drawable.business));
        mIncomeCategory.add(new Category("2","Project",R.drawable.project));
        mIncomeCategory.add(new Category("3","Royalty",R.drawable.royalty));
        mIncomeCategory.add(new Category("4","Other",R.drawable.other));

//        mLiabilityCategory.add(new Category("0","Condo Fee","condo"));
//        mLiabilityCategory.add(new Category("1","Property Taxes","property"));
//        mLiabilityCategory.add(new Category("2","Mortgage Payment","mortgage"));
//        mLiabilityCategory.add(new Category("3","Cell Phone","phone"));
//        mLiabilityCategory.add(new Category("4","Car Gas","gas"));
//        mLiabilityCategory.add(new Category("5","Car Insurance","insurance"));
//        mLiabilityCategory.add(new Category("6","Electric Bill","electric"));
//        mLiabilityCategory.add(new Category("7","Groceries","groceries"));
//        mLiabilityCategory.add(new Category("8","Gas/Heating","heating"));
//        mLiabilityCategory.add(new Category("9","Tv/Internet","tv"));
//        mLiabilityCategory.add(new Category("10","Water","water"));
//        mLiabilityCategory.add(new Category("11","Secured Line of Credit","secured"));

        mRepeatCategort.add("Once");
        mRepeatCategort.add("Every Day");
        mRepeatCategort.add("Every Week");
        mRepeatCategort.add("Every Month");
        mRepeatCategort.add("Semi Anually");
        mRepeatCategort.add("Anually");

        Common.getInstance().setmIncomeCategory(mIncomeCategory);
        Common.getInstance().setmLiabilityCategory(mLiabilityCategory);
        Common.getInstance().setmRepeatCategory(mRepeatCategort);
        
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

//                                    Common.getInstance().setLogin_status("yes");
//                                    Common.getInstance().setClinictype(type);
//                                    loginStatus = "yes" + " " + clinic_id + " " + type;
//                                    writeFile();
                                    Intent intent = new Intent(LoginActivity.this, DiscloserActivity.class);
                                    startActivity(intent);
                                    finish();
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