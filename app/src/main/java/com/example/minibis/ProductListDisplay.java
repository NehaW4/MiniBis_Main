package com.example.minibis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ProductListDisplay extends AppCompatActivity {

    TextView headline,subHeadline;
    RecyclerView recyclerView;
    Intent callingIntent;
    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list_display);

        callingIntent=getIntent();
        headline=(TextView) findViewById(R.id.productListHeadline);
        subHeadline=(TextView) findViewById(R.id.productListSubHeadline);

        recyclerView=(RecyclerView) findViewById(R.id.productListRecyclerView);

        int num=callingIntent.getIntExtra("category",-1);
        switch(num) {
            case 0:
                headline.setText("Western Clothing");
                query = "westernClothing";
                break;
            case 1:
                headline.setText("Ethnic Clothing");
                query = "ethnicClothing";
                break;
            case 2:
                headline.setText("Accessories");
                query = "accessories";
                break;
            case 3:
                headline.setText("SkinCare");
                query = "skinCare";
                break;
            case 4:
                headline.setText("Footwear");
                query = "footwear";
                break;
            case 5:
                headline.setText("Home-Decor");
                query = "homeDecor";
                break;
            case 6:
                headline.setText("Search Result");
                query = "search";
                break;
            default:
                finish();
                break;
        }
        subHeadline.setVisibility(View.VISIBLE);
        subHeadline.setText(query);
        if(callingIntent.getStringExtra("subHeading")!=null){
            subHeadline.setText(callingIntent.getStringExtra("subHeading"));
            subHeadline.setVisibility(View.VISIBLE);
        }

        if(num==6){
            //search Query
        }
        else{

        }


    }
}