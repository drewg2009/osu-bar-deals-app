package mobileapps.osubardeals.osubardealsapp.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import mobileapps.osubardeals.osubardealsapp.R;

/**
 * Created by Jeremy Clark on 3/17/18.
 */

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {
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
    public FavoritesAdapter(List<JSONObject> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FavoritesAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent,
                                                          int viewType) {

        final LinearLayout favoriteCard = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_favorite, parent, false);



        CardView cardView = favoriteCard.findViewById(R.id.favoriteCard);
        ImageView imageView = favoriteCard.findViewById(R.id.favoriteImageView);
        final TextView nameTextView = favoriteCard.findViewById(R.id.nameTextViewFavorite);
        final TextView descTextView = favoriteCard.findViewById(R.id.descTextViewFavorite);
        final TextView hoursTextView = favoriteCard.findViewById(R.id.hoursTextViewFavorite);
//        TextView addressTextView = favoriteCard.findViewById(R.id.addressTextViewFavorite);

        ViewHolder vh = new ViewHolder
                (cardView, imageView, nameTextView, descTextView, hoursTextView, null, favoriteCard);

        favoriteCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO change this up based on deal or bar
//                Bundle barBundle = new Bundle();
//                barBundle.putString("location", nameTextView.getText().toString());
//                barBundle.putString("description", descTextView.getText().toString());
//                barBundle.putString("hours", hoursTextView.getText().toString());
//                BarFragment bar= new BarFragment();
//                bar.setArguments(barBundle);
//                ((Activity) parent.getContext()).getFragmentManager();
//                FragmentManagerSingleton.instance().loadFragment(((FragmentActivity) parent.getContext()).getSupportFragmentManager(), bar, true);
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
            holder.nameTextView.setText(mDataset.get(position).getString("location"));
            holder.descTextView.setText(mDataset.get(position).getString("description"));
            holder.hoursTextView.setText(mDataset.get(position).getString("hours"));

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
