package com.example.mustnoticeboard;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AddPostActivity extends AppCompatActivity {
private ImageView mImage;
private static final int REQUEST_CODE_IMAGE=1;
private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        mImage=findViewById(R.id.imageSelect);
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });
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
}
