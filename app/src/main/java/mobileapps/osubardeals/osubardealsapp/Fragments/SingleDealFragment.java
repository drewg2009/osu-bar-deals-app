package mobileapps.osubardeals.osubardealsapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.MapView;

import java.io.UnsupportedEncodingException;

import mobileapps.osubardeals.osubardealsapp.R;

/**
 * Created by Erin George on 3/20/2018.
 */

public class SingleDealFragment extends Fragment {

    private TextView price, hour, location;
    private MapView map;
    private CheckBox favorite;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SingleDealFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SingleDealFragment newInstance(String param1, String param2) {
        SingleDealFragment fragment = new SingleDealFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }


    public void initFavorite(Context c, final String email,  String dealName) {

// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(c);
        String q="email="+email + "&type=deal&deal_name="+dealName;
        String url = "https://osu-bar-deals-api.herokuapp.com/favorites/deal/get?" + q;
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Get favorite: ", response);

                        favorite.setChecked(!response.equals("not found"));

                        //remove spinner
                        //spinner.setVisibility(View.GONE);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Get favorite: error", error.toString());
                //mTextView.setText("That didn't work!");
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


    public void setFavorite(Context c, String email, String dealName, String location,boolean add) throws UnsupportedEncodingException {

// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(c);
        //String url ="http://www.google.com";
        String ext = add ? "add" : "delete";
        String q="email="+email + "&deal_name=" + dealName + "&type=deal&bar_name="+location;
        String url = "https://osu-bar-deals-api.herokuapp.com/favorites/" + ext + "?" + q;
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Make favorite: ", response);

                        //remove spinner
                        //spinner.setVisibility(View.GONE);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Make favorite: error", error.toString());
                //mTextView.setText("That didn't work!");
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_deal_page, container, false);
        price = (TextView)v.findViewById(R.id.dealPrice);
        hour = (TextView)v.findViewById(R.id.dealHours);
        location = (TextView)v.findViewById(R.id.dealLocation);
        map = (MapView) v.findViewById(R.id.dealDirections);
        favorite = (CheckBox) v.findViewById(R.id.dealFavorite);


        //populate everything
        if(getArguments() != null){
            price.setText(getArguments().getString("description"));
            hour.setText(getArguments().getString("hours"));
            location.setText(getArguments().getString("location"));
        }

        final String email = getContext().getSharedPreferences("preferences", 0).getString("email","false");


        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get user session email
                if(!email.equals("false")) {
                    try {
                        setFavorite(getContext(), email, price.getText().toString(), location.getText().toString(), favorite.isChecked());
                    }
                    catch (UnsupportedEncodingException ex){
                        ex.printStackTrace();
                    }
                }
            }
        });
        initFavorite(getContext(), email, price.getText().toString());

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
        if (context instanceof LoginFragment.OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
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
