package mobileapps.osubardeals.osubardealsapp.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import mobileapps.osubardeals.osubardealsapp.Adapters.BarsAdapter;
import mobileapps.osubardeals.osubardealsapp.R;
import mobileapps.osubardeals.osubardealsapp.Utilities.FragmentManagerSingleton;
import mobileapps.osubardeals.osubardealsapp.Utilities.JSONHelper;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BarsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BarsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BarsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button home, deals, bars, favorites;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BarsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BarsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BarsFragment newInstance(String param1, String param2) {
        BarsFragment fragment = new BarsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void getAllBars(Context c) {
        SharedPreferences s = c.getSharedPreferences("preferences",0);
        String barsJSONStr = s.getString("barsJSON","false");
        if (!barsJSONStr.equals("false")){
            mAdapter = new BarsAdapter(JSONHelper.getJSONArray(barsJSONStr));
            mRecyclerView.setAdapter(mAdapter);
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bars, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.barsRecyclerView);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        home = (Button) v.findViewById(R.id.home_button);
        deals = (Button) v.findViewById(R.id.deals_button);
        bars = (Button) v.findViewById(R.id.bars_button);
        favorites = (Button) v.findViewById(R.id.favorites_button);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManagerSingleton.instance().loadFragment(getFragmentManager(), new HomeFragment(), true);
            }
        });
        deals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManagerSingleton.instance().loadFragment(getFragmentManager(), new DealsFragment(), true);
            }
        });
        bars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManagerSingleton.instance().loadFragment(getFragmentManager(), new BarsFragment(), true);

            }
        });
        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManagerSingleton.instance().loadFragment(getFragmentManager(), new FavoritesFragment(), true);
            }
        });


        getAllBars(getContext());

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
