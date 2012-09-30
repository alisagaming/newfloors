package com.emerginggames.floors.levels;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.SparseArray;
import android.view.View;
import android.widget.RelativeLayout;
import com.emerginggames.floors.R;
import com.emerginggames.floors.elevators.Elevator_2DoorsSlide;
import com.emerginggames.floors.model.Item;
import com.emrg.view.ImageView;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: babay
 * Date: 30.09.12
 * Time: 11:43
 * To change this template use File | Settings | File Templates.
 */
public class Level14 extends Level {
    private static final int[] NOTES = {7,6,5,4,3,2,1};
    private static final long MELODY_RESET_INTERVAL = 3000;
    private static final long OPEN_DELAY = 1100;
    boolean playButtonAdded;
    RelativeLayout playButtonCont;
    SoundController mSoundController;
    long lastClickedNoteTime;
    int noteProgress;

    public Level14(LevelListener levelListener, Context context) {
        super(levelListener, context);
        elevator.debug();
        items = new SparseArray<Item>(1);
        items.append(R.id.button, new Item(1, R.drawable.level14_play, false));

        setControl(((Elevator_2DoorsSlide) elevator).getLeftDoorId());
        mSoundController = new SoundController(getContext());
        setControl(R.id.note1);
        setControl(R.id.note2);
        setControl(R.id.note3);
        setControl(R.id.note4);
        setControl(R.id.note5);
        setControl(R.id.note6);
        setControl(R.id.note7);
    }

    @Override
    protected void onElementClicked(View elementView) {
        if (!playButtonAdded && elementView.getId() == ((Elevator_2DoorsSlide) elevator).getLeftDoorId()
                && levelListener.getCurrentItem() != null && levelListener.getCurrentItem().itemId == 1){
            playButtonCont.setVisibility(VISIBLE);
            playButtonAdded = true;
            elementView.setOnClickListener(null);
            levelListener.removeCurrentItem();
        } else switch (elementView.getId()){
            case R.id.level_control1:
                if (playButtonAdded)
                    mSoundController.play(0);
                break;
            case R.id.note1:
                mSoundController.play(NOTES[0]);
                checkNote(0);
                break;
            case R.id.note2:
                mSoundController.play(NOTES[1]);
                checkNote(1);
                break;
            case R.id.note3:
                mSoundController.play(NOTES[2]);
                checkNote(2);
                break;
            case R.id.note4:
                mSoundController.play(NOTES[3]);
                checkNote(3);
                break;
            case R.id.note5:
                mSoundController.play(NOTES[4]);
                checkNote(4);
                break;
            case R.id.note6:
                mSoundController.play(NOTES[5]);
                checkNote(5);
                break;
            case R.id.note7:
                mSoundController.play(NOTES[6]);
                checkNote(6);
                break;
        }
    }

    void checkNote(int n){
        if (System.currentTimeMillis() - lastClickedNoteTime > MELODY_RESET_INTERVAL)
            noteProgress = 0;

        lastClickedNoteTime = System.currentTimeMillis();

        if (NOTES[n] == noteProgress +1)
            noteProgress++;

        if (noteProgress == NOTES.length)
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    elevator.openDoors();
                }
            }, OPEN_DELAY);

    }

    @Override
    protected int getLevelLayoutId() {
        return R.layout.level_14;
    }

    @Override
    protected void initView() {
        super.initView();

        int doorId = ((Elevator_2DoorsSlide) elevator).getLeftDoorId();
        RelativeLayout.LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        lp.addRule(ALIGN_LEFT, doorId);
        lp.addRule(ALIGN_RIGHT, doorId);
        lp.addRule(ALIGN_TOP, doorId);
        lp.addRule(ALIGN_BOTTOM, doorId);

        playButtonCont = new RelativeLayout(getContext());
        playButtonCont.setVisibility(GONE);

        RelativeLayout.LayoutParams lpIcon = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lpIcon.addRule(CENTER_IN_PARENT);

        ImageView image = new ImageView(getContext());
        image.setImageResource(R.drawable.level14_play);
        image.setScaleType(android.widget.ImageView.ScaleType.FIT_CENTER);
        image.setPadding(5,0,0,0);
        image.setId(R.id.level_control1);
        playButtonCont.addView(image, lpIcon);

        elevator.getDoorsView().addView(playButtonCont, lp);

        setControl(R.id.level_control1);
    }

    class SoundController {
        Context mContext;
        SoundPool soundPool;
        int[] soundIds = new int[8];

        SoundController(Context mContext) {
            this.mContext = mContext;

            soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
            try {
                soundIds[0] = loadSoundAsset("sounds/level14/level14music.mp3");
                soundIds[1] = loadSoundAsset("sounds/level14/level14_sound1.mp3");
                soundIds[2] = loadSoundAsset("sounds/level14/level14_sound2.mp3");
                soundIds[3] = loadSoundAsset("sounds/level14/level14_sound3.mp3");
                soundIds[4] = loadSoundAsset("sounds/level14/level14_sound4.mp3");
                soundIds[5] = loadSoundAsset("sounds/level14/level14_sound5.mp3");
                soundIds[6] = loadSoundAsset("sounds/level14/level14_sound6.mp3");
                soundIds[7] = loadSoundAsset("sounds/level14/level14_sound7.mp3");

                //soundPool.load()
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private int loadSoundAsset(String path) throws IOException {
            AssetFileDescriptor descriptor = mContext.getAssets().openFd(path);
            return soundPool.load(descriptor, 1);
        }

        public void play(int n) {
            soundPool.play(soundIds[n], 0.7f, 0.7f, 0, 0, 1);
        }
    }
}
