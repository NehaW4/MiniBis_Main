package com.example.minibis;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class ImageStringOperation {
    public static String getString(Bitmap b){
        if(b==null){
            return null;
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        String result = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return result;
    }
    public static Bitmap getImage(String s){
        try{
            byte [] encodeByte=Base64.decode(s,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }
}
