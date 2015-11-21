package edu.csumb.partyon.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.csumb.partyon.R;

/**
 * Created by Tobias on 21.11.2015.
 */
public class PartyFragment extends Fragment {

    public static final String TAG = "PartyFragment";

    private Toolbar toolbar;

    public PartyFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newparty, container, false);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        initToolbar();
        return view;
    }

    private void initToolbar() {
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
            //actionBar.setTitle(R.string.app_title);
            actionBar.setTitle(R.string.newparty_title);
        }
    }

}
