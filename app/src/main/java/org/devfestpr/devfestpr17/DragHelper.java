package org.devfestpr.devfestpr17;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import java.util.ArrayList;

/**
 * Palestra - Animacoes fluidas no Android
 * DevFestPR 2017 - 11/11/2017
 * @author Renato Peterman
 */

public class DragHelper implements View.OnTouchListener {

    public interface DragHelperListener {
        void onDragStart(View view, float xPosition, float yPosition);
        void onDragMove(View view, float xPosition, float yPosition);
        void onDragEnd(View view, float xVelocity, float yVelocity);
    }

    private ArrayList<View> draggableViews = new ArrayList<>();
    private View selectedView;
    private VelocityTracker velocityTracker = VelocityTracker.obtain();
    private float downX = 0f;
    private float downY = 0f;
    private Rect childHitRect = new Rect();
    private DragHelperListener dragHelperListener;

    private boolean animate = true;

    public void addDraggable(View view) {
        draggableViews.add(view);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();

        switch (action) {

            case MotionEvent.ACTION_DOWN:
                for (View v : draggableViews) {
                    if (handleDown(v, motionEvent)) {
                        return true;
                    }
                }
                return false;

            case MotionEvent.ACTION_MOVE:
                float currentX = motionEvent.getX();
                float currentY = motionEvent.getY();

                if (selectedView != null && animate) {
                    selectedView.setTranslationX(
                            selectedView.getTranslationX() + (currentX - downX)
                    );

                    selectedView.setTranslationY(
                            selectedView.getTranslationY() + (currentY - downY)
                    );
                }

                downX = motionEvent.getX();
                downY = motionEvent.getY();

                if (dragHelperListener != null && selectedView != null) {
                    dragHelperListener.onDragMove(selectedView, currentX, currentY);
                }

                velocityTracker.addMovement(motionEvent);
                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                velocityTracker.computeCurrentVelocity(1000);
                if (dragHelperListener != null && selectedView != null) {
                    dragHelperListener.onDragEnd(view, velocityTracker.getXVelocity(), velocityTracker.getYVelocity());
                }
                velocityTracker.clear();
                selectedView = null;
                return true;

            default:
                return false;
        }
    }

    private boolean handleDown(View view, MotionEvent event) {
        view.getHitRect(childHitRect);
        if (childHitRect.contains((int)event.getX(), (int)event.getY())) {
            downX = event.getX();
            downY = event.getY();
            velocityTracker.addMovement(event);
            selectedView = view;
            if (dragHelperListener != null) {
                dragHelperListener.onDragStart(view, downX, downY);
            }
            return true;
        }

        return false;
    }

    public void setDragHelperListener(DragHelperListener dragHelperListener) {
        this.dragHelperListener = dragHelperListener;
    }

    public void setAnimate(boolean animate) {
        this.animate = animate;
    }
}
