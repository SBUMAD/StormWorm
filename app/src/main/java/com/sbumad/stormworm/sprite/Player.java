package com.sbumad.stormworm.sprite;

import android.graphics.Matrix;

/**
 * Created by John on 2/22/14.
 */
public class Player extends Sprite {
    private Sprite city;
    private Sprite destCity;
    private float currentRotation;

    public void setCity(Sprite city){this.city = city;}
    public Sprite getCity(){return this.city;}
    public void setDestCity(Sprite destCity){this.destCity = destCity;}
    public Sprite getDestCity(){return this.destCity;}
    public void setCurrentRotation(float currentRotation){this.currentRotation = currentRotation;}
    public float getCurrentRotation(){return  this.currentRotation;}


    public Player(SpriteType spriteType, float x, float y, float vx, float vy, Sprite city) {
        super(spriteType, x, y, vx, vy);
        this.city = city;
        this.destCity = city;
        currentRotation = 0.0f;
    }

}
