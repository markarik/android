package com.example.ecommerceapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ecommerceapp.object_box.ObjectBox;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.objectbox.Box;

public class ProductActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 400;
    private static final int DEFAULT_POSITION =-2;
    private  static final  int REQUEST_CAMERA_PERMISSIONS = 67;
    private static final int GALLERY_REQUEST_CODE = 677;

    private EditText productName,productDescription,productPrice;
    private Button addProduct,emailbtn;
    private Spinner categorySpinner;
    private ImageView productImage;
    private Box<Product>mProductBox;
    private int mposition;
    private static  final String[] CAMERA_PERMISSION = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private Uri photoURI;
    private  File mPhotoFile;

    private void getIntentPosition(){
        mposition = getIntent().getIntExtra(ProductListAdapter.CURRENT_POSITION_VALUE,DEFAULT_POSITION);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        mProductBox = ObjectBox.get().boxFor(Product.class);
        productImage = (ImageView) findViewById(R.id.product_image);
        productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPermissionGranted()){
                    startCameraDialog();
                }else{
                    requestCameraPermissions();
                }

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


                sendEmail(ProductActivity.this);
            }
        });


        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isErrors()) {


                    Toast.makeText(ProductActivity.this, "Please ensure you fill all the fields before proceeding",Toast.LENGTH_SHORT).show();
                    return;
                }else{


                 Toast toast = Toast.makeText(ProductActivity.this,"item Has been  added",Toast.LENGTH_SHORT);

                   toast.setGravity(Gravity.TOP,0,100);
                 toast.show();

                    addProduct();
                }
            }
        });
        populateSpinner();

        getIntentPosition();

        fillData();

    }

    private void fillData() {

        if(mposition !=DEFAULT_POSITION)
        {
            Product product = ProductListActivity.mProductArrayList.get(mposition);
            productImage.setImageResource(product.getImage());
            productName.setText(product.getName());
            productPrice.setText(product.getPrice());
            productDescription.setText(product.getDescription());
        }
    }

    public void sendEmail(Context context) {
        String subject = "My subject";
        String text = "I noticed something";
        Intent i =new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc2822");
        i.putExtra(Intent.EXTRA_SUBJECT,subject);
        i.putExtra(Intent.EXTRA_TEXT,text);
        context.startActivity(i);
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
        if(resultCode == RESULT_OK/* && requestCode == REQUEST_CODE && data != null*/){

//            Bitmap b =(Bitmap)data.getExtras().get("data");

//            productImage.setImageBitmap(b);
            return;
        }
        else if ((requestCode == REQUEST_CODE)) {
            Uri uri =
                    FileProvider.getUriForFile(ProductActivity.this,
                            "com.example.ecommerceapp.fileprovider",
                            mPhotoFile);
            photoURI=uri;
            revokeUriPermission(uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            updatePhotoView();
        }else if(requestCode==GALLERY_REQUEST_CODE){
            Uri  photopath  =data.getData();
            photoURI=photopath;
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(this.getContentResolver(),photopath);
                updatePhotoView();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //for camera that saves to file
        else if ((requestCode == REQUEST_CODE)) {
            Uri uri =
                    FileProvider.getUriForFile(ProductActivity.this,
                            "e.anonymous.lesson02productactivity.fileprovider",
                            mPhotoFile);
            photoURI=uri;
            revokeUriPermission(uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            updatePhotoView();
        }

            }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch(itemId){
            case R.id.admin_email:{
                sendEmail(ProductActivity.this);
                break;
            }

            case R.id.action_next:{
         //next
                moveNext();

                break;
            }
            case R.id.action_previous:{
         //previous
                movePrevious();

                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void movePrevious() {

        mposition--;
        fillData();
        invalidateOptionsMenu();
    }

    private void moveNext() {
        mposition++;
        fillData();
        invalidateOptionsMenu();
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem item = menu.findItem(R.id.action_next);
        MenuItem previtem = menu.findItem(R.id.action_previous);
            int productPosition = ProductListActivity.mProductArrayList.size()-1;
           /* if(mposition< productPosition){
                item.setEnabled(true);
            }*/
            //item.setEnabled(mposition < productPosition);
            item.setVisible(mposition<productPosition);

            previtem.setVisible(mposition !=0);
          /* if(mposition != 0*//*&&mposition<productPosition*//*){
               previtem.setVisible(true);
           }else {
               previtem.setVisible(false);
           }*/
            //Log.d("position",productPosition+"");
        return super.onPrepareOptionsMenu(menu);
    }


    private void addProduct(){
        String name = productName.getText().toString();
        String price = productPrice.getText().toString();
        String description = productDescription.getText().toString();
        String category = categorySpinner.getSelectedItem().toString();


        ////insert into products model
        Product product = new Product(category,name,price,description,R.drawable.beat_headphones);

        //add product to objectbox
        mProductBox.put(product);

        finish();
    }

    private void startCameraDialog(){
        ImageView cameraImageView,galarreyImageView,closeDialogImageView;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(R.layout.view_camera);
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        View view= LayoutInflater.from(ProductActivity.this).inflate(R.layout.view_camera,null);
        cameraImageView=alertDialog.findViewById(R.id.dialog_camera);
        galarreyImageView=alertDialog.findViewById(R.id.dialog_gallarey);
        closeDialogImageView=alertDialog.findViewById(R.id.dialog_close);
        closeDialogImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        cameraImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePhotoToFilePathAndRetrieve();
                alertDialog.dismiss();
            }

        });
        galarreyImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFromGallarey();
                alertDialog.dismiss();
            }
        });
    }

    private  void showCamera(){
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

//startActivity(i);
        startActivityForResult(i,REQUEST_CODE);
    }
    private void requestCameraPermissions(){
        if(!isPermissionGranted() && Build.VERSION.SDK_INT>= Build.VERSION_CODES.M) {
            requestPermissions(CAMERA_PERMISSION,
                    REQUEST_CAMERA_PERMISSIONS);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSIONS: {
                if (isPermissionGranted()){
                    startCameraDialog();
                }
                else{
                    return;
                }
            }
            default:
                super.onRequestPermissionsResult(requestCode,
                        permissions, grantResults);
        }
    }
    private boolean isPermissionGranted() {
        int result=3;
        for (int i = 0; i < CAMERA_PERMISSION.length; i++) {
            result = ContextCompat
                    .checkSelfPermission(ProductActivity.this,
                            CAMERA_PERMISSION[i]);
            if(result!= PackageManager.PERMISSION_GRANTED){
                break;
            }
        }
        return result==PackageManager.PERMISSION_GRANTED;
    }
    public String getPhotoFilename() {
        return "IMG_" + new Random().nextDouble() + ".jpg";
    }
    public File getPhotoFile() {
        File filesDir = getFilesDir();
        return new File(filesDir,
                getPhotoFilename());
    }
    private void updatePhotoView() {
        if(photoURI!=null) {
            Glide.with(this).load(photoURI).into(productImage);
        }
    }
    public void savePhotoToFilePathAndRetrieve(){
        mPhotoFile=getPhotoFile();
        final Intent captureImage = new
                Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri =
                FileProvider.getUriForFile(ProductActivity.this,
                        "com.example.ecommerceapp.fileprovider",
                        mPhotoFile);
        captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        List<ResolveInfo> cameraActivities =
                ProductActivity.this
                        .getPackageManager().queryIntentActivities(captureImage,
                        PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo activity :
                cameraActivities) {
            ProductActivity.this.grantUriPermission(activity.activityInfo.packageName,
                    uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        startActivityForResult(captureImage,
                REQUEST_CODE);
    }
    private void loadFromGallarey() {
        Intent photointent=new Intent();
        photointent.setType("image/*");
        photointent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(photointent,"Choose a product Photo"),GALLERY_REQUEST_CODE);
    }
}
