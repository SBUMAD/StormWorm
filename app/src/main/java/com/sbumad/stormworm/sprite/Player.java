package com.sbumad.stormworm.sprite;

/**
 * Created by John on 2/22/14.
 */
public class Player extends Sprite {
    private Sprite city;
    private Sprite destCity;
    public void setCity(Sprite city){this.city = city;}
    public Sprite getCity(){return this.city;}
    public void setDestCity(Sprite destCity){this.destCity = destCity;}
    public Sprite getDestCity(){return this.destCity;}
    public Player(SpriteType spriteType, float x, float y, float vx, float vy, Sprite city) {
        super(spriteType, x, y, vx, vy);
        this.city = city;
        this.destCity = city;
    }

}
