package com.sbumad.stormworm.gui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


import com.sbumad.stormworm.R;
import com.sbumad.stormworm.game.DataModel;
import com.sbumad.stormworm.game.MainActivity;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by John on one/21/14.
 */
public class Menu {
    //Generic
    private Bitmap pixel;
    private Bitmap title;
    private int screenWidth;
    private int screenHeight;
    private Paint paint;
    private boolean alive;
    private ButtonManager buttonManager;
    private static Thread spriteThread;

    public Menu(int screenWidth, int screenHeight){
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        pixel = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_4444);
        paint = new Paint();
        title = BitmapFactory.decodeResource(MainActivity.getMain().getResources(), R.drawable.title);
        title = Bitmap.createScaledBitmap(title, screenWidth, screenHeight * 20 / 100, true);
        buttonManager = new ButtonManager(title.getHeight());
    }
    public void addButton(String text){
        buttonManager.addButton(text);
    }
    public void turnOn(ArrayList<String> buttons){
        MainActivity.getMain().getScreen().setOnTouchListener(buttonManager);
        alive = true;
        buttonManager.removeAllButtons();
        for (String s : buttons){
           buttonManager.addButton(s);
        }
    }
    public void turnOff(){
        alive = false;
    }

    // Draws the menu
    public void drawGUI(Canvas c){
        c.drawBitmap(title, 0, 0, new Paint());
        buttonManager.drawButtons(c);
    }
}
