package mobileapps.osubardeals.osubardealsapp.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import mobileapps.osubardeals.osubardealsapp.R;

/**
 * Created by drewgallagher on 3/7/18.
 */

public class DealsAdapter extends RecyclerView.Adapter<DealsAdapter.ViewHolder> {
    private List<JSONObject> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CardView cardView;
        public ImageView imageView;
        public TextView descTextView;
        public TextView hoursTextView;
        public TextView locationTextView;

        public ViewHolder(CardView cardView, ImageView imageView, TextView descTextView,
                          TextView hoursTextView, TextView locationTextView) {
            super(cardView);
            this.cardView = cardView;
            this.imageView = imageView;
            this.descTextView = descTextView;
            this.hoursTextView = hoursTextView;
            this.locationTextView = locationTextView;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public DealsAdapter(List<JSONObject> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DealsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {

        CardView dealCard = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_deal, parent, false);
        ImageView imageView = (ImageView) dealCard.findViewById(R.id.dealImageView);
        TextView descTextView = (TextView) dealCard.findViewById(R.id.descTextView);
        TextView hoursTextView = (TextView) dealCard.findViewById(R.id.hoursTextView);
        TextView locationTextView = (TextView) dealCard.findViewById(R.id.locationTextView);

        // create a new view
//        TextView v = (TextView) LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.my_text_view, parent, false);

        ViewHolder vh = new ViewHolder(dealCard, imageView, descTextView, hoursTextView, locationTextView);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        try {
            holder.locationTextView.setText(mDataset.get(position).getString("location"));
            holder.hoursTextView.setText(mDataset.get(position).getString("hours"));
            holder.descTextView.setText(mDataset.get(position).getString("description"));

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
