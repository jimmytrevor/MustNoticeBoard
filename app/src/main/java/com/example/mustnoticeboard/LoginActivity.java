package com.example.mustnoticeboard;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private EditText user_email;
    private  EditText user_password;
        private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //hidong the action bar on the main activity
        getSupportActionBar().hide();

        user_email=findViewById(R.id.userEmail);
        user_password=findViewById(R.id.userPassword);
        progressDialog=new ProgressDialog(this);
    }
//The login method
    public void loginUserWithCredentials(View view) {

        setLogin(user_email.getText().toString(),user_password.getText().toString());


    }

    public void registerUserWithCredentails(View view) {
        final AlertDialog.Builder registerDialog=new AlertDialog.Builder(this);
        View registerView=getLayoutInflater().inflate(R.layout.register_dialog,null);
        registerDialog.setCancelable(false);
        EditText mail=(EditText)registerView.findViewById(R.id.registerEmail);
        EditText pass=(EditText)registerView.findViewById(R.id.registerPassword);
        EditText confirm=(EditText)registerView.findViewById(R.id.registerConfirm);
        CheckBox terms=(CheckBox) registerView.findViewById(R.id.registerTerms);

        registerDialog.setNegativeButton("CLOSE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        registerDialog.setView(registerView);
        AlertDialog alertDialog=registerDialog.create();
        alertDialog.show();

    }
    //definig the login Button
    public void setLogin(String email,String pass){
        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(pass)){
            user_email.setError("Email cannot be Empty");
            user_password.setError("Password cannot be Empty");
        }
        else if (TextUtils.isEmpty(email)){
            user_email.setError("Email cannot be Empty");
        }
        else if ( TextUtils.isEmpty(pass)){
            user_password.setError("Password cannot be Empty");
        }
        else {
            //definig the progress dialog
            progressDialog.setTitle("USER LOGIN");
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Checking For User Credential Validity.......");
            progressDialog.show();
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            LoginActivity.this.finish();
        }

    }

}
