package com.hession.podge.photoimport;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.io.IOException;
import java.security.Permission;

public class MainActivity extends AppCompatActivity {

    //create a Constant variable to hold the result of the Permission the user chose
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //int permissionCheck = ContextCompat.checkSelfPermission(MainActivity, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            //should I show an explanation to the user?


        } else {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }

        //change the intent of the app
        //Intent.ACTION_PICK indicates that I'm gonna pick media and
        //MediaStore.Images.Media.EXTERNAL_CONTENT_URI indicates the place I want to get and media from (MediaStore) and the type of media (Images)
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //later, when I type code for a response I'm gonna look for a returned value of 1
        startActivityForResult(i, 1);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

//to do something with the selected image, use the onActivityResult method
//this method gets called whenever I go to an external activity i.e. the Image Picker Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //requestCode is the code that was sent when the activity was initiated. i.e. 1
        //RESULT_OK means the user selected an image and didn't cancel the selection
        //data is the image
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {


            //Uri is the location of the selected image
            Uri selectedImage = data.getData();


            //try and catch is used to ensure the image is a valid image
            try {

                //convert the image to a bitmap
                //What i'm saying here is: "Take my image and make a bitmap from it"
                Bitmap bitmapImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);

                //get the image view
                ImageView imageView = (ImageView)findViewById(R.id.imageViewId);
                //set the image bitmap
                imageView.setImageBitmap(bitmapImage);



            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

    //override the onRequestPermissionsResult method


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                //if request is cancelled the result arrays are empty
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //permission was granted
                    //do the photo import task you would like to do

                } else {


                    //permission denied
                    //disable the functionality that depends on the permission

                }

                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
