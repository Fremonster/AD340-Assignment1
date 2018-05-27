package com.shaffer.ad340assignments;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shaffer.ad340assignments.models.MatchItem;
import com.shaffer.ad340assignments.viewmodels.MatchesViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Matches
 */
public class MatchesContentFragment extends Fragment {

    public static final String ARG_COLUMN_COUNT = "column-count";
    public static final String ARG_DATA_SET = "matches";

    int mColumnCount = 8;
    List<MatchItem> mDataSet;
    private OnListFragmentInteractionListener mListener;
    private RecyclerView view;
    private Parcelable recylerViewState;
    GetGPSLocation gps;
    double latitude;
    double longitude;
    Context context;

    private static String TAG = MatchesContentFragment.class.getSimpleName();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MatchesContentFragment() {
    }

    @SuppressWarnings("unused")
    public static MatchesContentFragment newInstance(int columnCount) {
        MatchesContentFragment fragment = new MatchesContentFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
        Log.i(TAG, "onAttach()");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            mDataSet = getArguments().getParcelableArrayList(ARG_DATA_SET);
        }
        gps = new GetGPSLocation(context);
        if(gps.canGetLocation()){
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
        }else{
            gps.showSettingsAlert();
        }
        Log.i(TAG, "onCreate()");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);
        // Instantiate view model
        MatchesViewModel viewModel = new MatchesViewModel();
        // Set the adapter
        viewModel.getMatchItems(
                (ArrayList<MatchItem> matches) -> {
                    MatchItemRecyclerViewAdapter adapter = new MatchItemRecyclerViewAdapter(matches, mListener);
                    ArrayList<MatchItem> itemsToRemove = new ArrayList<>();
                    for (MatchItem item : matches) {
                        double itemLongitude = Double.valueOf(item.longitude);
                        double itemLatitude = Double.valueOf(item.lat);
                        double milesAway = distanceFrom(itemLatitude, itemLongitude);
                        if (milesAway > 16.0934) { //in km, equal to 10 miles
                            itemsToRemove.add(item);
                        }
                    }
                    matches.removeAll(itemsToRemove);
                    view.setAdapter(adapter);
                    view.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    if (recylerViewState != null) {
                        view.getLayoutManager().onRestoreInstanceState(recylerViewState);
                    }
                    view.setLayoutManager(layoutManager);
                }
        );
        return view;
    }

    private double distanceFrom(double latitude, double longitude) {
        double R = 6371; // km - radius of the Earth
        double dLat = Math.toRadians(this.latitude-latitude);
        double dLon = Math.toRadians(this.longitude-longitude);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(this.latitude)) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        gps.stopUsingGPS(); // removes updates
        Log.i(TAG, "onDetach()");
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(MatchItem item);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated()");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG,"onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }

}