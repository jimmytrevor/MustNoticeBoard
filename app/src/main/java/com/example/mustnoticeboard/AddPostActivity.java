package com.example.mustnoticeboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class AddPostActivity extends AppCompatActivity {
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private ProgressDialog showMeProgess;
private ImageView mImage;
private static final int REQUEST_CODE_IMAGE=1;
private Uri imageUri;
private EditText title,desc;
public String downloadUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        title=findViewById(R.id.title);
        desc=findViewById(R.id.decription);
        showMeProgess=new ProgressDialog(this);

        mImage=findViewById(R.id.imageSelect);
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });


//        Instanctaite firebase references
        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Posts");

    }
//the method that picks an image from the file chooser
    public void pickImage(){
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,REQUEST_CODE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode==REQUEST_CODE_IMAGE &&resultCode==RESULT_OK && data !=null && data.getData() != null){
            imageUri=data.getData();
            mImage.setImageURI(imageUri);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void addNewPost(View view) {
        String t=title.getText().toString().trim();
        String d=desc.getText().toString().trim();
        if (TextUtils.isEmpty(t) || TextUtils.isEmpty(d) || imageUri == null){
            Toast.makeText(this, "Cannot add posts please\n Reason :Some fields are empty", Toast.LENGTH_SHORT).show();
        }
        else {

            showMeProgess.setMessage("DATA SAVING");
            showMeProgess.setCancelable(false);
            showMeProgess.show();
            final StorageReference filepath=storageReference.child("post_image").child(imageUri.getLastPathSegment());
final UploadTask uploadTask=filepath.putFile(imageUri);

            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    showMeProgess.dismiss();
                    final DatabaseReference db=databaseReference.push();

                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            HashMap<String,String> hashMap=new HashMap<>();

                            db.child("title").setValue(title.getText().toString().trim());
                                db.child("description").setValue(desc.getText().toString().trim());
                                db.child("image").setValue(String.valueOf(uri));
                                Toast.makeText(getApplicationContext(),"Post Added successfully to the database",Toast.LENGTH_LONG).show();
                                title.setText("");
                                desc.setText("");

                        }
                    });
//


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    showMeProgess.dismiss();
                    Toast.makeText(AddPostActivity.this, "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    Double progress=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    showMeProgess.setMessage("Data submission is at "+String.valueOf(progress)+"%");
                }
            });


        }
    }
}
