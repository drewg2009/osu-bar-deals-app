package mobileapps.osubardeals.osubardealsapp.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import mobileapps.osubardeals.osubardealsapp.R;
import mobileapps.osubardeals.osubardealsapp.Utilities.DialogHelper;
import mobileapps.osubardeals.osubardealsapp.Utilities.FragmentManagerSingleton;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegisterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {


    private Button registerButton;
    private EditText emailField;
    private EditText passwordField;
    private EditText confirmPasswordField;
    private ProgressBar registerSpinner;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RegisterFragment() {
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
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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

    public void registerRequest(final String email, String password, String confirmPassword) {
        //validate password
        if (email.length() > 0 && email.indexOf('@') != -1 && email.indexOf('.') != -1
                && password.length() > 0
                && confirmPassword.length() > 0
                && password.equals(confirmPassword)) {
            // Inflate the layout for this fragment// Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(getContext());
            //String url ="http://www.google.com";
            String url = "https://osu-bar-deals-api.herokuapp.com/users/add?email="+email+"&password="+password;

            registerSpinner.setVisibility(View.VISIBLE);
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            //mTextView.setText("Response is: "+ response.substring(0,500));
                            Log.i("Signup: volley res", response);
                            //login credentials wrong
                            if(response.equals("false")){
                                DialogHelper.showDialog(getContext(), "That user already exists. Please try another email!", "Login Error");
                            }
                            else{
                                //start user session
                                SharedPreferences pref = getContext().getSharedPreferences("preferences", 0); // 0 - for private mode
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("email", email);
                                editor.apply();

                                FragmentManagerSingleton.instance().loadFragment(getFragmentManager(), new HomeFragment(),true);
                            }
                            registerSpinner.setVisibility(View.GONE);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    DialogHelper.showDialog(getContext(), "Network errors. Please try again later.", "Register Network Error");
                    registerSpinner.setVisibility(View.GONE);
                    //mTextView.setText("That didn't work!");
                }
            });
            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        }
        else{
            DialogHelper.showDialog(getContext(), "Please enter a valid email, password, and confirm password.", "Register Validation Error");
            registerSpinner.setVisibility(View.GONE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        registerButton = (Button) v.findViewById(R.id.registerButton);
        emailField = (EditText) v.findViewById(R.id.registerEmailField);
        passwordField = (EditText) v.findViewById(R.id.registerPasswordField);
        confirmPasswordField = (EditText)v.findViewById(R.id.registerConfirmPasswordField);
        registerSpinner = (ProgressBar) v.findViewById(R.id.registerSpinner);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerRequest(emailField.getText().toString(),passwordField.getText().toString(),confirmPasswordField.getText().toString());
            }
        });

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
