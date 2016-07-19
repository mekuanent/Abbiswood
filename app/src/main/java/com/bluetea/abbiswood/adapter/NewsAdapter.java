package com.bluetea.abbiswood.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluetea.abbiswood.R;
import com.bluetea.abbiswood.helper.Commons;
import com.bluetea.abbiswood.helper.Connectivity;
import com.bluetea.abbiswood.vo.NewsVO;
import com.flyco.animation.ZoomEnter.ZoomInEnter;
import com.flyco.animation.ZoomExit.ZoomOutExit;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

/**
 * Created by MEK on 6/30/2016.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ItemViewHolder>{

    private Context context;

    public List<NewsVO> getList() {
        return list;
    }

    public void setList(List<NewsVO> list) {
        this.list = list;
    }

    private List<NewsVO> list;

    public NewsAdapter(Context context, List<NewsVO> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        holder.tvTitle.setText(list.get(position).title);
        holder.tvDetail.setText(list.get(position).detail.length()> 20? list.get(position).detail.substring(0, 20)
                :list.get(position).detail);
        if(list.get(position).pic!= null)
            Picasso.with(context).load(Connectivity.ROOT_URL + list.get(position).pic).placeholder(R.drawable.holder).error(R.drawable.holder).into(holder.ivNews);
        else
            holder.ivNews.setVisibility(View.GONE);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Commons.showInfoAlert(context, list.get(position).detail, list.get(position).title);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{

        @InjectView(R.id.iv_news)
        ImageView ivNews;
        @Optional
        @InjectView(R.id.tv_detail)
        TextView tvDetail;
        @InjectView(R.id.tv_title)
        TextView tvTitle;

        View view;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            view = itemView;
        }
    }

}
