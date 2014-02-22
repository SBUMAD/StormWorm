package com.sbumad.stormworm.sprite;

import android.graphics.Bitmap;

import com.sbumad.stormworm.game.DataModel;
import com.sbumad.stormworm.game.MainActivity;

/**
 * Created by John on 2/22/14.
 */
public class SpriteType {
    private String id;
    private Bitmap image;

    // leave a dimension blank to maintain aspect ratio on a galaxy s4
    public SpriteType(String id, Bitmap image, float widthPercent, float heightPercent){
        this.id = id;
        this.image = image;
        if (widthPercent == 0){
            widthPercent = (int)(((float)image.getWidth() / (float)image.getHeight()) * (1080.0f / 1920.0f) * heightPercent + .5f);
        }
        if (heightPercent == 0){
            heightPercent = (int)(((float)image.getHeight() / (float)image.getWidth()) * (1920.0f / 1080.0f) * widthPercent + .5f);
        }
        this.image = Bitmap.createScaledBitmap(this.image, (int)((float)MainActivity.getScreenWidth() * ((float)widthPercent / 100.0f)), (int)((float)MainActivity.getScreenHeight() * ((float)heightPercent / 100.0f)), true);
    }
    public String getId(){return this.id;}
    public Bitmap getImage(){return this.image;}
    public int getWidth(){
        return image.getWidth();
    }
    public int getHeight(){
        return image.getHeight();
    }
    public int getRelativeWidth(){
        return (int)(((float)image.getWidth() / (float) DataModel.getScreenWidth()) * 100.0f);
    }
    public int getRelativeHeight(){
        return (int)(((float)image.getHeight() / (float)DataModel.getScreenHeight()) * 100.0f);
    }
}
