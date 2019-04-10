package com.example.mustnoticeboard;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {


    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_about, container, false);
        ListView myListView=v.findViewById(R.id.aboutlist);
        final ArrayList<String> myArrayList=new ArrayList<String>();
        myArrayList.add("App Version");
        myArrayList.add("Supported Version");
        myArrayList.add("Developer Info");
        myArrayList.add("App Info");
        myArrayList.add("Supported Services");
        ArrayAdapter<String> myAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,myArrayList);
        myListView.setAdapter(myAdapter);
        //Adding Action Liste to the List
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        AlertDialog.Builder version= new AlertDialog.Builder(getContext())
                                .setCancelable(false)
                                .setTitle("CURRENT APP VERSION")
                                .setMessage("\t\nCurrently Version \n mustBoard 1.0")
                                .setNegativeButton("CLOSE", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alertDialog=version.create();
                        alertDialog.show();

                        break;
                    case 1:
                        break;
                    default:
                        return;

                }
            }
        });

        // Inflate the layout for this fragment
        return v;
    }

}
