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
import android.widget.TextView;

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
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    private Button registerButton;
    private Button logInButton;
    private EditText emailUsernameField;
    private EditText passwordField;
    private TextView forgotPasswordText;
    private ProgressBar loginSpinner;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public LoginFragment() {
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
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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

    public void loginRequest(final String email, String password) {
        //validate password
        if (email.length() > 0 && email.indexOf('@') != -1 && email.indexOf('.') != -1
                && password.length() > 0) {
            // Inflate the layout for this fragment// Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(getContext());
            //String url ="http://www.google.com";
            String url = "https://osu-bar-deals-api.herokuapp.com/users/login?email="+email+"&password="+password;

            loginSpinner.setVisibility(View.VISIBLE);
            // Request a string response from the provided URL.
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        // Display the first 500 characters of the response string.
                                        //mTextView.setText("Response is: "+ response.substring(0,500));
                                        Log.i("Login: volley res", response);
                                        //login credentials wrong
                                        if(response.equals("false")){



                                            DialogHelper.showDialog(getContext(), "Your email or password is incorrect. Please try again!", "Login Error");
                                        }
                                        else{
                                            //start user session
                                            if(getContext()!=null) {
                                                SharedPreferences pref = getContext().getSharedPreferences("preferences", 0); // 0 - for private mode
                                                SharedPreferences.Editor editor = pref.edit();
                                                editor.putString("email", email);
                                                editor.apply();
                                                FragmentManagerSingleton.instance().loadFragment(getFragmentManager(), new HomeFragment(),true);
                                            }
                                        }
                                        loginSpinner.setVisibility(View.GONE);
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                DialogHelper.showDialog(getContext(), "Network errors. Please try again later.", "Login Network Error");
                                loginSpinner.setVisibility(View.GONE);
                            }
                        });
            // Add the request to the RequestQueue.
                        queue.add(stringRequest);
        }
        else{
            DialogHelper.showDialog(getContext(), "Please enter a valid email and password.", "Login Validation Error");
            loginSpinner.setVisibility(View.GONE);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View v = inflater.inflate(R.layout.fragment_login, container, false);
        registerButton = (Button) v.findViewById(R.id.loginRegisterButton);
        forgotPasswordText = (TextView) v.findViewById(R.id.loginForgotPasswordText);
        logInButton = (Button) v.findViewById(R.id.loginButton);
        emailUsernameField = (EditText) v.findViewById(R.id.loginEmailField);
        passwordField = (EditText) v.findViewById(R.id.loginPasswordField);
        forgotPasswordText = (TextView) v.findViewById(R.id.loginForgotPasswordText);
        loginSpinner = (ProgressBar) v.findViewById(R.id.loginSpinner);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManagerSingleton.instance().loadFragment(getFragmentManager(), new RegisterFragment(), true);
            }
        });

        forgotPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManagerSingleton.instance().loadFragment(getFragmentManager(), new ForgotPasswordFragment(), true);
            }
        });

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FragmentManagerSingleton.instance().loadFragment(getFragmentManager(), new HomeFragment(), true);
                loginRequest(emailUsernameField.getText().toString(), passwordField.getText().toString());
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
