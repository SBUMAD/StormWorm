package com.sbumad.stormworm.game;

import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.sbumad.stormworm.R;
import com.sbumad.stormworm.sprite.SpriteManager;



public class MainActivity extends ActionBarActivity {

    public static MainActivity main;
    private SpriteManager spriteManager;
    private static int screenWidth;
    private static int screenHeight;


    public SpriteManager getSpriteManager(){return spriteManager;}
    public static MainActivity getMain(){
        return main;
    }

    public static int getScreenWidth(){return screenWidth;}
    public static int getScreenHeight(){return screenHeight;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        // Make a new Screen
        DrawView screen = new DrawView(this);
        // create the sprite manager
        spriteManager = new SpriteManager(BitmapFactory.decodeResource(getResources(), R.drawable.background), 0, 200);
        // set the screen as the current display
        setContentView(screen);


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
