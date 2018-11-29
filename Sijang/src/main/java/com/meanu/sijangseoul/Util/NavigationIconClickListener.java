package com.meanu.sijangseoul.Util;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.ImageView;

import com.meanu.sijangseoul.Backdrop.BackdropMainFragment;
import com.meanu.sijangseoul.R;

/**
 * {@link View.OnClickListener} used to translate the product grid sheet downward on
 * the Y-axis when the navigation icon in the toolbar is pressed.
 */
public class NavigationIconClickListener implements View.OnClickListener {

    private final AnimatorSet animatorSet = new AnimatorSet();
    private Context context;
    private View sheet;
    private Interpolator interpolator;
    private int height;
    private boolean backdropShown = false;
    private Drawable openIcon;
    private Drawable closeIcon;
    private FragmentManager fragmentManager;
    int a = 0;

    public  NavigationIconClickListener(
            Context context, FragmentManager fragmentManager, View sheet, @Nullable Interpolator interpolator,
            @Nullable Drawable openIcon, @Nullable Drawable closeIcon) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.sheet = sheet;
        this.interpolator = interpolator;
        this.openIcon = openIcon;
        this.closeIcon = closeIcon;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;


    }

    public NavigationIconClickListener(Context context) {
        this.context = context;
        if (a == 0){
            a++;
        }
        else{
            a--;
        }
    }

    @Override
    public void onClick(View view) {

        switch (a) {
            case 0:
                FragmentTransaction fts = fragmentManager.beginTransaction();
                fts.replace(R.id.backdrop, new BackdropMainFragment(), "BackdropMainFragment");
                fts.commit();
                updateFragment(view);
                a++;
                break;
            case 1:
                FragmentTransaction fts3 = fragmentManager.beginTransaction();
                fts3.replace(R.id.backdrop, new BackdropMainFragment(), "BackdropMainFragment");
                fts3.commit();
                updateFragment(view);
                fragmentManager.popBackStack();
                a--;
        }

    }

    private void updateIcon(View view) {
        if (openIcon != null && closeIcon != null) {
            if (!(view instanceof ImageView)) {
                throw new IllegalArgumentException("updateIcon() must be called on an ImageView");
            }
            if (backdropShown) {
                ((ImageView) view).setImageDrawable(closeIcon);
            } else {
                ((ImageView) view).setImageDrawable(openIcon);
            }
        }
    }

    public void updateFragment(View view) {
        backdropShown = !backdropShown;
        animatorSet.removeAllListeners();
        animatorSet.end();
        animatorSet.cancel();
//        updateIcon(view);
        AnimatedVectorDrawable drawable = (AnimatedVectorDrawable) context.getDrawable(R.drawable.ic_menu_animatable);
        ((ImageView) view).setImageDrawable(drawable);
        drawable.start();
        final int translateY =
                height - 300;
        ObjectAnimator animator = ObjectAnimator.ofFloat(sheet, "translationY", backdropShown ? translateY : 0);
        animator.setDuration(500);
        if (interpolator != null) {
            animator.setInterpolator(interpolator);
        }
        animatorSet.play(animator);
        animator.start();

    }
//      TODO: when user CLICKED , get updateFragment
//    @OnClick(R.id.NestedScrollView)
//    void onClick(){
//
//    }
}
