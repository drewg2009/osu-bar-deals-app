package mobileapps.osubardeals.osubardealsapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
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
        public LinearLayout ll;
        public TextView tv;
        public ViewHolder(LinearLayout ll) {
            super(ll);
            this.ll = ll;
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

        LinearLayout dealLayout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_deal, parent, false);
        TextView tv = (TextView) dealLayout.findViewById(R.id.dealTextView);

        // create a new view
//        TextView v = (TextView) LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.my_text_view, parent, false);

        ViewHolder vh = new ViewHolder(dealLayout);
        vh.tv = tv;
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        try {
            holder.tv.setText(mDataset.get(position).getString("bar_name"));

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
