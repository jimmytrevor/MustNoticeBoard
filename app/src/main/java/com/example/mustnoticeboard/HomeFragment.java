package com.example.mustnoticeboard;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private ListView myListView;
    private ArrayAdapter<String> myAdapter;
    private ArrayList<String> myArrayList;
    private RecyclerView recyclerView;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_home, container, false);
        myListView=v.findViewById(R.id.aboutlist);
        myArrayList=new ArrayList<String>();
        myArrayList.add("App Version");
        myArrayList.add("Developer Info");
        myArrayList.add("App Info");
        myArrayList.add("Supported Services");
        myAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,myArrayList);
        myListView.setAdapter(myAdapter);

        // Inflate the layout for this fragment
        return v;
    }

}
