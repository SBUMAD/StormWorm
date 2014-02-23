package com.sbumad.stormworm.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.sbumad.stormworm.sprite.Sprite;

/**
 * Created by John on 2/22/14.
 */
public class DataModel {
    public static DataModel dataModel = null;
    private Bitmap background;
    private static int screenWidth;
    private static int screenHeight;

    private float transX;
    private float transY;

    public Bitmap getBackground() {return background;}
    public void setBackground(Bitmap background) {this.background = background;}
    public static int getScreenWidth() {return screenWidth;}
    public static void setScreenWidth(int screenWidth) {screenWidth = screenWidth;}
    public static int getScreenHeight() {return screenHeight;}
    public static void setScreenHeight(int screenHeight) {screenHeight = screenHeight;}
    public float getTransX(){return this.transX;}
    public float getTransY(){return this.transY;}
    public void setTransX(float transX){this.transX = transX;}
    public void setTransY(float transY){this.transY = transY;}

    public static DataModel getDataModel(){
        if (dataModel == null){
            dataModel = new DataModel();
        }
        return dataModel;
    }
    private DataModel(){
        transX = 0.0f;
        transY = 0.0f;
    }

    public static float toRelativeWidth(float x){
        return (x / (float)screenWidth) * 100.0f;
    }
    public static float toRelativeHeight(float y){
        return (y / (float)screenHeight) * 100.0f;
    }
    public static float toAbsoluteWidth(float x){
        return (x * (float)screenWidth / 100.0f);
    }
    public static float toAbsoluteHeight(float y){
        return (y * (float)screenHeight / 100.0f);
    }
    public static Bitmap convertImage565(Bitmap image){
        Bitmap newB = Bitmap.createBitmap(image.getWidth(), image.getHeight(), Bitmap.Config.RGB_565);
        Canvas c = new Canvas(newB);
        c.drawBitmap(image, 0, 0, new Paint());
        return newB;
    }
    public static Bitmap convertImage4444(Bitmap image){
        Bitmap newB = Bitmap.createBitmap(image.getWidth(), image.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas c = new Canvas(newB);
        c.drawBitmap(image, 0, 0, new Paint());
        return newB;
    }
    public boolean isOffBackground(Sprite s){
        if (s.getX() > transX + background.getWidth() || s.getX() + s.getSpriteType().getWidth() < transX || s.getY() > transY + background.getHeight() || s.getY() + s.getSpriteType().getHeight() < transY){
            return true;
        }
        return false;
    }
}