package com.juanjofp.android.animation;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ICSAnimationActivity extends Activity {
    /** Called when the activity is first created. */
	
	private TextView tvAnimated;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        tvAnimated = (TextView)findViewById(R.id.tvAnimated);

        
    }
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();

    }

    private void animatedOut()
    {
        final ValueAnimator va = ValueAnimator.ofInt(0,400);
        va.setDuration(3000);

        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // TODO Auto-generated method stub
                tvAnimated.setX((Integer)animation.getAnimatedValue());
                tvAnimated.setY((Integer)animation.getAnimatedValue());
            }
        });

        va.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // TODO Auto-generated method stub
                va.reverse();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // TODO Auto-generated method stub

            }
        });

        va.start();

        tvAnimated.animate().alpha(1).setDuration(3000);
    }

    public void animatedIn(View v)
    {
        final ValueAnimator va = ValueAnimator.ofInt(0,400);
        va.setDuration(3000);

        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // TODO Auto-generated method stub
                tvAnimated.setX((Integer)animation.getAnimatedValue());
                tvAnimated.setY((Integer)animation.getAnimatedValue());
            }
        });

        va.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // TODO Auto-generated method stub
                va.reverse();
                tvAnimated.animate().alpha(1).setDuration(3000);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // TODO Auto-generated method stub

            }
        });

        va.start();

        tvAnimated.animate().alpha(0).setDuration(3000);
    }
}