package mobileapps.osubardeals.osubardealsapp.Fragments;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;

import mobileapps.osubardeals.osubardealsapp.R;
import mobileapps.osubardeals.osubardealsapp.Utilities.FragmentManagerSingleton;
import mobileapps.osubardeals.osubardealsapp.Utilities.JSONHelper;
import mobileapps.osubardeals.osubardealsapp.Utilities.DialogHelper;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BarFragment extends Fragment implements OnMapReadyCallback {

    private TextView name, desc, hoursOp, hours, directions;
    private Button barDealsButton;
    private MapView map;
    private CheckBox favorite;
    private GoogleMap mMap;
    private String address;
    private Button home, deals, bars, favorites;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BarFragment() {
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
    public static BarFragment newInstance(String param1, String param2) {
        BarFragment fragment = new BarFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bar, container, false);
        name = (TextView) v.findViewById(R.id.barName);
        barDealsButton = (Button) v.findViewById(R.id.barDealsButton);
        desc = (TextView) v.findViewById(R.id.barDescription);
        hoursOp = (TextView) v.findViewById(R.id.HoursOp);
        hours = (TextView) v.findViewById(R.id.barHours);
        directions = (TextView) v.findViewById(R.id.directions);
//        map = (MapView) v.findViewById(R.id.barMapView);
        favorite = (CheckBox) v.findViewById(R.id.barFavorite);
        final String email = getContext().getSharedPreferences("preferences", 0).getString("email","false");



        //populate everything
        if (getArguments() != null) {
            name.setText(getArguments().getString("name"));
            desc.setText(getArguments().getString("description"));
            hours.setText(getArguments().getString("hours"));
            address = getArguments().getString("address");
            barDealsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DealsFragment dealsFragment = new DealsFragment();
                    Bundle args = new Bundle();
                    args.putString("barName", getArguments().getString("name"));
                    dealsFragment.setArguments(args);
                    FragmentManagerSingleton.instance().loadFragment(getFragmentManager(), dealsFragment,true);
                }
            });
        }

        initFavorite(getContext(), email,name.getText().toString());
        favorite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //get user session email
                if(!email.equals("false")) {
                    try {
                        setFavorite(getContext(), email, name.getText().toString(), favorite.isChecked());
                    }
                    catch (UnsupportedEncodingException ex){
                        ex.printStackTrace();
                    }
                }
            }
        });

        home= (Button) v.findViewById(R.id.home_button);
        deals= (Button) v.findViewById(R.id.deals_button);
        bars= (Button) v.findViewById(R.id.bars_button);
        favorites= (Button) v.findViewById(R.id.favorites_button);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManagerSingleton.instance().loadFragment(getFragmentManager(),new HomeFragment(),true);
            }
        });
        deals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManagerSingleton.instance().loadFragment(getFragmentManager(),new DealsFragment(),true);
            }
        });
        bars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManagerSingleton.instance().loadFragment(getFragmentManager(),new BarsFragment(),true);

            }
        });
        /*
        barCrawlCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManagerSingleton.instance().loadFragment(getFragmentManager(),new ListBarCrawlFragment(),true);
            }
        });
        */
        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManagerSingleton.instance().loadFragment(getFragmentManager(),new FavoritesFragment(),true);
            }
        });

        SupportMapFragment mapFragment;
        FragmentManager fm = getChildFragmentManager();
        mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return v;
    }

    private void setMarker(String locName, String addressName, Location l) throws IOException{
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        double latitude, longitude;
        latitude = longitude = 0;
        if(l == null) {
            List<Address> addresses = geocoder.getFromLocationName(addressName, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                longitude = address.getLongitude();
                latitude = address.getLatitude();
            }
            else{
                DialogHelper.showDialog(getContext(),"We could not locate the address at this time. Please try again later.","Address not found");
            }
        }
        else{
            latitude = l.getLatitude();
            longitude = l.getLongitude();
        }
        LatLng loc = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(loc).title(locName));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 12));


    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        try {
            //set bar marker
            setMarker(name.getText().toString(), address, null);
            String email = getContext().getSharedPreferences("preferences", 0).getString("email", "false");

            FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());

            if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                        0);
                //check if the permission was set okay
                if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            //set my marker
                            try {
                                setMarker("Me", null, location);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    });
                }
            } else {

                mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        //set my marker
                        try {
                            setMarker("Me", null, location);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }


        }
        catch (IOException ex){
            ex.printStackTrace();
        }




            //setMarker(email,);


    }

    public void initFavorite(Context c, final String email,  String barName) {

// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(c);
        String q="email="+email + "&type=bar&bar_name="+barName;
        String url = "https://osu-bar-deals-api.herokuapp.com/favorites/bar/get?" + q;
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

    public void setFavorite(Context c, String email,String barName, boolean add) throws UnsupportedEncodingException{

// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(c);
        //String url ="http://www.google.com";
        String ext = add ? "add" : "delete";
        String q="email="+email + "&type=bar&bar_name="+barName;
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
