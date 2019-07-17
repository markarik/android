package com.example.ecommerceapp.object_box;

import android.content.Context;
import android.util.Log;

import com.example.ecommerceapp.BuildConfig;
import com.example.ecommerceapp.MyObjectBox;

import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;

public class ObjectBox {
    private static BoxStore sBoxStore;
    static void init(Context context){
     sBoxStore= MyObjectBox.builder()
             .androidContext(context.getApplicationContext())
             .build();


     if (BuildConfig.DEBUG){
         Log.d(App.TAG,String.format("Using ObjectsBox %s (%s)",
                 BoxStore.getVersion(),BoxStore.getVersionNative()));

         new AndroidObjectBrowser(sBoxStore).start(context.getApplicationContext());
     }
    }

    public static BoxStore get(){
        return sBoxStore;
    }
}
