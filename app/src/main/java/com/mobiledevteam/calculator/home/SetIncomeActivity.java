package com.mobiledevteam.calculator.home;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.mobiledevteam.calculator.R;
import com.mobiledevteam.calculator.Utils.Common;
import com.mobiledevteam.calculator.cell.ExpandCategoryAdapter;
import com.mobiledevteam.calculator.cell.ExpandRepeatAdapter;
import com.mobiledevteam.calculator.login.LoginActivity;
import com.mobiledevteam.calculator.login.SignupActivity;
import com.mobiledevteam.calculator.profile.UserHomeActivity;

public class SetIncomeActivity extends AppCompatActivity {
    private ExpandableListView _expandCategory;
    private ExpandableListView _expandRepeat;
    private ExpandCategoryAdapter exCateAdapter;
    private ExpandRepeatAdapter exRepeatAdapter;
    private EditText _value;
    private EditText _note;
    private ImageView _imgCancel;
    private ImageView _imgAdd;

    private int categoryID=100;
    private int repeatID=100;
    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_income);
        _expandCategory = findViewById(R.id.exp_category);
        _expandRepeat = findViewById(R.id.exp_repeat);
        _value = (EditText)findViewById(R.id.input_value);
        _note = (EditText)findViewById(R.id.input_note);
        _imgCancel = (ImageView)findViewById(R.id.img_cancel);
        _imgAdd = (ImageView)findViewById(R.id.img_add);
        userid = Common.getInstance().getUserID();
        exCateAdapter = new ExpandCategoryAdapter(this, Common.getInstance().getmIncomeCategory());
        exRepeatAdapter = new ExpandRepeatAdapter(this, Common.getInstance().getmRepeatCategory());
        setReady();
    }
    private void setReady(){
        _expandCategory.setDivider(null);
        _expandCategory.setAdapter(exCateAdapter);
        _expandCategory.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                ViewGroup.LayoutParams params = _expandCategory.getLayoutParams();
                params.height = params.height * 6;
                _expandCategory.setLayoutParams(params);
            }
        });
        _expandCategory.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                ViewGroup.LayoutParams params = _expandCategory.getLayoutParams();
                params.height = params.height / 6;
                _expandCategory.setLayoutParams(params);
            }
        });
        _expandCategory.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                categoryID = childPosition;
                Log.d("CategoryID::", String.valueOf(categoryID));
                return true;
            }
        });

        _expandRepeat.setDivider(null);
        _expandRepeat.setAdapter(exRepeatAdapter);
        _expandRepeat.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                ViewGroup.LayoutParams params = _expandRepeat.getLayoutParams();
                params.height = params.height * 7;
                _expandRepeat.setLayoutParams(params);
            }
        });
        _expandRepeat.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                ViewGroup.LayoutParams params = _expandRepeat.getLayoutParams();
                params.height = params.height / 7;
                _expandRepeat.setLayoutParams(params);
            }
        });
        _expandRepeat.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                repeatID = childPosition;
                Log.d("repeatID::", String.valueOf(repeatID));
                return true;
            }
        });

        _imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        _imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddIncome();
            }
        });
    }

    private void onAddIncome() {
        if (!validate())
            return;
        String value = _value.getText().toString();
        String note = _note.getText().toString();

        final ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppTheme_Bright_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Add income...");
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setCancelable(false);
        progressDialog.show();

        // TODO: Implement your own signup logic here.
        JsonObject json = new JsonObject();
        json.addProperty("id",userid);
        json.addProperty("value",value);
        json.addProperty("categoryid",categoryID);
        json.addProperty("repeatid",repeatID);
        json.addProperty("note",note);

        try {
            Ion.with(this)
                    .load(Common.getInstance().getBaseURL()+"api/addincome")
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
                                    Toast.makeText(getBaseContext(),"Add income Success!", Toast.LENGTH_LONG).show();
                                }else {
                                    Toast.makeText(getBaseContext(),"Add Fail", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(getBaseContext(),"Add Fail", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }catch(Exception e){
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public boolean validate() {
        boolean valid = true;
        String value = _value.getText().toString();

        if (value.isEmpty()) {
            _value.setError("Input value");
            valid = false;
        } else {
            _value.setError(null);
        }
        if (categoryID == 100){
            Toast.makeText(getBaseContext(),"Select the category", Toast.LENGTH_LONG).show();
            valid = false;
        }
        if (repeatID == 100){
            Toast.makeText(getBaseContext(),"Select the repeat", Toast.LENGTH_LONG).show();
            valid = false;
        }

        return valid;
    }
}