package com.sbumad.stormworm.sprite;

/**
 * Created by John on 2/22/14.
 */
public class Sprite {
    SpriteType spriteType;
    private float x;
    private float y;
    private float vx;
    private float vy;
    public Sprite(float x, float y, float vx, float vy){
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;

    }
}
