package org.devfestpr.devfestpr17;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Palestra - Animacoes fluidas no Android
 * DevFestPR 2017 - 11/11/2017
 * @author Renato Peterman
 */

public class ApisActivity extends AppCompatActivity {

    private View iconViewAnimation;

    private View headerPropertyAnimation;
    private View iconObjectAnimatorXml;
    private View iconObjectAnimatorCode;
    private View iconViewPropertyAnimator;

    private View iconValueAnimator;
    private TextView labelValueAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apis);

        iconViewAnimation = findViewById(R.id.icon_view_animation);
        iconViewAnimation.setOnClickListener(this::onClick);

        headerPropertyAnimation = findViewById(R.id.header_property_animation);

        iconObjectAnimatorCode = findViewById(R.id.icon_object_animator_code);
        iconObjectAnimatorCode.setOnClickListener(this::onClick);

        iconObjectAnimatorXml = findViewById(R.id.icon_object_animator_xml);
        iconObjectAnimatorXml.setOnClickListener(this::onClick);

        iconValueAnimator = findViewById(R.id.icon_value_animator);
        iconValueAnimator.setOnClickListener(this::onClick);
        labelValueAnimator = findViewById(R.id.label_value_animator);

        iconViewPropertyAnimator = findViewById(R.id.icon_view_property_animator);
        iconViewPropertyAnimator.setOnClickListener(this::onClick);
    }

    protected void onClick(View view) {
        switch (view.getId()) {

            case R.id.icon_view_animation:
                playViewAnimation();
                break;

            case R.id.header_property_animation:

                break;

            case R.id.icon_object_animator_xml:
                Animator animatorFromXml = createObjectAnimatorFromXml();
                animatorFromXml.start();
                break;

            case R.id.icon_object_animator_code:
                Animator animatorFromCode = createObjectAnimatorFromCode();
                animatorFromCode.start();
                break;

            case R.id.icon_value_animator:
                Animator valueAnimator = createValueAnimator();
                valueAnimator.start();
                break;

            case R.id.icon_view_property_animator:
                playViewPropertyAnimator();
                break;
        }

    }

    protected void playViewAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this,
                R.anim.translate_x_and_scale_view_animation);

        iconViewAnimation.startAnimation(animation);
    }

    protected Animator createObjectAnimatorFromXml() {

        // load the animator set
        Animator animator = AnimatorInflater.loadAnimator(this,
                R.animator.rotate_and_translate_horizontal_object_animator);

        // set the view target
        animator.setTarget(iconObjectAnimatorXml);

        return animator;
    }

    protected Animator createObjectAnimatorFromCode() {

        // translation X animation
        float positionStart = 0;
        float positionEnd = Utils.dpToPx(this, 250);
        Animator translationAnimator = ObjectAnimator.ofFloat(iconObjectAnimatorCode, View.TRANSLATION_X, positionStart, positionEnd);

        LinearInterpolator translationInterpolator = new LinearInterpolator();
        translationAnimator.setInterpolator(translationInterpolator);

        // translation Y animation
        float rotationStart = 0;
        float rotationEnd = 720;
        Animator rotationAnimator = ObjectAnimator.ofFloat(iconObjectAnimatorCode, View.ROTATION, rotationStart, rotationEnd);

        AccelerateDecelerateInterpolator rotationInterpolator = new AccelerateDecelerateInterpolator();
        rotationAnimator.setInterpolator(rotationInterpolator);

        // play together
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(2000); // 2 segundos

        // executa as animacoes todas ao mesmo tempo
        animatorSet.playTogether(translationAnimator, rotationAnimator);

        // executa as animacoes em sequencia
        // animatorSet.playSequentially(translationAnimator, rotationAnimator);

        return animatorSet;
    }

    protected Animator createValueAnimator() {

        // translation X animation
        float positionStart = 0;
        float positionEnd = Utils.dpToPx(this, 250);
        ValueAnimator translationAnimator = ValueAnimator.ofFloat(positionStart, positionEnd);
        translationAnimator.setDuration(2000);

        translationAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                iconValueAnimator.setTranslationX(animatedValue);
                labelValueAnimator.setText(String.format("ValueAnimator (%.2f)", animatedValue));
            }
        });

        translationAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Toast.makeText(ApisActivity.this, "ValueAnimator fim!", Toast.LENGTH_SHORT);
            }
        });

        return translationAnimator;
    }

    protected void playViewPropertyAnimator() {

        float positionEnd = Utils.dpToPx(this, 250);
        float rotationEnd = 720;

        iconViewPropertyAnimator
                .animate()
                .setDuration(2000)
                .translationX(positionEnd)
                .rotation(rotationEnd).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Toast.makeText(ApisActivity.this, "ViewPropertyAnimator fim!", Toast.LENGTH_SHORT);
                    }
                });
    }
}
