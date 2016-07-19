package com.bluetea.abbiswood.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluetea.abbiswood.R;
import com.bluetea.abbiswood.VotedListFragment;
import com.bluetea.abbiswood.helper.Commons;
import com.bluetea.abbiswood.helper.Connectivity;
import com.bluetea.abbiswood.helper.ApiClient;
import com.bluetea.abbiswood.vo.ListingVO;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by MEK on 6/10/2016.
 */
public class ListingsAdapter extends RecyclerView.Adapter<ListingsAdapter.ItemViewHolder>{

    private VotedListFragment context;
    private boolean voting;
    private List<ListingVO> listings;
    RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Connectivity.BASE_URL).build();
    ApiClient apiClient =
            restAdapter.create(ApiClient.class);

    private Long type;
    private boolean canVote;

    public ListingsAdapter(VotedListFragment context, boolean voting, List<ListingVO> listings) {
        this.context = context;
        this.voting = voting;
        this.listings = listings;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        if(voting){
            v =  LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_listing_voted, parent, false);
        }else{
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_listing_nonvoted, parent, false);
        }
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        holder.tvTitle.setText(listings.get(position).name);
        if(listings.get(position).poster!= null)
            Picasso.with(context.getActivity()).load(Connectivity.ROOT_URL + listings.get(position).poster).placeholder(R.drawable.holder).error(R.drawable.holder).into(holder.ivPoster);
        else
            holder.ivPoster.setVisibility(View.INVISIBLE);
        if(voting){
            holder.tvVotes.setText(listings.get(position).votes + "");
            if(!canVote){
                holder.ibVote.setVisibility(View.GONE);
            }else{
                holder.ibVote.setVisibility(View.VISIBLE);
            }
            if(canVote)
                holder.ibVote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Callback<Long> callback = new Callback<Long>() {
                        @Override
                        public void success(Long votes, Response response) {
                            if(votes == -1){
                                Commons.showErrorAlert(context.getActivity(), "Seems like you have already voted");
                            }
                            context.refreshContent(type,true);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Commons.showErrorAlert(context.getActivity(), "Something bad happened, please check your internet connection");
                        }
                    };

                    String deviceCode = Commons.getDeviceCode(context.getActivity());
                    if(deviceCode == null){
                        Commons.showErrorAlert(context.getActivity(), "Sorry, You cannot vote with this device!");
                    }else{
                        apiClient.vote(type,deviceCode,listings.get(position).id,callback);
                    }

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listings.size();
    }

    public List<ListingVO> getListings() {
        return listings;
    }

    public void setListings(List<ListingVO> listings) {
        this.listings = listings;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public boolean isCanVote() {
        return canVote;
    }

    public void setCanVote(boolean canVote) {
        this.canVote = canVote;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{

        @InjectView(R.id.iv_poster)
        ImageView ivPoster;
        @Optional
        @InjectView(R.id.tv_votes)
        TextView tvVotes;
        @InjectView(R.id.tv_title)
        TextView tvTitle;
        @Optional
        @InjectView(R.id.ib_vote)
        ImageButton ibVote;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

}
