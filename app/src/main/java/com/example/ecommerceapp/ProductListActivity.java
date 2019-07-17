package com.example.ecommerceapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.ecommerceapp.object_box.ObjectBox;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.query.Query;

public class ProductListActivity extends AppCompatActivity {

    public static ArrayList<Product> mProductArrayList = new ArrayList<>();

    RecyclerView mRecyclerView;

    //variables

    private Box<Product>mProductBox;
    private Query<Product>mProductQuery;


    static int position =78;

   /* private String[] productNames = {
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


    };*/

    ProductListAdapter mProductListAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        if(savedInstanceState!=null){
            position = savedInstanceState.getInt("Position");
        }


        mRecyclerView=(RecyclerView) findViewById(R.id.product_list_recyclerView);
        mProductListAdapter=new ProductListAdapter(ProductListActivity.this,mProductArrayList);

//        populateRecyclerView();
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
        Log.d("start","Acticity onCreate and position variable="+position);

        initObjectBox();
    }
   /* public  void populateRecyclerView(){
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
    }*/

   /* @Override
    public void onBackPressed() {
        Toast.makeText(this, "Do you want to exit", Toast.LENGTH_SHORT).show();
    }
*/


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Start","Activity onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("start","Activity onResume");

        updateProucts();
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d("Start","Activity onStop");

    }

    @Override
    protected void onPause() {
        super.onPause();
        position = 80;
        Log.d("Start","Activity onPause and position ="+position);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Start","Activity onDestroy");

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Position",position);
    }

  /*  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemID = item.getItemId();
        switch (itemID){
            case R.id.options_logout:{
               new ProductActivity().sendEmail(ProductListActivity.this);
                break;
            }

        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }*/

  public  void  initObjectBox(){
      mProductBox = ObjectBox.get().boxFor(Product.class);
  }


  public void  updateProucts(){
 mProductArrayList.clear();
      mProductQuery = mProductBox.query().order(Product_.__ID_PROPERTY).build();
      List<Product>products = mProductQuery.find();
      mProductArrayList.addAll(products);
      mProductListAdapter.notifyDataSetChanged();
  }
}
