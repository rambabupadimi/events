package com.firebase.events;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FavouriteListAdapter extends RecyclerView.Adapter<FavouriteListAdapter.CustomViewHolder>{
    private List<WebsiteModel> websiteModalList;
    private Context mContext;
    private String TAG = "Favourite.java";



    public FavouriteListAdapter(Context context, List<WebsiteModel> websiteModalList) {
        this.mContext       =   context;
             this.websiteModalList = websiteModalList;

    }




    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_adapter, null);
        CustomViewHolder holder=new CustomViewHolder(view);
        return  holder;

    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {


        final WebsiteModel websiteModel= websiteModalList.get(position);
        holder.name.setText(Html.fromHtml(websiteModel.getName()));
        holder.category.setText(Html.fromHtml(websiteModel.getCategory()));

        if(websiteModel.getImgUrl().equalsIgnoreCase("null") || websiteModel.getImgUrl().equals("") ) {
            holder.imageView.setImageResource(R.drawable.default_user_men_icon);
        }
        else
        {
            Picasso
                    .with(mContext)
                    .load(websiteModel.getImgUrl())
                    .into(holder.imageView);
        }
        Log.i(TAG,"is fav"+websiteModel.getIsfavorite());
        Log.i(TAG,"id"+websiteModel.getId());
        if(websiteModel.getIsfavorite()!=null) {
            if (websiteModel.getIsfavorite().equalsIgnoreCase("true")) {
                holder.star.setChecked(true);
            }
            else
            {
                holder.star.setChecked(false);
            }
        }



    }


    @Override
    public int getItemCount() {
        //   return (null != filtered_items ? filtered_items.size() : 0);
        return (null != websiteModalList ? websiteModalList.size() : 0);

        //      return 5;
    }
    public class CustomViewHolder extends RecyclerView.ViewHolder
    {

        TextView name,category;
        ImageView imageView;
        ToggleButton star;
        CardView cardView;
        public CustomViewHolder(View itemView) {
            super(itemView);

            this.name=(TextView)itemView.findViewById(R.id.website_name);
            this.category=(TextView) itemView.findViewById(R.id.website_category);
            this.imageView  =(ImageView)itemView.findViewById(R.id.website_image);
            this.cardView   =   (CardView)itemView.findViewById(R.id.cardview);
            this.star       =   (ToggleButton) itemView.findViewById(R.id.star);

        }
    }


}
