package com.emerginggames.floors;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import com.emerginggames.floors.levels.*;
import com.emerginggames.floors.model.Item;
import com.emrg.view.CheckImageView;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created with IntelliJ IDEA.
 * User: babay
 * Date: 21.08.12
 * Time: 9:40
 * To change this template use File | Settings | File Templates.
 */
public class FloorActivity extends Activity {
    Level currentLevel;
    Level oldLevel;
    CheckImageView currentCell;
    LevelInterface levelInterface = new LevelInterface();
    Preferences prefs;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_level);
        prefs = Preferences.getInstance(getApplicationContext());
        prefs.setCurrentLevel(1);
        startLevel(prefs.getCurrentLevel());

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (currentLevel != null)
            currentLevel.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (currentLevel != null)
            currentLevel.onPause();
    }

    public void OnMenuButtonClick(View v){

    }

    public void OnCellClick(View v){
        CheckImageView cell = (CheckImageView)v;
        if (currentCell != v && currentCell != null)
            currentCell.setChecked(false);
        if (cell.isChecked()){
            currentCell = cell;
            currentLevel.itemSelected((Item)currentCell.getTag());
        }
        else
            currentCell = null;
    }

    public void OnRestartButtonClick(View v){
        onLevelStart();
    }

    void startLevel(int n){
        switch (n){
            case 1:
                startLevel(new Level01(levelInterface, getApplicationContext()));
                break;
            case 2:
                startLevel(new Level02(levelInterface, getApplicationContext()));
                break;
            case 3:
                startLevel(new Level03(levelInterface, getApplicationContext()));
                break;

            default:
        }
    }

    void startLevel(Level level){

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        ViewGroup levelCont = (ViewGroup)findViewById(R.id.levelCont);
        if (currentLevel == null){

            levelCont.addView(level, lp);
            currentLevel = level;
        }
        else {
            oldLevel = currentLevel;
            currentLevel = level;

            levelCont.addView(currentLevel, lp);

            Animation leaveAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_level_down);
            leaveAnimation.setAnimationListener(levelGoneListener);
            oldLevel.startAnimation(leaveAnimation);

            Animation introduceAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_level_from_up);
            introduceAnimation.setAnimationListener(levelSwitchDoneListener);
            currentLevel.startAnimation(introduceAnimation);
        }
    }

    void onLevelStart(){
        if (currentCell != null){
            currentCell.setChecked(false);
            currentCell = null;
        }

        clearCell((CheckImageView)findViewById(R.id.cell1));
        clearCell((CheckImageView)findViewById(R.id.cell2));
        clearCell((CheckImageView)findViewById(R.id.cell3));
        clearCell((CheckImageView)findViewById(R.id.cell4));

        currentLevel.start();
    }


    void clearCell(CheckImageView cell){
        cell.setTag(null);
        cell.setChecked(false);
        cell.setImageDrawable(null);
    }


    class LevelInterface implements Level.LevelListener {
        @Override
        public Item getCurrentItem() {
            return currentCell == null ? null : (Item)currentCell.getTag();
        }

        @Override
        public void addItem(Item item) {
            CheckImageView cell = getNextFreeCell();
            if (cell == null)
                throw new RuntimeException("No free cell!!!!");
            cell.setTag(item);
            cell.setImageResource(item.inventoryDrawableId);
        }

        @Override
        public void removeCurrentItem() {
            if (currentCell != null)
                clearCell(currentCell);
        }

        @Override
        public void levelComplete() {
            prefs.advanceLevel();
            startLevel(prefs.getCurrentLevel());
        }

        @Override
        public void resetCurrentItem() {
            if (currentCell != null){
                currentCell.setChecked(false);
                currentCell = null;
            }
        }
    }

    CheckImageView getNextFreeCell(){
        CheckImageView cell;
        cell = (CheckImageView)findViewById(R.id.cell1);
        if (cell.getTag() == null)
            return cell;

        cell = (CheckImageView)findViewById(R.id.cell2);
        if (cell.getTag() == null)
            return cell;

        cell = (CheckImageView)findViewById(R.id.cell3);
        if (cell.getTag() == null)
            return cell;

        cell = (CheckImageView)findViewById(R.id.cell4);
        if (cell.getTag() == null)
            return cell;

        return null;
    }

    Animation.AnimationListener levelSwitchDoneListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            currentLevel.onResume();
            onLevelStart();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    Animation.AnimationListener levelGoneListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {}

        @Override
        public void onAnimationEnd(Animation animation) {
            oldLevel.post(new Runnable() {
                @Override
                public void run() {
                    oldLevel.onPause();
                    ViewGroup levelCont = (ViewGroup)findViewById(R.id.levelCont);
                    levelCont.removeView(oldLevel);
                }
            });
        }

        @Override
        public void onAnimationRepeat(Animation animation) {}
    };


}