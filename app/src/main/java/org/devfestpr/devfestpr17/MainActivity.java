package org.devfestpr.devfestpr17;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Palestra - Animacoes fluidas no Android
 * DevFestPR 2017 - 11/11/2017
 * @author Renato Peterman
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.item_apis).setOnClickListener(
                view -> Utils.launch(this, ApisActivity.class));

        findViewById(R.id.item_interpolators).setOnClickListener(
                view -> Utils.launch(this, InterpolatorsActivity.class));

        findViewById(R.id.item_spring_drag).setOnClickListener(
                view -> Utils.launch(this, SpringDragActivity.class));

        findViewById(R.id.item_spring_drag_advanced).setOnClickListener(
                view -> Utils.launch(this, SpringDragAdvancedActivity.class));
    }
}
