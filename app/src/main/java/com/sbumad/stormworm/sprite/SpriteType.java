package com.sbumad.stormworm.sprite;

import android.graphics.Bitmap;

/**
 * Created by John on 2/22/14.
 */
public class SpriteType {
    private String id;
    private float statesPerSecond;
    // Bitmap stuff
    private Bitmap[] images;
    private int states;

    public SpriteType(String id, Bitmap image, int states, float statesPerSecond){
        this.id = id;
        this.states = states;
        this.statesPerSecond = statesPerSecond;
        
    }
}
