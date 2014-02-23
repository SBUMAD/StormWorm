package com.sbumad.stormworm.sprite;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.sbumad.stormworm.game.DataModel;
import com.sbumad.stormworm.game.MainActivity;

/**
 * Created by John on 2/22/14.
 */
public class Sprite {
    SpriteType spriteType;
    private float x;
    private float y;
    private float vx;
    private float vy;

    private static Paint p = new Paint();

    public float getX(){return this.x;}
    public float getY(){return this.y;}
    public SpriteType getSpriteType(){return this.spriteType;}

    public Sprite(SpriteType spriteType, float x, float y, float vx, float vy){
        this.spriteType = spriteType;
        this.x = DataModel.toAbsoluteWidth(x);
        this.y = DataModel.toAbsoluteHeight(y);
        this.vx = DataModel.toAbsoluteWidth(vx);
        this.vy = DataModel.toAbsoluteHeight(vy);
    }
    // this returns true if the sprite is off the game board
    public boolean update(){
        x += vx;
        y += vy;
        return DataModel.getDataModel().isOffBackground(this);
    }
    public void drawSelf(Canvas canvas){
        canvas.drawBitmap(spriteType.getImage(), DataModel.getDataModel().getTransX() + x, DataModel.getDataModel().getTransY() + y, p);
    }
}
