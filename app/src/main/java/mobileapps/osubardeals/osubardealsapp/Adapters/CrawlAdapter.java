package mobileapps.osubardeals.osubardealsapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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

import mobileapps.osubardeals.osubardealsapp.Fragments.BarCrawlFragment;
import mobileapps.osubardeals.osubardealsapp.R;
import mobileapps.osubardeals.osubardealsapp.Utilities.FragmentManagerSingleton;

/**
 * Created by Erin George on 3/23/18.
 */

public class CrawlAdapter extends RecyclerView.Adapter<CrawlAdapter.ViewHolder> {
    private List<JSONObject> mDataset;
private Context c;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public LinearLayout ll;
        public CardView cardView;
        public TextView crawlNameTextView;
        public TextView crawlStartTextView;
        public TextView crawlDateTextView;


        public ViewHolder(CardView cardView,  TextView crawlNameTextView, TextView crawlStartTextView,
                          TextView crawlDateTextView,  LinearLayout ll) {
            super(ll);
            this.ll = ll;
            this.cardView = cardView;
            this.crawlNameTextView = crawlNameTextView;
            this.crawlDateTextView = crawlDateTextView;
            this.crawlStartTextView = crawlStartTextView;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CrawlAdapter(List<JSONObject> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CrawlAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent,
                                                      int viewType) {

        final LinearLayout crawlCard = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_crawl, parent, false);



        CardView cardView = crawlCard.findViewById(R.id.crawlCard);
        final TextView crawlNameTextView = crawlCard.findViewById(R.id.nameTextViewCrawl);
        final TextView crawlDateTextView = crawlCard.findViewById(R.id.dateTextViewCrawl);
        final TextView crawlStartTextView = crawlCard.findViewById(R.id.startTextViewCrawl);

        ViewHolder vh = new ViewHolder
                (cardView, crawlNameTextView, crawlStartTextView, crawlDateTextView, crawlCard);

        crawlCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle crawlBundle = new Bundle();
                crawlBundle.putString("name", crawlNameTextView.getText().toString());
                crawlBundle.putString("time", crawlDateTextView.getText().toString());
                crawlBundle.putString("startLocation", crawlStartTextView.getText().toString());
                BarCrawlFragment crawl= new BarCrawlFragment();
                crawl.setArguments(crawlBundle);
                ((Activity) parent.getContext()).getFragmentManager();
                FragmentManagerSingleton.instance().loadFragment(((FragmentActivity) parent.getContext()).getSupportFragmentManager(), crawl, true);
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
            holder.crawlDateTextView.setText(mDataset.get(position).getString("time"));
            holder.crawlNameTextView.setText(mDataset.get(position).getString("name"));
            holder.crawlStartTextView.setText(mDataset.get(position).getString("startLocation"));


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
