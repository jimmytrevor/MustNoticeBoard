package com.example.mustnoticeboard;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {
    MyInternetConnection myInternetConnection;
    BatteryTest batteryTest;
    TestDatabaseHelper testDatabaseHelper;
    private FirebaseAuth mAuth;
    private EditText user_email,mail,confirm,pass,user,course,year;
    private CheckBox terms;
    private  EditText user_password;
        private ProgressDialog progressDialog,registerProgress;
        private DatabaseReference mref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //hidong the action bar on the main activity
        getSupportActionBar().hide();
        testDatabaseHelper=new TestDatabaseHelper(this);
        batteryTest= new BatteryTest();
        myInternetConnection=new MyInternetConnection();
mAuth=FirebaseAuth.getInstance();
mref = FirebaseDatabase.getInstance().getReference().child("users");

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
        year=(EditText)registerView.findViewById(R.id.year);
        user=(EditText)registerView.findViewById(R.id.fullname);
        course=(EditText)registerView.findViewById(R.id.course);
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
            ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();

            if(networkInfo !=null && networkInfo.isConnected()){


            //definig the progress dialog
            progressDialog.setTitle("USER LOGIN");
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Checking For User Credential Validity.......");
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        checkUserData();
                        progressDialog.dismiss();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"You need to create an account",Toast.LENGTH_LONG).show();
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

        else {
            boolean data=testDatabaseHelper.getData(user_email.getText().toString(),user_password.getText().toString());
            if (data==true){
                startActivity(new Intent(getApplicationContext(),NoticeBoardActivity.class));
                LoginActivity.this.finish();
            }
            else{
                Toast.makeText(getApplicationContext(),"Sorry You need to create account",Toast.LENGTH_LONG).show();
            }

        }}
    }

    public void checkUserData(){
        final String user_id=mAuth.getCurrentUser().getUid();
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(user_id)){
                    startActivity(new Intent(getApplicationContext(),NoticeBoardActivity.class));
                    LoginActivity.this.finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    public void registerUser(View view) {
        String m = mail.getText().toString().trim();
        String p = pass.getText().toString().trim();
        String c = confirm.getText().toString();
        final  String n=user.getText().toString().trim();
        final String co=course.getText().toString().trim();
        final   String y=year.getText().toString().trim();
        final String f=co+" "+y;

        if (TextUtils.isEmpty(m) || TextUtils.isEmpty(p) || TextUtils.isEmpty(c) || TextUtils.isEmpty(n) || TextUtils.isEmpty(co)|| TextUtils.isEmpty(y) ){
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
            boolean getEmail = testDatabaseHelper.checkEmailExistance(mail.getText().toString());
            if (getEmail == true) {
                Toast.makeText(this, "It seems someone else if not you used this email to create account\nUse it to log into the application", Toast.LENGTH_SHORT).show();
            } else {
                saveUserEmailAndPassword();
                registerProgress.setCancelable(false);
                registerProgress.setTitle("SIGNING UP");
                registerProgress.setMessage("Adding User to the database.......");
                registerProgress.show();
                mAuth.createUserWithEmailAndPassword(m, p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String user_id = mAuth.getCurrentUser().getUid();
                            DatabaseReference current_user = mref.child(user_id);
                            current_user.child("Full_Name").setValue(n);
                            current_user.child("Program").setValue(f);
                            registerProgress.dismiss();
                            Toast.makeText(LoginActivity.this, "User Information added successfully", Toast.LENGTH_SHORT).show();
                            mail.setText("");
                            pass.setText("");
                            confirm.setText("");
                            user.setText("");
                            course.setText("");
                            year.setText("");
                            terms.setSelected(false);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        registerProgress.cancel();
                    }
                });
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(myInternetConnection,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        registerReceiver(batteryTest,new IntentFilter(BatteryManager.ACTION_CHARGING));

    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(myInternetConnection);
        unregisterReceiver(batteryTest);
    }
    public void saveUserEmailAndPassword(){



        boolean send=testDatabaseHelper.sendData(user.getText().toString(),mail.getText().toString(),pass.getText().toString());
        if (send==true){
            Toast.makeText(getApplicationContext(),"Login Credentials added successfully",Toast.LENGTH_LONG).show();


        }
        else{
            Toast.makeText(getApplicationContext(),"Registration failed please try again",Toast.LENGTH_LONG).show();
        }
    }

}