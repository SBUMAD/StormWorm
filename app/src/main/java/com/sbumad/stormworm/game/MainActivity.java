package com.sbumad.stormworm.game;

import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Build;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Display;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.sbumad.stormworm.R;
import com.sbumad.stormworm.sprite.Sprite;
import com.sbumad.stormworm.sprite.SpriteManager;



public class MainActivity extends ActionBarActivity {

    public static MainActivity main;
    private SpriteManager spriteManager;
    private static int screenWidth;
    private static int screenHeight;
    private GestureDetectorCompat gestureDetector;
    private float currentScale;

    public SpriteManager getSpriteManager(){return spriteManager;}
    public static MainActivity getMain(){
        return main;
    }

    public static int getScreenWidth(){return screenWidth;}
    public static int getScreenHeight(){return screenHeight;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataModel.getDataModel();
        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // set the singleton
        main = this;
        // Find screen size
        Point size = new Point();
        WindowManager w = getWindowManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2){
            w.getDefaultDisplay().getSize(size);
            screenWidth = size.x;
            screenHeight = size.y;
        }else{
            Display d = w.getDefaultDisplay();
            screenWidth = d.getWidth();
            screenHeight = d.getHeight();
        }
        DataModel.setScreenWidth(screenWidth);
        DataModel.setScreenHeight(screenHeight);
        // Make a new Screen
        DrawView screen = new DrawView(this);
        // create the sprite manager
        spriteManager = new SpriteManager(BitmapFactory.decodeResource(getResources(), R.drawable.background),100, 100);
        // set the screen as the current display
        setContentView(screen);

        // Set up the sprites
        initSprites();


    }
    private void initSprites(){
        spriteManager.initSpriteType("city", BitmapFactory.decodeResource(getResources(), R.drawable.citysprite), 3, 0);
        Sprite newYork = spriteManager.addSprite("city", 25, 34, 0, 0);
        Sprite sanFrancisco = spriteManager.addSprite("city", 12, 40, 0, 0);
        Sprite mexicoCity = spriteManager.addSprite("city", 18, 48, 0, 0);
        Sprite cairo = spriteManager.addSprite("city", 55, 44, 0, 0);
        Sprite london = spriteManager.addSprite("city", 46, 28, 0, 0);
        Sprite lisbon = spriteManager.addSprite("city", 44, 37, 0, 0);
        Sprite manila = spriteManager.addSprite("city", 82, 50, 0, 0);
        Sprite beijing = spriteManager.addSprite("city", 81, 35, 0, 0);
        Sprite shanghai = spriteManager.addSprite("city", 81, 43, 0, 0);
        Sprite capeTown = spriteManager.addSprite("city", 52, 81, 0, 0);
        Sprite jakarta = spriteManager.addSprite("city", 78, 65, 0, 0);
        Sprite newDehli = spriteManager.addSprite("city", 68, 45, 0, 0);
        Sprite rio = spriteManager.addSprite("city", 33, 74, 0, 0);
        Sprite timbuktu = spriteManager.addSprite("city", 47, 52, 0, 0);
        Sprite stPetersburg = spriteManager.addSprite("city", 55, 27, 0, 0);
        Sprite lima = spriteManager.addSprite("city", 25, 72, 0, 0);
        Sprite seattle = spriteManager.addSprite("city", 13, 32, 0, 0);
        Sprite sydney = spriteManager.addSprite("city", 88, 83, 0, 0);
        Sprite tehran = spriteManager.addSprite("city", 61, 45, 0, 0);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_main, container, false);
            return rootView;
        }
    }

}
