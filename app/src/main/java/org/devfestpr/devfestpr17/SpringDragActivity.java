package org.devfestpr.devfestpr17;

import android.os.Bundle;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SpringDragActivity extends AppCompatActivity
        implements DragHelper.DragHelperListener {

    private View iconMovement;
    private DragHelper dragHelper;
    private View mainLayout;
    private View buttonSimpleAnim;

    private SpringAnimation currentSimpleAnimation;

    private SpringAnimation iconMovementSpringX;
    private SpringAnimation iconMovementSpringY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spring_drag);

        buttonSimpleAnim = findViewById(R.id.btn_simple_animation);
        buttonSimpleAnim.setOnClickListener(v -> this.createSimpleSpringAnimation());
        iconMovement = findViewById(R.id.icon_movement);

        dragHelper = new DragHelper();
        dragHelper.setDragHelperListener(this);
        dragHelper.addDraggable(iconMovement);

        mainLayout = findViewById(R.id.main_layout);
        mainLayout.setOnTouchListener(dragHelper);
    }

    @Override
    public void onDragStart(View view, float xPosition, float yPosition) {

    }

    @Override
    public void onDragMove(View view, float xPosition, float yPosition) {

    }

    @Override
    public void onDragEnd(View view, float xVelocity, float yVelocity) {
        this.cancelMovementAnimationsIfNeeded();

        iconMovementSpringX = new SpringAnimation(iconMovement, SpringAnimation.TRANSLATION_X, 0f);
        iconMovementSpringX.setStartVelocity(xVelocity);

        iconMovementSpringY = new SpringAnimation(iconMovement, SpringAnimation.TRANSLATION_Y, 0f);
        iconMovementSpringY.setStartVelocity(yVelocity);

        // start!
        iconMovementSpringX.start();
        iconMovementSpringY.start();
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
    }

    private void createSimpleSpringAnimation() {
        if (currentSimpleAnimation != null && currentSimpleAnimation.isRunning())
            currentSimpleAnimation.cancel();

        iconMovement.setTranslationX(0);
        iconMovement.setTranslationY(0);

        // calcula a posição Y - convert 100dp para pixels
        float endPosition = Utils.dpToPx(this, -180);

        // cria a animacao
        currentSimpleAnimation = new SpringAnimation(iconMovement,
                SpringAnimation.TRANSLATION_Y,
                endPosition
        );

        // altera o damping (amortecimento)
        currentSimpleAnimation.getSpring()
                .setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY)
                .setStiffness(SpringForce.STIFFNESS_LOW);

        // inicia na animcação
        currentSimpleAnimation.start();
    }
}
