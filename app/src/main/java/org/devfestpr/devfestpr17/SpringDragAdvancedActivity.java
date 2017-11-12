package org.devfestpr.devfestpr17;

import android.os.Bundle;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SpringDragAdvancedActivity extends AppCompatActivity
        implements DragHelper.DragHelperListener {

    private View mainLayout;
    private View iconMovement;

    private DragHelper dragHelper;

    private SpringAnimation iconMovementSpringX;
    private SpringAnimation iconMovementSpringY;

    private SpringAnimation iconScaleSpringX;
    private SpringAnimation iconScaleSpringY;
    private float iconSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spring_drag_advanced);

        iconSize = Utils.dpToPx(this, 80);

        iconMovement = findViewById(R.id.icon_movement);

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
                .setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY)
                .setStiffness(SpringForce.STIFFNESS_LOW);

        iconMovementSpringY = new SpringAnimation(iconMovement, SpringAnimation.Y, 0f);
        iconMovementSpringY.getSpring()
                .setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY)
                .setStiffness(SpringForce.STIFFNESS_LOW);

        // Scale
        iconScaleSpringX = new SpringAnimation(iconMovement, SpringAnimation.SCALE_X, 0f);
        iconScaleSpringX.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);

        iconScaleSpringY = new SpringAnimation(iconMovement, SpringAnimation.SCALE_Y, 0f);
        iconScaleSpringY.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
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
    }

    @Override
    protected void onDestroy() {
        this.cancelMovementAnimationsIfNeeded();
        super.onDestroy();
    }

    private void cancelMovementAnimationsIfNeeded() {
        if (iconMovementSpringX != null) {
            iconMovementSpringX.cancel();
        }

        if (iconMovementSpringY != null) {
            iconMovementSpringY.cancel();
        }

        if (iconScaleSpringX != null) {
            iconScaleSpringX.cancel();
        }

        if (iconScaleSpringY != null) {
            iconScaleSpringY.cancel();
        }
    }
}
