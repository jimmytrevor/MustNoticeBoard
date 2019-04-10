package com.example.mustnoticeboard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class NoticeBoardActivity extends AppCompatActivity {
private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_board);

recyclerView=findViewById(R.id.new_list);
recyclerView.setHasFixedSize(true);
recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addpost_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.addPost:
                startActivity(new Intent(getApplicationContext(),AddPostActivity.class));
                return true;
            case R.id.set:
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                return true;
            default:

                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {

        super.onStart();
    }

    public static final class NewViewHolder extends RecyclerView.ViewHolder{

        View mView;
       public NewViewHolder(View itemview){
            super(itemview);
           mView=itemView;

        }

        public void setTitle(String title){
            TextView titleView=(TextView)mView.findViewById(R.id.postTitle);
            titleView.setText(title);
        }
        public void setDesc(String desc){
            TextView descView=(TextView)mView.findViewById(R.id.postTitle);
            descView.setText(desc);
        }
    }



}
