package com.example.mustnoticeboard;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NoticeBoardActivity extends AppCompatActivity {
//private RecyclerView recyclerView;
MyInternetConnection myInternetConnection;
private DatabaseReference md;
FirebaseRecyclerOptions<PostUpload> firebaseRecyclerOptions;
FirebaseRecyclerAdapter<PostUpload ,NewViewHolder> firebaseRecyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_board);
myInternetConnection=new MyInternetConnection();
//recyclerView=findViewById(R.id.new_list);
//recyclerView.setHasFixedSize(true);
//recyclerView.setLayoutManager(new LinearLayoutManager(this));

md= FirebaseDatabase.getInstance().getReference("posts");


        firebaseRecyclerOptions=new FirebaseRecyclerOptions.Builder<PostUpload>()
                .setQuery(md,PostUpload.class).build();

        firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<PostUpload, NewViewHolder>(firebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull NewViewHolder holder, int position, @NonNull PostUpload model) {
holder.setDesc(model.getDescription());
holder.setTitle(model.getTitle());
            }

            @NonNull
            @Override
            public NewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.new_layout,viewGroup,false);
                return new NewViewHolder(v);
            }

        };
        firebaseRecyclerAdapter.startListening();
//        recyclerView.setAdapter(firebaseRecyclerAdapter);

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
        if (firebaseRecyclerAdapter!=null){
            firebaseRecyclerAdapter.stopListening();

        }
      registerReceiver(myInternetConnection,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onResume() {
        if (firebaseRecyclerAdapter!=null){
            firebaseRecyclerAdapter.startListening();

        }
        super.onResume();
    }

    public void addNewPost(View view) {

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


    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(myInternetConnection);
    }
}
