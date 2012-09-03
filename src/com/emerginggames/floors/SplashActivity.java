package com.emerginggames.floors;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.emerginggames.floors.levels.ControllerLevels;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: babay
 * Date: 03.09.12
 * Time: 22:37
 * To change this template use File | Settings | File Templates.
 */
public class SplashActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_screen);

        if (Settings.DEV_FUNC){
            Spinner selectLevelSpinner = (Spinner)findViewById(R.id.selectLevelSpinner);
            selectLevelSpinner.setVisibility(View.VISIBLE);

            ArrayList<String> spinnerArray = new ArrayList<String>();
            for (int i=1; i<= ControllerLevels.maxLevels; i++)
                spinnerArray.add(String.format("Level %d", i));

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                    R.layout.simple_spinner_item, spinnerArray);
            spinnerArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
            selectLevelSpinner.setAdapter(spinnerArrayAdapter);

            selectLevelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Preferences.getInstance(getApplicationContext()).setCurrentLevel(position+1);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    public void onClickStart(View v){
        startActivity(new Intent(this, FloorActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Settings.DEV_FUNC){
            Spinner selectLevelSpinner = (Spinner)findViewById(R.id.selectLevelSpinner);
            selectLevelSpinner.setSelection(Preferences.getInstance(getApplicationContext()).getCurrentLevel() - 1);
        }
    }
}