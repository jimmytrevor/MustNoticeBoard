package com.example.mustnoticeboard;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText user_email,mail,confirm,pass;
    private CheckBox terms;
    private  EditText user_password;
        private ProgressDialog progressDialog,registerProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //hidong the action bar on the main activity
        getSupportActionBar().hide();
mAuth=FirebaseAuth.getInstance();

        user_email=findViewById(R.id.userEmail);
        user_password=findViewById(R.id.userPassword);
        progressDialog=new ProgressDialog(this);
        registerProgress=new ProgressDialog(this);
    }
//The login method
    public void loginUserWithCredentials(View view) {

        setLogin(user_email.getText().toString(),user_password.getText().toString());


    }

    public void registerUserWithCredentails(View view) {
        final AlertDialog.Builder registerDialog=new AlertDialog.Builder(this);
        View registerView=getLayoutInflater().inflate(R.layout.register_dialog,null);
        registerDialog.setCancelable(false);
         mail=(EditText)registerView.findViewById(R.id.registerEmail);
         pass=(EditText)registerView.findViewById(R.id.registerPassword);
         confirm=(EditText)registerView.findViewById(R.id.registerConfirm);
         terms=(CheckBox) registerView.findViewById(R.id.registerTerms);



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
            mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        progressDialog.dismiss();
                        startActivity(new Intent(getApplicationContext(),NoticeBoardActivity.class));
                        LoginActivity.this.finish();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(LoginActivity.this, "Error "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.cancel();
                }
            });
        }
    }

    public void registerUser(View view) {
        String m = mail.getText().toString().trim();
        String p = pass.getText().toString().trim();
        String c = confirm.getText().toString();
        if (TextUtils.isEmpty(m) || TextUtils.isEmpty(p) || TextUtils.isEmpty(c)){
            Toast.makeText(this, "Check out some fields are empty", Toast.LENGTH_SHORT).show();
        }
        else if (p.length()<8 || p.length()>10){
            pass.setError("Password must contain 8 to 10 character");
            pass.requestFocus();
        }
        else if (!p.equals(c)){
            confirm.setError("Password confirmation Failed");
            Toast.makeText(this,"Different Password entered",Toast.LENGTH_SHORT).show();
        }
        else if (terms.isSelected()==false){
            Toast.makeText(this, "You must accept terms and condition so continue", Toast.LENGTH_SHORT).show();
            terms.setSelected(true);
        }
           else {
               registerProgress.setCancelable(false);
               registerProgress.setTitle("SIGNING UP");
               registerProgress.setMessage("Adding User to the database.......");
               registerProgress.show();
               mAuth.createUserWithEmailAndPassword(m,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()){
                           registerProgress.dismiss();
                           Toast.makeText(LoginActivity.this, "User Information added successfully", Toast.LENGTH_SHORT).show();
                           mail.setText("");
                           pass.setText("");
                           confirm.setText("");
                           terms.setSelected(false);
                       }
                   }
               }).addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       Toast.makeText(LoginActivity.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                       registerProgress.cancel();
                   }
               });
        }

    }}