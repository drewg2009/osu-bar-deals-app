package mobileapps.osubardeals.osubardealsapp.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        manager = getSupportFragmentManager();
        autoLogin(true);
        Log.i("lifeCycle", "onCreate");
    }

    public void autoLogin(boolean ignore) {
        if(ignore){
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

