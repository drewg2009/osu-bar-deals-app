package mobileapps.osubardeals.osubardealsapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import mobileapps.osubardeals.osubardealsapp.Fragments.BarFragment;
import mobileapps.osubardeals.osubardealsapp.R;
import mobileapps.osubardeals.osubardealsapp.Utilities.FragmentManagerSingleton;

/**
 * Created by Erin George on 3/23/18.
 */

public class SelectBarsAdapter extends RecyclerView.Adapter<SelectBarsAdapter.ViewHolder> {
    private List<JSONObject> mDataset;
private Context c;
    ArrayList<String> selectedStrings = new ArrayList<String>();

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public LinearLayout ll;
        public CheckBox checkBox;

        public ViewHolder(CheckBox checkBox, LinearLayout ll) {
            super(ll);
            this.ll = ll;
            this.checkBox = checkBox;

        }
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public SelectBarsAdapter(List<JSONObject> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SelectBarsAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent,
                                                           int viewType) {

        final LinearLayout selectBarCard = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_select_bars, parent, false);



        final CheckBox checkBox = selectBarCard.findViewById(R.id.selectedBar);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedStrings.add(checkBox.getText().toString());
                }else{
                    selectedStrings.remove(checkBox.getText().toString());
                }

            }
        });

        ViewHolder vh = new ViewHolder
                (checkBox, selectBarCard);

        selectBarCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add stuff to add info to database
                Log.i("add", "Added to crawl");
        }

        });

        return vh;
    }

    ArrayList<String> getSelectedString(){
        return selectedStrings;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        try {
            holder.checkBox.setText(mDataset.get(position).getString("name"));

        }
        catch (JSONException ex){
            ex.printStackTrace();
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
