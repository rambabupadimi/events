package com.firebase.events;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.provider.SyncStateContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.events.httpclient.Constants;
import com.firebase.events.httpclient.Get;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EventsAdapter eventsAdapter;
    private RecyclerView eventReyclerView;
    private String TAG = "MainActivity.java";
    private Toolbar mToolbarTop,searchToolbar;
    private Boolean isSearch    =   false;
    private Boolean isMenu      =   false;
    int hcount=0,bcount=0,hircount=0,comcount=0;
    List<WebsiteModel> websiteModelList;
    ExpensesDAO expensesDAO;
    LinearLayout notfoundLayout,loadingLayout;
    Button openFavList,apiQuote;
    TextView totalprojects;
    AppPreferences appPreferences;
    Button sortByFav,sortByCat;

    public boolean checkval = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eventReyclerView    =   (RecyclerView)findViewById(R.id.events_recycler_view);
        expensesDAO = new ExpensesDAO(this);

        appPreferences = new AppPreferences(this);
        notfoundLayout          =   (LinearLayout) findViewById(R.id.not_found_layout);
        loadingLayout           =   (LinearLayout) findViewById(R.id.loading_layout);
        mToolbarTop             =   (Toolbar) findViewById(R.id.toolbar_viewpager);
        searchToolbar           =   (Toolbar) findViewById(R.id.toolbar_search);
        totalprojects           =   (TextView) findViewById(R.id.total_projects);
        apiQuote                =   (Button) findViewById(R.id.api_quote);

        sortByCat               =   (Button) findViewById(R.id.sort_by_category);
        sortByFav               =   (Button) findViewById(R.id.sort_by_favourite);


        prepareActionBar(mToolbarTop);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        notfoundLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.VISIBLE);
        eventReyclerView.setVisibility(View.GONE);

        openFavList     =   (Button) findViewById(R.id.openfavlist);
        openFavList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,FavoriteListActivity.class);
                startActivity(intent);
            }
        });

        Get get             = new Get(this) {
            @Override
            public void onResponseReceived(JSONObject result) {
                try {

                    if(result.get("Response").equals(1))
                    {

                      int quote =  Integer.parseInt(result.get("quote_max").toString());
                      int quoteavailable =Integer.parseInt(result.get("quote_available").toString());
                      apiQuote.setText("API Quota : "+(quote/quoteavailable)+"%");


                        JSONArray jsonArray=(JSONArray)result.get("websites");
                        Log.i(TAG,"json object is"+jsonArray);
                        totalprojects.setText(""+jsonArray.length());
                         websiteModelList = new ArrayList<>();
                        for (int i=0;i<jsonArray.length();i++)
                        {
                           JSONObject   jsonObject = jsonArray.getJSONObject(i);
                           WebsiteModel websiteModel = new WebsiteModel();
                            websiteModel.setId(Integer.parseInt(jsonObject.get("id").toString()));
                           websiteModel.setName(jsonObject.get("name").toString());
                           websiteModel.setImgUrl(jsonObject.get("image").toString());
                           websiteModel.setCategory(jsonObject.get("category").toString());
                           websiteModel.setDescription(jsonObject.get("description").toString());
                            websiteModel.setExperience(jsonObject.get("experience").toString());
                           try
                           {
                               if(jsonObject.get("category").toString().equalsIgnoreCase("HIRING"))
                               {
                                    hircount++;
                               }
                               else if(jsonObject.get("category").toString().equalsIgnoreCase("HACKATHON"))
                               {
                                    hcount++;
                               }
                               else if(jsonObject.get("category").toString().equalsIgnoreCase("BOT")){
                                    bcount++;
                               }
                               else if(jsonObject.get("category").toString().equalsIgnoreCase("competitive")){
                                   comcount++;
                               }

                           }catch (Exception e)
                           {
                               e.printStackTrace();
                           }


                            websiteModelList.add(websiteModel);
                        }



                        Log.i(TAG,"counts "+hircount+" "+hcount+" "+bcount+" "+comcount);
                        HashMap map = new HashMap();
                        map.put("hiring",hcount);
                        map.put("hackathon",hcount);
                        map.put("bot",bcount);
                        map.put("competitive",comcount);


                        try
                        {
                            AppPreferences appPreferences = new AppPreferences(MainActivity.this);
                            appPreferences.saveMap(map);
                            Log.i(TAG,"get map"+appPreferences.loadMap());

                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                        try
                        {
                            if(appPreferences.getAppInsertDb().toString().equalsIgnoreCase("false")) {
                                    insertInToDB(websiteModelList);
                            }
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                        try {
                            List<WebsiteModel> data = expensesDAO.getData();

                            if (data!=null && data.size()>0)  {
                                eventReyclerView.setVisibility(View.VISIBLE);
                                notfoundLayout.setVisibility(View.GONE);
                                loadingLayout.setVisibility(View.GONE);
                                eventsAdapter = new EventsAdapter(MainActivity.this, data);
                                LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                                eventReyclerView.setLayoutManager(layoutManager);
                                eventReyclerView.setAdapter(eventsAdapter);
                            } else {
                                eventReyclerView.setVisibility(View.GONE);
                                notfoundLayout.setVisibility(View.VISIBLE);
                                loadingLayout.setVisibility(View.GONE);
                            }
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                    else if (result.get("Response").equals(0)) {
                        Log.e(TAG,"response 0 profile not exist");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        get.execute(Constants.API+"");


        sortByCat.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {

                                             try {
                                                 List<WebsiteModel> data = expensesDAO.getDataSortByCat();

                                                 if (data != null && data.size() > 0) {
                                                     eventReyclerView.setVisibility(View.VISIBLE);
                                                     notfoundLayout.setVisibility(View.GONE);
                                                     loadingLayout.setVisibility(View.GONE);
                                                     eventsAdapter = new EventsAdapter(MainActivity.this, data);
                                                     LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                                                     eventReyclerView.setLayoutManager(layoutManager);
                                                     eventReyclerView.setAdapter(eventsAdapter);
                                                 } else {
                                                     eventReyclerView.setVisibility(View.GONE);
                                                     notfoundLayout.setVisibility(View.VISIBLE);
                                                     loadingLayout.setVisibility(View.GONE);
                                                 }
                                             } catch (Exception e) {
                                                 e.printStackTrace();
                                             }
                                         }
                                     });

        sortByFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    List<WebsiteModel> data = expensesDAO.getDataSortByFav();

                    if (data!=null && data.size()>0)  {
                        eventReyclerView.setVisibility(View.VISIBLE);
                        notfoundLayout.setVisibility(View.GONE);
                        loadingLayout.setVisibility(View.GONE);
                        eventsAdapter = new EventsAdapter(MainActivity.this, data);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                        eventReyclerView.setLayoutManager(layoutManager);
                        eventReyclerView.setAdapter(eventsAdapter);
                    } else {
                        eventReyclerView.setVisibility(View.GONE);
                        notfoundLayout.setVisibility(View.VISIBLE);
                        loadingLayout.setVisibility(View.GONE);
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }


    public int insertInToDB(List<WebsiteModel> data)
    {
        ExpensesDAO expensesDAO = new ExpensesDAO(this);

        try {
            for (int i = 0; i < data.size(); i++) {
                WebsiteModel websiteModel = (WebsiteModel) data.get(i);
                expensesDAO.insert(websiteModel);
            }
            appPreferences.setAppInsertDb("true");
            return 1;
        }catch (Exception e)
        {
            e.printStackTrace();
            return  0;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(isSearch ? R.menu.menu_search_toolbar : R.menu.menu_personal, menu);
        if (isSearch) {

            isMenu=true;
            //Toast.makeText(getApplicationContext(), "Search " + isSearch, Toast.LENGTH_SHORT).show();
            final SearchView search = (SearchView) menu.findItem(R.id.action_search).getActionView();
            search.setIconified(false);
            search.setQueryHint("Search...");

            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                        try {
                            if (eventsAdapter.getFilter() != null) {
                                eventsAdapter.getFilter().filter(s);
                            }
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                    return true;
                }
            });
            search.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    closeSearch();
                    return true;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);

    }


    @Override
    protected void onResume() {
        super.onResume();
        try {
            List<WebsiteModel> data = expensesDAO.getData();

            if (data!=null && data.size()>0)  {
                eventReyclerView.setVisibility(View.VISIBLE);
                notfoundLayout.setVisibility(View.GONE);
                loadingLayout.setVisibility(View.GONE);
                eventsAdapter = new EventsAdapter(MainActivity.this, data);
                LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                eventReyclerView.setLayoutManager(layoutManager);
                eventReyclerView.setAdapter(eventsAdapter);
            } else {
                eventReyclerView.setVisibility(View.GONE);
                notfoundLayout.setVisibility(View.VISIBLE);
                loadingLayout.setVisibility(View.GONE);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(isMenu)
        {

            if(id==android.R.id.home) {
                closeSearch();
                return true;
            }

        }
        switch (id)
        {
       case R.id.search: {

           isSearch = true;
                searchToolbar.setVisibility(View.VISIBLE);
                prepareActionBar(searchToolbar);
                supportInvalidateOptionsMenu();
                return true;
            }

        }
        return super.onOptionsItemSelected(item);
    }


    private void closeSearch() {

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        if (isSearch) {
            isSearch = false;
            isMenu   =false;
            try {
                    eventsAdapter.getFilter().filter("");

            }catch (Exception e)
            {
                e.printStackTrace();
            }

            prepareActionBar(mToolbarTop);
            searchToolbar.setVisibility(View.GONE);
            supportInvalidateOptionsMenu();
        }
    }


    private void prepareActionBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Events");
        ActionBar actionBar = getSupportActionBar();
       actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

}
