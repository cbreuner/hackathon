package edu.csumb.partyon.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.csumb.partyon.R;
import edu.csumb.partyon.activities.MainActivity;

/**
 * Created by Tobias on 20.11.2015.
 */
public class DashboardFragment extends Fragment {

    public static final String TAG = "DashboardFragment";

    private Toolbar toolbar;
    private CardView newPartyCv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        newPartyCv = (CardView) view.findViewById(R.id.dashboard_newparty);
        newPartyCv.setOnClickListener(newPartyListener);
        initToolbar();
        return view;
    }

    private void initToolbar() {
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.dashboard_title);
        }
    }

    private View.OnClickListener newPartyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((MainActivity)getActivity()).newPartyFragment();
        }
    };
}
