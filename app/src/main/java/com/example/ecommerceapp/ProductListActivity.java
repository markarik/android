package com.example.ecommerceapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity {

    public static ArrayList<Product> mProductArrayList = new ArrayList<>();

    RecyclerView mRecyclerView;

    private String[] productNames = {
            "Hover board v-4 s",
            "Natural light googles",
            "Black Leather Wallet",
            "Nike supra 3",
            "Beat wireless earphones",
            "Khaki handbags",
            "Brown Rubber"
    };
    private String[] productPrices={
            "17000",
            "800",
            "1500",
            "3500",
            "1500",
            "1200",
            "2200"
    };
    private String[] productStrikedPrice={
      "20000",
      "1000",
      "2000",
      "4000",
      "2000",
      "1500",
      "2500"
    };
    private int[] productImages = {
            R.drawable.hover_board,
            R.drawable.googles,
            R.drawable.wallet,
            R.drawable.nike,
            R.drawable.beat_headphones,
            R.drawable.h_bag,
            R.drawable.rubber_shoe
    };

    private String[] productDescription = {
            "Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum",
            "sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum",
            " containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum",
            " Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum",
            " Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum",
            " passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum",
            " and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum",


    };

    ProductListAdapter mProductListAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        mRecyclerView=(RecyclerView) findViewById(R.id.product_list_recyclerView);
        mProductListAdapter=new ProductListAdapter(ProductListActivity.this,mProductArrayList);

        populateRecyclerView();
        mRecyclerView.setAdapter(mProductListAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(ProductListActivity.this,2));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ProductListActivity.this,ProductActivity.class);
                startActivity(i);
            }
        });
    }
    public  void populateRecyclerView(){
        mProductArrayList.clear();
        for(int index =0;index<productImages.length;index++){
            Product product=new Product();
            product.setName(productNames[index]);
            product.setPrice(productPrices[index]);
            product.setImage(productImages[index]);
            product.setStrikedPrice(productStrikedPrice[index]);
            product.setDescription(productDescription[index]);
            mProductArrayList.add(product);
        }
        mProductListAdapter.notifyDataSetChanged();
    }


}
