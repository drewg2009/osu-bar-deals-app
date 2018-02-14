package mobileapps.osubardeals.osubardealsapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by drewgallagher on 2/14/18.
 */

public class FragmentManagerSingleton {

    private static FragmentManagerSingleton fms;

    private FragmentManagerSingleton(){

    }

    public static FragmentManagerSingleton instance(){
        if (fms == null) return new FragmentManagerSingleton();
        else return fms;
    }


    public void loadFragment(FragmentManager manager, Fragment f, boolean replace){

        FragmentTransaction transaction = manager.beginTransaction();

        if(replace){
            transaction.replace(R.id.fragmentContainer, f);
        }
        else{
            transaction.add(R.id.fragmentContainer,f,"LoginFragment");
        }
        transaction.addToBackStack(null);
        transaction.commit();
    }


}
