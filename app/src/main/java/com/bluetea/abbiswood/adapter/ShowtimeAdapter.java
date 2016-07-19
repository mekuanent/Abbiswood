package com.bluetea.abbiswood.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluetea.abbiswood.R;
import com.bluetea.abbiswood.helper.Connectivity;
import com.bluetea.abbiswood.vo.ShowtimeVO;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by MEK on 6/10/2016.
 */
public class ShowtimeAdapter extends RecyclerView.Adapter<ShowtimeAdapter.ItemViewHolder>{

    public List<ShowtimeVO> getShowtime() {
        return showtime;
    }

    public void setShowtime(List<ShowtimeVO> showtime) {
        this.showtime = showtime;
    }

    private Context context;
    public List<ShowtimeVO> showtime;

    public ShowtimeAdapter(Context context, List<ShowtimeVO> showtime) {
        this.context = context;
        this.showtime = showtime;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_showtime, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.tvCinema.setText(showtime.get(position).cinema);
        StringBuilder sb = new StringBuilder();
        for(ShowtimeVO.ST st : showtime.get(position).showtimes){

            sb.append(st.time).append(" : ").append(st.movie).append("\n");

        }
        holder.tvShowtime.setText(sb.toString());
    }

    @Override
    public int getItemCount() {
        return showtime.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{

        @InjectView(R.id.tv_cinema)
        TextView tvCinema;
        @InjectView(R.id.tv_showtime)
        TextView tvShowtime;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this,itemView);
        }
    }

}
