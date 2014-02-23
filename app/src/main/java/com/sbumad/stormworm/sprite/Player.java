package com.sbumad.stormworm.sprite;

/**
 * Created by John on 2/22/14.
 */
public class Player extends Sprite {
    private Sprite city;
    public void setCity(Sprite city){this.city = city;}
    public Sprite getCity(){return this.city;}
    public Player(SpriteType spriteType, float x, float y, float vx, float vy, Sprite city) {
        super(spriteType, x, y, vx, vy);
        this.city = city;
    }

}
