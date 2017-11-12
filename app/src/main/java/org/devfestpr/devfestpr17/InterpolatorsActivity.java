package org.devfestpr.devfestpr17;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Palestra - Animacoes fluidas no Android
 * DevFestPR 2017 - 11/11/2017
 * @author Renato Peterman
 */

public class InterpolatorsActivity extends AppCompatActivity {

    protected List<Animator> animatorList = new ArrayList<>();

    protected AnimatorSet animatorSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interpolators);

        setupAnimators();

        findViewById(R.id.main_layout).setOnClickListener(this::playAll);
    }

    protected void playAll(View v) {
        // verifique se a animacao esta rodando
        if (animatorSet != null && animatorSet.isRunning()) {
            animatorSet.cancel();
        }

        // cria um novo animator set
        animatorSet = new AnimatorSet();
        animatorSet.setDuration(2000);

        animatorSet.playTogether(animatorList);

        animatorSet.start();
    }

    private void addAnimatorToTheList(View view, Interpolator interpolator) {
        animatorList.add(createAnimator(view, interpolator));
    }

    protected Animator createAnimator(View view, Interpolator interpolator) {
        Animator translationAnimator = ObjectAnimator.ofFloat(view,
                View.TRANSLATION_X,
                0,
                Utils.dpToPx(this, 250)
        );

        translationAnimator.setInterpolator(interpolator);

        // vamos set a duration no AnimatorSet
        // translationAnimator.setDuration(1000);

        return translationAnimator;
    }

    private void setupAnimators() {
        addAnimatorToTheList(
                findViewById(R.id.icon_accelerate_decelerate_interpolator),
                new AccelerateDecelerateInterpolator()
        );

        addAnimatorToTheList(
                findViewById(R.id.icon_accelerate_interpolator),
                new AccelerateInterpolator()
        );

        addAnimatorToTheList(
                findViewById(R.id.icon_decelerate_interpolator),
                new DecelerateInterpolator()
        );

        addAnimatorToTheList(
                findViewById(R.id.icon_anticipate_interpolator),
                new AnticipateInterpolator()
        );

        addAnimatorToTheList(
                findViewById(R.id.icon_anticipate_overshoot_interpolator),
                new AnticipateOvershootInterpolator()
        );

        addAnimatorToTheList(
                findViewById(R.id.icon_linear_interpolator),
                new LinearInterpolator()
        );

        addAnimatorToTheList(
                findViewById(R.id.icon_bounce_interpolator),
                new BounceInterpolator()
        );

        addAnimatorToTheList(
                findViewById(R.id.icon_overshoot_interpolator),
                new OvershootInterpolator()
        );
    }
}

