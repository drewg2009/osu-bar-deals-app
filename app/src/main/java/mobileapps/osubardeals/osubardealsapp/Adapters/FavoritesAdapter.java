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
        public TextView nameTextView;
        public TextView dealNameTextView;


        public ViewHolder(CardView cardView, TextView nameTextView, TextView dealNameTextView,
                            LinearLayout ll) {
            super(ll);
            this.ll = ll;
            this.cardView = cardView;
            this.nameTextView = nameTextView;
            this.dealNameTextView = dealNameTextView;
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
        final TextView nameTextView = favoriteCard.findViewById(R.id.nameTextViewFavorite);
        final TextView dealNameTextView = favoriteCard.findViewById(R.id.dealNameTextViewFavorite);

        ViewHolder vh = new ViewHolder
                (cardView, nameTextView, dealNameTextView, favoriteCard);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        try {
            holder.nameTextView.setText(mDataset.get(position).getString("bar_name"));
            if(mDataset.get(position).getString("type").equals("deal")) {
                holder.dealNameTextView.setText(mDataset.get(position).getString("deal_name"));
            }
            else{
                holder.dealNameTextView.setVisibility(View.GONE );
            }

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
