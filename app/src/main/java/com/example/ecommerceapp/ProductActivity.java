package com.example.ecommerceapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 400;
    private EditText productName,productDescription,productPrice;
    private Button addProduct,emailbtn;
    private Spinner categorySpinner;
    private ImageView productImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        productImage = (ImageView) findViewById(R.id.product_image);
        productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

//startActivity(i);
                startActivityForResult(i,REQUEST_CODE);
            }
        });
        productName = (EditText) findViewById(R.id.product_name_edit);
        productDescription = (EditText) findViewById(R.id.product_description_edit);
        productPrice = (EditText)  findViewById(R.id.product_price_edit);
        categorySpinner = (Spinner) findViewById(R.id.category_spinner);
        addProduct = (Button) findViewById(R.id.add_product);

        emailbtn = (Button)findViewById(R.id.btn_email);

        emailbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                sendEmail();
            }
        });


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
        Intent i = getIntent();
        int position = i.getIntExtra(ProductListAdapter.CURRENT_POSITION_VALUE,-2);
        if(position!=-2) {
            Product product = ProductListActivity.mProductArrayList.get(position);
            productImage.setImageResource(product.getImage());
            productName.setText(product.getName());
            productPrice.setText(product.getPrice());
            productDescription.setText(product.getDescription());
        }

    }

    private void sendEmail() {
        String subject = "My subject";
        String text = "I noticed something";
        Intent i =new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc2822");
        i.putExtra(Intent.EXTRA_SUBJECT,subject);
        i.putExtra(Intent.EXTRA_TEXT,text);
        startActivity(i);
    }

    public boolean isErrors(){
        String name = productName.getText().toString();
        String price = productPrice.getText().toString();
        String description = productDescription.getText().toString();

        /*if(TextUtils.isEmpty(name)|| TextUtils.isEmpty(price)||TextUtils.isEmpty(description)){
            return  true;
        }
        else{
            return  false;
        }*/

        if(TextUtils.isEmpty(name)){
            productName.setError("Input Name");
            return  true;
        }


        if(TextUtils.isEmpty(price)){
            productPrice.setError("Input Price");
            return  true;
        }


        if(description.length()<6){
            productDescription.setError("Enter more than 6 characters");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE && data != null){

            Bitmap b =(Bitmap)data.getExtras().get("data");

            productImage.setImageBitmap(b);
        }
    }
}
