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

    private boolean movingToPoint;
    private float dx;
    private float dy;
    private float dv;

    long timePassed;
    long oldTime;

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
        movingToPoint = false;
        timePassed = 0;
        oldTime = System.currentTimeMillis();
    }
    // this returns true if the sprite is off the game board
    public boolean update(){
        if (timePassed == 0){
            timePassed = 1;
        } else {
            timePassed = System.currentTimeMillis() - oldTime;
        }
        if (movingToPoint == true){
            float hypot = (float)Math.sqrt(Math.pow((dx - x)*100.0f / (float)DataModel.getScreenWidth(), 2.0) + Math.pow((dy - y)*100.0f / (float)DataModel.getScreenHeight(), 2.0));
            float time = hypot / dv;
            if (time < timePassed/1000.0f){
                time = timePassed;
                if (this instanceof Player){
                    ((Player)this).setCity(((Player)this).getDestCity());
                }
                movingToPoint = false;
            }
            vx = (dx - x)  / time;
            vy = (dy - y) / time;
        }
        x += vx * ((float)timePassed/1000.0f);
        y += vy * ((float)timePassed/1000.0f);

        oldTime = System.currentTimeMillis();

        return DataModel.getDataModel().isOffBackground(this);
    }
    public void drawSelf(Canvas canvas){
        if (getSpriteType().getId().startsWith("player") || getSpriteType().getId().startsWith("bot")){
            x -= getSpriteType().getWidth()/2.8;
            y -= getSpriteType().getHeight()/1.7;
        }
        canvas.drawBitmap(spriteType.getImage(), DataModel.getDataModel().getTransX() + x, DataModel.getDataModel().getTransY() + y, p);
        if (getSpriteType().getId().startsWith("player") || getSpriteType().getId().startsWith("bot")){
            x += getSpriteType().getWidth()/2.8;
            y += getSpriteType().getHeight()/1.7;
        }
    }
    public void moveToPoint(float dx, float dy){
        movingToPoint = true;
        this.dx = dx;
        this.dy = dy;
        dv = 30;
    }
}
