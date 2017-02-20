package com.firebase.events;

        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.Color;
        import android.os.Bundle;
        import android.support.v7.widget.CardView;
        import android.support.v7.widget.RecyclerView;
        import android.text.Html;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Filter;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.TextView;
        import android.widget.Toast;
        import android.widget.ToggleButton;

        import com.squareup.picasso.Picasso;

        import java.io.FileInputStream;
        import java.io.FileNotFoundException;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.Serializable;
        import java.net.MalformedURLException;
        import java.net.URL;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.CustomViewHolder>{
        private List<WebsiteModel> websiteModalList;
        private Context mContext;
        private String TAG = "EventsAdapter.java";
    private List<WebsiteModel> original_items = new ArrayList<>();
    private List<WebsiteModel> filtered_items = new ArrayList<>();
    private ItemFilter mFilter = new ItemFilter();

    FileOutputStream fileOutputStream;



    public EventsAdapter(Context context, List<WebsiteModel> websiteModalList) {
        this.mContext       =   context;
        this.original_items =   websiteModalList;
        this.filtered_items =   websiteModalList;
   //     this.websiteModalList = websiteModalList;
        this.fileOutputStream = fileOutputStream;
    }



    public Filter getFilter() {
        return mFilter;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_adapter, null);
        CustomViewHolder holder=new CustomViewHolder(view);
        return  holder;

    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, int position) {


        final WebsiteModel websiteModel= filtered_items.get(position);
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




        holder.star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               if( holder.star.isChecked() == true)
               {
                   holder.star.setChecked(true);

                   ExpensesDAO expensesDAO = new ExpensesDAO(mContext);
                   expensesDAO.updateIsFavoriteTrue(websiteModel.getId(),"true");

               }
                else
               {

                   ExpensesDAO expensesDAO = new ExpensesDAO(mContext);
                   expensesDAO.updateIsFavoriteTrue(websiteModel.getId(),"false");

                   holder.star.setChecked(false);
               }

            }
        });


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,WebsiteDetailsActivity.class);
                intent.putExtra("name",websiteModel.getName());
                intent.putExtra("description",websiteModel.getDescription());
                intent.putExtra("image",websiteModel.getImgUrl());
                intent.putExtra("experience",websiteModel.getExperience());

                mContext.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
     //   return (null != filtered_items ? filtered_items.size() : 0);
        return (null != filtered_items ? filtered_items.size() : 0);

  //      return 5;
    }
    public class CustomViewHolder extends RecyclerView.ViewHolder
    {

        TextView name,category;
        ImageView imageView;
        ToggleButton star;
        CardView    cardView;
      public CustomViewHolder(View itemView) {
            super(itemView);

            this.name=(TextView)itemView.findViewById(R.id.website_name);
            this.category=(TextView) itemView.findViewById(R.id.website_category);
            this.imageView  =(ImageView)itemView.findViewById(R.id.website_image);
            this.cardView   =   (CardView)itemView.findViewById(R.id.cardview);
            this.star       =   (ToggleButton) itemView.findViewById(R.id.star);

        }
    }



    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String query = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();
            final List<WebsiteModel> list = original_items;
            final List<WebsiteModel> result_list = new ArrayList<>(list.size());

            Log.e(TAG,"before filter orginal items"+original_items);
            Log.e(TAG,"before filter result_list"+result_list);

            for (int i = 0; i < list.size(); i++) {
                String str_title = list.get(i).getName();
                if (str_title.toLowerCase().contains(query)) {
                    result_list.add(list.get(i));
                }
            }

            results.values = result_list;
            results.count = result_list.size();

            Log.e(TAG,"after filter orginal items"+result_list);
            Log.e(TAG,"after filter result_list"+result_list.size());


            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filtered_items = (List<WebsiteModel>) results.values;
            notifyDataSetChanged();
        }
    }



}
