package com.sbumad.stormworm.sprite;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.sbumad.stormworm.game.DataModel;
import com.sbumad.stormworm.game.MainActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by John on 2/22/14.
 */
public class SpriteManager {
    private HashMap<String, SpriteType> spriteTypes;
    private ArrayList<Sprite> sprites;
    private ArrayList<Player> players;
    private ArrayList<Sprite> toRemove;
    private Bitmap background;
    private Paint p;
    private ArrayList<Road> roads;
    private ArrayList<Road> toRemoveRoads;

    // leave a dimension 0 to maintain aspect ratio on a galaxy s4
    public SpriteManager(Bitmap background, int widthPercent, int heightPercent){
        this.background = background;
        toRemoveRoads = new ArrayList<Road>();
        roads = new ArrayList<Road>();
        players = new ArrayList<Player>();
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
    public Player initPlayer(Bitmap image, float widthPercent, float heightPercent){
        Sprite city = null;
        Collections.shuffle(sprites);
        for (Sprite s : sprites){
            if (s.getSpriteType().getId() == "city"){
                boolean flag = false;
                for (Player p : players){
                    if (p.getCity() == s){
                        flag = true;
                    }
                }
                if (flag == false){
                    city = s;
                }
            }
        }
        String id = String.format("player%d", players.size());
        spriteTypes.put(id,new SpriteType(id, image, widthPercent, heightPercent));
        Player p = new Player(spriteTypes.get(id), DataModel.toRelativeWidth(city.getX()), DataModel.toRelativeHeight(city.getY()), 0, 0, city);
        sprites.add(p);
        players.add(p);
        return p;
    }
    public Sprite addSprite(String id, float x, float y, float vX, float vY){
        Sprite s = new Sprite(spriteTypes.get(id), x, y, vX, vY);
        sprites.add(s);
        return s;
    }
    public void connectSprites(Sprite s1, Sprite s2){
        roads.add(new Road(s1, s2));
    }
    public void update(Canvas canvas){
        canvas.drawBitmap(background, DataModel.getDataModel().getTransX(), DataModel.getDataModel().getTransY(), p);

        for (Road r : roads){
            p.setColor(Color.YELLOW);
            p.setStrokeWidth(8.0f);
            canvas.drawLine((r.s1.getSpriteType().getWidth()/2) + r.s1.getX() + DataModel.getDataModel().getTransX(), (r.s1.getSpriteType().getHeight() / 2 ) + r.s1.getY() + DataModel.getDataModel().getTransY(), (r.s2.getSpriteType().getWidth()/2) + r.s2.getX() + DataModel.getDataModel().getTransX(), (r.s2.getSpriteType().getHeight() / 2) + r.s2.getY() + DataModel.getDataModel().getTransY(), p);
        }

        for (Sprite s : sprites){
            if (s.update()){
                toRemove.add(s);
            }
            s.drawSelf(canvas);
        }
        for (Sprite s : toRemove){
            sprites.remove(s);
            for (Road r : roads){
                if (s == r.s1 || s == r.s2 ){
                    toRemoveRoads.add(r);
                }
            }
        }
        for (Road r : toRemoveRoads){
            roads.remove(r);
        }
        toRemove.clear();
        toRemoveRoads.clear();
    }
    public void movePlayerToCity(Player p, Sprite city){
        if (p.getCity() == city){
            return;
        }
        p.moveToPoint(city.getX(), city.getY());
    }
    public void movePlayerIfCity(float x, float y){
        x = DataModel.getDataModel().toUnscaledX(x);
        y = DataModel.getDataModel().toUnscaledY(y);
        for (Sprite s : sprites){
            if (s.getSpriteType().getId().equals("city")){
                if (x >= s.getX() && x <= s.getX() + s.getSpriteType().getWidth() && y >= s.getY() && y <= s.getY() + s.getSpriteType().getHeight()){
                    boolean flag = false;
                    for (Road r : roads){
                        if ((r.s1 == players.get(0).getCity() && r.s2 == s) || (r.s1 == s && r.s2 == players.get(0).getCity())){
                            flag = true;
                        }
                    }
                    if (flag){
                        movePlayerToCity(players.get(0), s);
                        players.get(0).setCity(s);
                    }
                }
            }
        }
    }
}
