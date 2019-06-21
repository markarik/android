package com.example.ecommerceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {
    EditText productName,productDescription,productPrice;
    Button addProduct;
    Spinner categorySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);


        productName = (EditText) findViewById(R.id.product_name_edit);
        productDescription = (EditText) findViewById(R.id.product_description_edit);
        productPrice = (EditText)  findViewById(R.id.product_price_edit);
        categorySpinner = (Spinner) findViewById(R.id.category_spinner);
        addProduct = (Button) findViewById(R.id.add_product);

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isErrors()) {


                    Toast.makeText(ProductActivity.this, "Please ensure you fill all the fields before proceeding",Toast.LENGTH_SHORT).show();
               return;
                }else{
                    Toast.makeText(ProductActivity.this,"item will be added",Toast.LENGTH_SHORT).show();
                }
            }
        });
        populateSpinner();
    }

    public boolean isErrors(){
        String name = productName.getText().toString();
        String price = productPrice.getText().toString();
        String description = productDescription.getText().toString();

        if(TextUtils.isEmpty(name)|| TextUtils.isEmpty(price)||TextUtils.isEmpty(description)){
            return  true;
        }
        else{
            return  false;
        }
    }

    public void  populateSpinner(){
//
        List<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("Electronics");
        spinnerArray.add("Beauty Products");
        spinnerArray.add("Kitchen Products");
        spinnerArray.add("Kids Ware");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,android.R.layout.simple_spinner_item,spinnerArray
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categorySpinner.setAdapter(adapter);
    }

}
