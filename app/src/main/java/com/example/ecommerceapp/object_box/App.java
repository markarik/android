package com.example.ecommerceapp.object_box;

import android.app.Application;

public  class App extends Application{
    public static final String TAG = "ObjectBoxExample";

    @Override
    public void onCreate() {

        // Optional: if you distribute your app as App Bundle, provides detection of incomplete
        // installs due to sideloading and helps users reinstall the app from Google Play.
        // https://docs.objectbox.io/android/app-bundle-and-split-apk
        super.onCreate();

        ObjectBox.init(this);
    }
}
