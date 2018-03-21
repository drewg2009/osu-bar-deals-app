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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import mobileapps.osubardeals.osubardealsapp.Fragments.BarFragment;
import mobileapps.osubardeals.osubardealsapp.R;
import mobileapps.osubardeals.osubardealsapp.Utilities.FragmentManagerSingleton;

/**
 * Created by Jeremy Clark on 3/17/18.
 */

public class BarsAdapter extends RecyclerView.Adapter<BarsAdapter.ViewHolder> {
    private List<JSONObject> mDataset;
private Context c;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public LinearLayout ll;
        public CardView cardView;
        public ImageView imageView;
        public TextView nameTextView;
        public TextView descTextView;
        public TextView hoursTextView;
        public TextView addressTextView;


        public ViewHolder(CardView cardView, ImageView imageView, TextView nameTextView, TextView descTextView,
                          TextView hoursTextView, TextView addressTextView, LinearLayout ll) {
            super(ll);
            this.ll = ll;
            this.cardView = cardView;
            this.imageView = imageView;
            this.nameTextView = nameTextView;
            this.descTextView = descTextView;
            this.hoursTextView = hoursTextView;
            this.addressTextView = addressTextView;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public BarsAdapter(List<JSONObject> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public BarsAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent,
                                                     int viewType) {

        final LinearLayout barCard = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_bar, parent, false);



        CardView cardView = barCard.findViewById(R.id.barCard);
        ImageView imageView = barCard.findViewById(R.id.barImageView);
        final TextView nameTextView = barCard.findViewById(R.id.nameTextViewBar);
        TextView descTextView = barCard.findViewById(R.id.descTextViewBar);
        TextView hoursTextView = barCard.findViewById(R.id.hoursTextViewBar);
        TextView addressTextView = barCard.findViewById(R.id.addressTextViewBar);

        ViewHolder vh = new ViewHolder
                (cardView, imageView, nameTextView, descTextView, hoursTextView, addressTextView, barCard);

        barCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle barBundle = new Bundle();
                barBundle.putString("name", nameTextView.getText().toString());
                BarFragment bar= new BarFragment();
                bar.setArguments(barBundle);
                ((Activity) parent.getContext()).getFragmentManager();
                FragmentManagerSingleton.instance().loadFragment(((FragmentActivity) parent.getContext()).getSupportFragmentManager(), bar, true);
        }

        });
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        try {
            holder.nameTextView.setText(mDataset.get(position).getString("name"));
            holder.descTextView.setText(mDataset.get(position).getString("description"));
            holder.hoursTextView.setText(mDataset.get(position).getString("hours"));
            holder.addressTextView.setText(mDataset.get(position).getString("address"));

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
