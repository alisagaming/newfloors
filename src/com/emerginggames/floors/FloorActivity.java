package com.emerginggames.floors;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import com.emerginggames.floors.levels.*;
import com.emerginggames.floors.model.Item;
import com.emrg.view.CheckImageView;

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
    Level nextLevel;
    CheckImageView currentCell;
    LevelInterface levelInterface = new LevelInterface();
    Preferences prefs;
    int topOverflow;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.layout_level);

        Metrics.setSizeFromView(getWindow().getDecorView());
        prefs = Preferences.getInstance(getApplicationContext());

        //prefs.setCurrentLevel(1);
        startLevel(prefs.getCurrentLevel());

        topOverflow = (int)( (854 * Metrics.scale - Metrics.height) * 0.8f);
        if (topOverflow > 0 )
            ((ViewGroup.MarginLayoutParams)(findViewById(R.id.levelCont).getLayoutParams())).topMargin = -topOverflow;
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

    public void OnMenuButtonClick(View v) {
        finish();
    }

    public void OnCellClick(View v) {
        CheckImageView cell = (CheckImageView) v;
        deactivateCurrentCell();
        if (cell.isChecked())
            activateCell(cell);
    }

    void activateCell(CheckImageView cell){
        currentLevel.itemSelected((Item) cell.getTag());

        Item item = (Item) cell.getTag();
        if (item != null)
            item.onActivate(currentLevel);

        currentLevel.itemSelected((Item) cell.getTag());
        currentCell = cell;
    }

    void deactivateCurrentCell(){
        if (currentCell == null)
            return;

        currentCell.setChecked(false);
        Item item = (Item) currentCell.getTag();
        if (item != null)
            item.onDeactivate(currentLevel);

        currentLevel.itemSelected(null);
        currentCell = null;
    }

    public void OnRestartButtonClick(View v) {
        onLevelStart();
    }

    void startLevel(int n) {
        Level level = ControllerLevels.getLevel(n, levelInterface, getApplicationContext());
        if (level != null) {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
            lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);

            ViewGroup levelCont = (ViewGroup) findViewById(R.id.levelCont);
            levelCont.addView(level, lp);
            currentLevel = level;
            onLevelStart();
        }
    }

    void startNextLevel() {
        if (nextLevel == null)
            return;

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        ViewGroup levelCont = (ViewGroup) findViewById(R.id.levelCont);

        oldLevel = currentLevel;
        currentLevel = nextLevel;

        levelCont.addView(currentLevel, lp);

        Animation leaveAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_level_down);
        leaveAnimation.setAnimationListener(levelGoneListener);
        oldLevel.startAnimation(leaveAnimation);

        Animation introduceAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_level_from_up);
        introduceAnimation.setAnimationListener(levelSwitchDoneListener);
        currentLevel.startAnimation(introduceAnimation);
    }

    void onLevelStart() {
        if (currentCell != null) {
            currentCell.setChecked(false);
            currentCell = null;
        }

        clearCell((CheckImageView) findViewById(R.id.cell1));
        clearCell((CheckImageView) findViewById(R.id.cell2));
        clearCell((CheckImageView) findViewById(R.id.cell3));
        clearCell((CheckImageView) findViewById(R.id.cell4));

        currentLevel.start();

        nextLevel = ControllerLevels.getLevel(prefs.getCurrentLevel() + 1, levelInterface, getApplicationContext());
    }


    void clearCell(CheckImageView cell) {
        cell.setTag(null);
        cell.setChecked(false);
        cell.setImageDrawable(null);
    }


    class LevelInterface implements Level.LevelListener {
        @Override
        public Item getCurrentItem() {
            return currentCell == null ? null : (Item) currentCell.getTag();
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
            if (nextLevel != null)
                prefs.advanceLevel();
            startNextLevel();
            //startLevel(prefs.getCurrentLevel());
        }

        @Override
        public void resetCurrentItem() {
            if (currentCell != null) {
                currentCell.setChecked(false);
                currentCell = null;
                currentLevel.itemSelected(null);
            }
        }

        @Override
        public int getCoveredBottomHeight() {
            int bottomOverflow = currentLevel.getHeight() - Metrics.height - topOverflow;
            return findViewById(R.id.banner).getHeight() + findViewById(R.id.toolbar).getHeight() + bottomOverflow;
        }
    }

    CheckImageView getNextFreeCell() {
        CheckImageView cell;
        cell = (CheckImageView) findViewById(R.id.cell1);
        if (cell.getTag() == null)
            return cell;

        cell = (CheckImageView) findViewById(R.id.cell2);
        if (cell.getTag() == null)
            return cell;

        cell = (CheckImageView) findViewById(R.id.cell3);
        if (cell.getTag() == null)
            return cell;

        cell = (CheckImageView) findViewById(R.id.cell4);
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
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            oldLevel.post(new Runnable() {
                @Override
                public void run() {
                    oldLevel.onPause();
                    ViewGroup levelCont = (ViewGroup) findViewById(R.id.levelCont);
                    levelCont.removeView(oldLevel);
                }
            });
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    };


}