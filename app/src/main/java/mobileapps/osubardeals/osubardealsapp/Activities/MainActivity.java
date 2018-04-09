package mobileapps.osubardeals.osubardealsapp.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import mobileapps.osubardeals.osubardealsapp.Fragments.HomeFragment;
import mobileapps.osubardeals.osubardealsapp.Fragments.LoginFragment;
import mobileapps.osubardeals.osubardealsapp.R;
import mobileapps.osubardeals.osubardealsapp.Utilities.FragmentManagerSingleton;

public class MainActivity extends AppCompatActivity {

    FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = getSupportFragmentManager();
        getAllBarsIfNotUpdated();

        Log.i("lifeCycle", "onCreate");
    }

    public void autoLogin(boolean ignore) {
        if (ignore) {
            LoginFragment loginFragment = new LoginFragment();
            FragmentManagerSingleton.instance().loadFragment(manager, loginFragment, false);
        }
        //an email is set so auto login
        else if (!getSharedPreferences("preferences", 0).getString("email", "false").equals("false")) {
            HomeFragment homeFragment = new HomeFragment();
            FragmentManagerSingleton.instance().loadFragment(manager, homeFragment, true);
        } else {
            LoginFragment loginFragment = new LoginFragment();
            FragmentManagerSingleton.instance().loadFragment(manager, loginFragment, false);
        }
    }

    public void getAllBarsIfNotUpdated() {
        final SharedPreferences sp = getSharedPreferences("preferences", 0);


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        //String url ="http://www.google.com";
        String url = "https://osu-bar-deals-api.herokuapp.com/bars/all";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //if not cached or the cache is not up to date, cache the data
                        if (sp.getString("barsJSON", "false").equals("false") ||
                                !sp.getString("barsJSON", "false").equals(response)) {
                            sp.edit().putString("barsJSON", response).apply();
                        }
                        getAllDealsIfNotUpdated();

                    }
                }, new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("volley error", error.toString());
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void getAllDealsIfNotUpdated() {
        final SharedPreferences sp = getSharedPreferences("preferences", 0);


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        //String url ="http://www.google.com";
        String url = "https://osu-bar-deals-api.herokuapp.com/deals/all";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //if not cached or the cache is not up to date, cache the data
                        if (sp.getString("dealsJSON", "false").equals("false") ||
                                !sp.getString("dealsJSON", "false").equals(response)) {
                            sp.edit().putString("dealsJSON", response).apply();
                        }
                        autoLogin(true);

                    }
                }, new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("volley error", error.toString());
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("lifeCycle", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("lifeCycle", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("lifeCycle", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("lifeCycle", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("lifeCycle", "onDestroy");
    }
}

