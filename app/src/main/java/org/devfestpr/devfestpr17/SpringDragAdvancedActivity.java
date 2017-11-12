package org.devfestpr.devfestpr17;

import android.graphics.Point;
import android.os.Bundle;
import android.support.animation.DynamicAnimation;
import android.support.animation.FlingAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.View;
import android.widget.SeekBar;

public class SpringDragAdvancedActivity extends AppCompatActivity
        implements DragHelper.DragHelperListener, SeekBar.OnSeekBarChangeListener {

    static final float DEFAULT_MOVEMENT_DAMPING = SpringForce.DAMPING_RATIO_HIGH_BOUNCY;
    static final float DEFAULT_MOVEMENT_STIFFNESS = SpringForce.STIFFNESS_LOW;

    private View mainLayout;
    private View iconMovement;
    private AppCompatSeekBar seekBarDamping;
    private AppCompatSeekBar seekBarStiffness;
    private AppCompatCheckBox checkBoxFling;

    private DragHelper dragHelper;

    private SpringAnimation iconMovementSpringX;
    private SpringAnimation iconMovementSpringY;

    private SpringAnimation iconScaleSpringX;
    private SpringAnimation iconScaleSpringY;

    private FlingAnimation flingAnimationX;
    private FlingAnimation flingAnimationY;

    private float iconSize;
    private Point screenSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spring_drag_advanced);

        iconSize = Utils.dpToPx(this, 80);
        screenSize = Utils.getScreenSize(this);

        iconMovement = findViewById(R.id.icon_movement);
        checkBoxFling = findViewById(R.id.checkbox_fling);

        seekBarDamping = findViewById(R.id.seek_var_damping);
        seekBarDamping.setProgress((int)(DEFAULT_MOVEMENT_DAMPING*100));
        seekBarDamping.setOnSeekBarChangeListener(this);

        seekBarStiffness = findViewById(R.id.seek_var_stiffness);
        seekBarStiffness.setProgress((int)DEFAULT_MOVEMENT_STIFFNESS);
        seekBarStiffness.setOnSeekBarChangeListener(this);

        dragHelper = new DragHelper();
        dragHelper.setAnimate(false);
        dragHelper.setDragHelperListener(this);
        dragHelper.addDraggable(iconMovement);

        mainLayout = findViewById(R.id.main_layout);
        mainLayout.setOnTouchListener(dragHelper);

        setupAnimations();
    }

    public void setupAnimations() {

        // Translation
        iconMovementSpringX = new SpringAnimation(iconMovement, SpringAnimation.X, 0f);
        iconMovementSpringX.getSpring()
                .setDampingRatio(DEFAULT_MOVEMENT_DAMPING)
                .setStiffness(DEFAULT_MOVEMENT_STIFFNESS);

        iconMovementSpringY = new SpringAnimation(iconMovement, SpringAnimation.Y, 0f);
        iconMovementSpringY.getSpring()
                .setDampingRatio(DEFAULT_MOVEMENT_DAMPING)
                .setStiffness(DEFAULT_MOVEMENT_STIFFNESS);

        // Scale
        iconScaleSpringX = new SpringAnimation(iconMovement, SpringAnimation.SCALE_X, 0f);
        iconScaleSpringX.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);

        iconScaleSpringY = new SpringAnimation(iconMovement, SpringAnimation.SCALE_Y, 0f);
        iconScaleSpringY.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);

        // Fling
        flingAnimationX = new FlingAnimation(iconMovement, DynamicAnimation.X);
        flingAnimationX.setFriction(1.1f);

        flingAnimationY = new FlingAnimation(iconMovement, DynamicAnimation.Y);
        flingAnimationY.setFriction(1.1f);
    }

    @Override
    public void onDragStart(View view, float xPosition, float yPosition) {

        // aplica os springs de scale ao selectionar
        iconScaleSpringX.animateToFinalPosition(1.4f);
        iconScaleSpringY.animateToFinalPosition(1.4f);
    }

    @Override
    public void onDragMove(View view, float xPosition, float yPosition) {

        // aplica os springs de movimento enquanto movimenta
        iconMovementSpringX.animateToFinalPosition(xPosition - iconSize / 2);
        iconMovementSpringY.animateToFinalPosition(yPosition - iconSize / 2);
    }

    @Override
    public void onDragEnd(View view, float xVelocity, float yVelocity) {

        // aplica os springs de scale ao soltar
        iconScaleSpringX.animateToFinalPosition(1f);
        iconScaleSpringY.animateToFinalPosition(1f);

        // fling
        if (checkBoxFling.isChecked()) {
            this.cancelMovementAnimationsIfNeeded();

            flingAnimationX.setStartVelocity(xVelocity).start();
            flingAnimationY.setStartVelocity(yVelocity).start();
        }

    }

    @Override
    protected void onDestroy() {
        this.cancelMovementAnimationsIfNeeded();
        this.cancelScaleAnimationsIfNeeded();

        super.onDestroy();
    }

    private void cancelMovementAnimationsIfNeeded() {
        if (iconMovementSpringX != null) {
            iconMovementSpringX.cancel();
        }

        if (iconMovementSpringY != null) {
            iconMovementSpringY.cancel();
        }
    }

    private void cancelScaleAnimationsIfNeeded() {
        if (iconScaleSpringX != null) {
            iconScaleSpringX.cancel();
        }

        if (iconScaleSpringY != null) {
            iconScaleSpringY.cancel();
        }
    }

    /** SeekBar.OnSeekBarChangeListener below **/

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar.getId() == R.id.seek_var_damping) {
            float v = (float)progress/100f;
            iconMovementSpringX.getSpring().setDampingRatio(v);
            iconMovementSpringY.getSpring().setDampingRatio(v);
        }
        else if (seekBar.getId() == R.id.seek_var_stiffness) {
            iconMovementSpringX.getSpring().setStiffness(progress);
            iconMovementSpringY.getSpring().setStiffness(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
