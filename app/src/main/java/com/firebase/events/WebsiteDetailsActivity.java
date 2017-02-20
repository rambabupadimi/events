package com.firebase.events;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class WebsiteDetailsActivity extends AppCompatActivity {

    private TextView experience,description;
    private ImageView detailImageView;
    Button statistics,back,share;
    TextView heading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website_details);


        heading     =   (TextView) findViewById(R.id.toolbar_heading);
        experience  =   (TextView) findViewById(R.id.experienceValue);
        description =   (TextView) findViewById(R.id.descriptionValue);
        detailImageView =   (ImageView) findViewById(R.id.website_detail_image);

        statistics  =   (Button) findViewById(R.id.statistics);
        share       =   (Button) findViewById(R.id.share);
        back        =   (Button) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        String shareBody = "Name :" +getIntent().getStringExtra("name").toString()+ " Experience " + getIntent().getStringExtra("experience").toString();
                        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, " SHARING");
                        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                        startActivity(Intent.createChooser(sharingIntent, "Share via"));

            }
        });



        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WebsiteDetailsActivity.this,Statistics.class);
                startActivity(intent);
            }
        });
        try {

            heading.setText(getIntent().getStringExtra("name").toString());
            experience.setText(getIntent().getStringExtra("experience").toString());
            description.setText(getIntent().getStringExtra("description").toString());
            String url = getIntent().getStringExtra("image").toString();
            if(url.equalsIgnoreCase("null") || url.equals("") ) {
                detailImageView.setImageResource(R.drawable.default_user_men_icon);
            }
            else
            {
                Picasso
                        .with(this)
                        .load(url)
                        .into(detailImageView);
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
