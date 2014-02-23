package com.sbumad.stormworm.gui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import com.sbumad.stormworm.R;
import com.sbumad.stormworm.game.DataModel;
import com.sbumad.stormworm.game.DrawView;
import com.sbumad.stormworm.game.MainActivity;

import java.util.ArrayList;

/**
 * Created by John on one/26/14.
 */
public class ButtonManager implements View.OnTouchListener{
    private ArrayList<Button> buttons;
    private Bitmap picture;
    private int buttonWidth;
    private int buttonHeight;
    private int titleHeight;

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() != MotionEvent.ACTION_DOWN){
            return true;
        }
        for (Button b : buttons){
            if (motionEvent.getX() >= b.x && motionEvent.getX() <= b.x + b.width && motionEvent.getY() >= b.y && motionEvent.getY() <= b.y + b.height){
                if (b.text.toLowerCase().equals("play")){
                    MainActivity.getMain().getSpriteManager().setMenuOn(false);
                    MainActivity.getMain().getScreen().setOnTouchListener(DataModel.getDataModel().getOnTouchListener());
                } else if (b.text.toLowerCase().equals("exit")){
                    System.exit(0);
                } else if (b.text.toLowerCase().equals("resume")){
                    //MainActivity.getMain().resumeGame();
                }
                return true;
            }
        }
        return false;
    }
    public ButtonManager(int titleHeight){
        this.titleHeight = titleHeight;
        buttons = new ArrayList<Button>();
        picture = null;
    }
    public void removeAllButtons(){
        buttons = new ArrayList<Button>();
    }
    public void addButton(String text){
        if (picture == null){
            buttonWidth = DataModel.getScreenWidth() * 40 / 100;
            buttonHeight = DataModel.getScreenHeight() * 30 / 100;
            picture = BitmapFactory.decodeResource(MainActivity.getMain().getResources(), R.drawable.button);
        }
        Button b = new Button(text);

        b.picture = this.picture.copy(Bitmap.Config.ARGB_4444, true);
        Canvas c = new Canvas(b.picture);
        Paint p = new Paint();
        p.setTextSize(200);
        Bitmap sticker = getTextBitmap(text);
        c.drawBitmap(sticker, (picture.getWidth() - sticker.getWidth()) / 2 , (picture.getHeight() - sticker.getHeight()) / 2, p);
        b.picture = Bitmap.createScaledBitmap(b.picture, buttonWidth, buttonHeight, true);
        buttons.add(b);
        b.width = b.picture.getWidth();
        b.height = b.picture.getHeight();
        organizeButtons();
    }
    // This centers the buttons
    private void organizeButtons(){
        int space = DataModel.getScreenHeight() - titleHeight - (buttons.size() * buttonHeight);
        space /= (buttons.size() + 1);
        for (int i = 0; i < buttons.size(); i++){
            buttons.get(i).x = (DataModel.getScreenWidth() - buttonWidth) / 2;
            buttons.get(i).y = titleHeight + ((i+1) * space) + (i * buttonHeight);
        }
    }
    public void drawButtons(Canvas c){
        int space = DataModel.getScreenHeight() - titleHeight - (buttons.size() * buttonHeight);
        space /= (buttons.size() + 1);
        for (int i = 0; i < buttons.size(); i++){
            c.drawBitmap(buttons.get(i).picture, buttons.get(i).x, buttons.get(i).y, new Paint());
        }
    }
    // This method generates a bitmap with the given text centered.  It is wildly inefficient memory wize
    public Bitmap getTextBitmap(String text){
        int size = 1000;
        Bitmap start = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888).copy(Bitmap.Config.ARGB_4444, true);
        Canvas c = new Canvas(start);
        Paint p = new Paint();
        p.setTextSize(200);
        c.drawText(text, 50, 200, p);
        int left = 0;
        int top = 0;
        int right = size-1;
        int bottom = size-1;
        int[] pixels = new int[size * size];
        start.getPixels(pixels, 0, size, 0, 0, size, size);
        boolean flag = false;
        int i;
        for (i = 0; i < pixels.length && flag == false; i++){
            if (pixels[i] == Color.BLACK){
                flag = true;
            }
        }
        top = i / size;
        flag = false;
        for (i = pixels.length - 1; i >= 0 && flag == false; i--){
            if (pixels[i] == Color.BLACK){
                flag = true;
            }
        }
        bottom = i / size;
        flag = false;
        i = 0;
        int j = 0;
        while (flag == false){
            if (pixels[j + i] == Color.BLACK){
                flag = true;
            }
            i += size;
            if (i > pixels.length - 1){
                i = 0;
                j++;
                if (j == size){
                    flag = true;
                }
            }
        }
        left = j;
        i = 0;
        j = size-1;
        flag = false;
        while (flag == false){
            if (pixels[j + i] == Color.BLACK){
                flag = true;
            }
            i += size;
            if (i > pixels.length - 1){
                i = 0;
                j--;
                if (j == -1){
                    flag = true;
                }
            }
        }
        right = j;
        return Bitmap.createBitmap(start, left, top, right-left, bottom-top);
    }
}
