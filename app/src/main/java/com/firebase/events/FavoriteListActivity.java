package com.firebase.events;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;


public class FavoriteListActivity extends AppCompatActivity {

    private String TAG = "FavoriteListActivity.java";

    private RecyclerView recyclerView;
    LinearLayout    notfoundLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_list);

        recyclerView    =   (RecyclerView) findViewById(R.id.fav_recyclerview);
        notfoundLayout  =   (LinearLayout) findViewById(R.id.not_found_layout);


        ExpensesDAO expensesDAO = new ExpensesDAO(this);
        Log.i(TAG,""+expensesDAO.getDataFavouriteList());

        List<WebsiteModel>  getFavList = expensesDAO.getDataFavouriteList();
        if(getFavList!=null && getFavList.size()>0) {
            notfoundLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            FavouriteListAdapter favouriteListAdapter = new FavouriteListAdapter(this, getFavList);
            LinearLayoutManager layoutManager = new LinearLayoutManager(FavoriteListActivity.this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(favouriteListAdapter);

        }
        else
        {
            recyclerView.setVisibility(View.GONE);
            notfoundLayout.setVisibility(View.VISIBLE);
        }




    }
}
