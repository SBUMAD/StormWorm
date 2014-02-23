package com.sbumad.stormworm.sprite;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.sbumad.stormworm.game.DataModel;
import com.sbumad.stormworm.game.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by John on 2/22/14.
 */
public class SpriteManager {
    private HashMap<String, SpriteType> spriteTypes;
    private ArrayList<Sprite> sprites;
    private ArrayList<Sprite> toRemove;
    private Bitmap background;
    private Paint p;

    // leave a dimension 0 to maintain aspect ratio on a galaxy s4
    public SpriteManager(Bitmap background, int widthPercent, int heightPercent){
        this.background = background;
        spriteTypes = new HashMap<String, SpriteType>();
        sprites = new ArrayList<Sprite>();
        toRemove = new ArrayList<Sprite>();
        if (widthPercent == 0){
            widthPercent = (int)(((float)background.getWidth() / (float)background.getHeight()) * (1080.0f / 1920.0f) * heightPercent + .5f);
        }
        if (heightPercent == 0){
            heightPercent = (int)(((float)background.getHeight() / (float)background.getWidth()) * (1920.0f / 1080.0f) * widthPercent + .5f);
        }
        this.background = Bitmap.createScaledBitmap(background, (int)((float)MainActivity.getScreenWidth() * ((float)widthPercent / 100.0f)), (int)((float)MainActivity.getScreenHeight() * ((float)heightPercent / 100.0f)), true);
        DataModel.getDataModel().setBackground(this.background);
        p = new Paint();
    }
    // leave a dimension 0 to maintain aspect ratio on a galaxy s4
    public void initSpriteType(String id, Bitmap image, float widthPercent, float heightPercent){
        spriteTypes.put(id, new SpriteType(id, image, widthPercent, heightPercent));
    }
    public Sprite addSprite(String id, float x, float y, float vX, float vY){
        Sprite s = new Sprite(spriteTypes.get(id), x, y, vX, vY);
        sprites.add(s);
        return s;
    }
    public void update(Canvas canvas){
        canvas.drawBitmap(background, DataModel.getDataModel().getTransX(), DataModel.getDataModel().getTransY(), p);

        for (Sprite s : sprites){
            if (s.update()){
                toRemove.add(s);
            }
            s.drawSelf(canvas);
        }
        for (Sprite s : toRemove){
            sprites.remove(s);
        }
    }
}
