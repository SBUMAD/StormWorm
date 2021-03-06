package com.sbumad.stormworm.sprite;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
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
    private boolean menuOn;

    public void setMenuOn(boolean menuOn){this.menuOn = menuOn;}

    // leave a dimension 0 to maintain aspect ratio on a galaxy s4
    public SpriteManager(Bitmap background, int widthPercent, int heightPercent){
        this.background = background;
        toRemoveRoads = new ArrayList<Road>();
        roads = new ArrayList<Road>();
        players = new ArrayList<Player>();
        spriteTypes = new HashMap<String, SpriteType>();
        sprites = new ArrayList<Sprite>();
        toRemove = new ArrayList<Sprite>();
        menuOn = true;
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
    public Player initPlayer(Bitmap image, boolean bot, float widthPercent, float heightPercent){
        Sprite city = null;
        Collections.shuffle(sprites);
        // keep the players in the back of the list
        ArrayList<Player> tempPs = new ArrayList<Player>();
        for (Sprite s : sprites){
            if (s instanceof  Player){
                tempPs.add((Player)s);
            }
        }
        for (Player p : tempPs){
            sprites.remove(p);
            sprites.add(sprites.size() - 1, p);
        }

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
        String id;
        if (bot){
            id = String.format("bot%d", players.size());
        } else {
            id = String.format("player%d", players.size());
        }
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

        if (menuOn){
            DataModel.getDataModel().getMenu().drawGUI(canvas);
            return;
        }

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
        // update bots
        for (Player p : players){
            if (p.getSpriteType().getId().startsWith("bot")){
                if (p.getCity() == p.getDestCity()){
                    Collections.shuffle(roads);
                    float cityX = 0.0f;
                    float cityY = 0.0f;
                    for (Road r : roads){
                        if (r.s1 == p.getCity()){
                            movePlayerToCity(p, r.s2);
                            cityX = r.s2.getX();
                            cityY = r.s2.getY();
                        } else if (r.s2 == p.getCity()){
                            movePlayerToCity(p, r.s1);
                            cityX = r.s1.getX();
                            cityY = r.s1.getY();
                        }
                        p.setCurrentRotation(((float)Math.PI/2) + (float)Math.atan((p.getDestCity().getY() - p.getY()) / (p.getDestCity().getX() - p.getX())));
                       float degrees = (float)Math.toDegrees(p.getCurrentRotation());
                        if (degrees >= 0 && degrees < 90){

                        }
                        if (degrees >= 90 && degrees < 180){
                            p.setCurrentRotation(p.getCurrentRotation() + (float)Math.PI);
                        }
                        if (degrees >= 180 && degrees < 270){
                            p.setCurrentRotation(p.getCurrentRotation() + (float)Math.PI);
                        }
                        if (degrees >= 270 && degrees < 360){
                            p.setCurrentRotation(p.getCurrentRotation() + (float)Math.PI);
                        }
                        if (cityY > p.getY()){
                            p.setCurrentRotation(p.getCurrentRotation() + (float)Math.PI);
                        }
                       //p.setCurrentRotation((float)Math.atan((cityX - p.getX()) / (cityY - p.getY())));
                       // p.setCurrentRotation((float)Math.atan2(p.getDestCity().getX() - p.getX(), p.getDestCity().getY() - p.getY()));
                        //p.setCurrentRotation((float)((p.getDestCity().getX() * p.getX() + p.getDestCity().getY() * p.getY())/(Math.sqrt((p.getDestCity().getX() * p.getDestCity().getX()) +(p.getDestCity().getY() * p.getDestCity().getY())) * Math.sqrt((p.getX() * p.getX()) + (p.getY() * p.getY())))));
                    }
                }
            }
        }
        toRemove.clear();
        toRemoveRoads.clear();
    }
    public void movePlayerToCity(Player p, Sprite city){
        p.moveToPoint(city.getX(), city.getY());
        p.setDestCity(city);
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
                            if (players.get(0).getCity() == players.get(0).getDestCity()){
                                // Moving from point, ok to go
                                flag = true;
                            }else{

                            }
                        }
                        // Already in motion, more checks necessary
                        if (players.get(0).getCity() == s){
                            flag = true;
                        }
                    }
                    if (flag){
                        movePlayerToCity(players.get(0), s);
                        players.get(0).setDestCity(s);
                    }
                }
            }
        }
    }
}
