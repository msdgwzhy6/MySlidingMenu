package com.example.linxj.myslidingmenu;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.SweepGradient;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by linxj on 2015/11/17.
 */
public class SlidingMenu extends HorizontalScrollView {
    private boolean once = true;
    private View menu;
    private View content;
    private LinearLayout mWapper;
    private int menuWidth;
    private int contentWidth;
    private int screenWidth;
    private int screenHeight;
    private int paddingLeft = 50;
            //(float)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,20,getResources().getDisplayMetrics());

    public SlidingMenu(Context context) {
        this(context, null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics out = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(out);
        screenWidth = out.widthPixels;
        screenHeight = out.heightPixels;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if(once){
            //int count = getChildCount();
           // for(int i = 0;i < count ;i++){
            mWapper = (LinearLayout) getChildAt(0);
            menu = (ViewGroup) mWapper.getChildAt(0);
            content = (ViewGroup) mWapper.getChildAt(1);

            menuWidth = screenWidth - paddingLeft;
            contentWidth = screenWidth;
            menuWidth = menu.getLayoutParams().width = screenWidth
                    - paddingLeft;
            content.getLayoutParams().width = screenWidth;
           //menu.measure(menuWidth,screenHeight);

            once = false;
            }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch( ev.getAction()){
            case MotionEvent.ACTION_DOWN:
               break;
            case MotionEvent.ACTION_MOVE:break;
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                if(scrollX<screenWidth/2){
                    this.smoothScrollTo(0,screenHeight);
                }else{
                    this.smoothScrollTo(menuWidth,screenHeight);
                }
            return true;

        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        this.scrollTo(menuWidth, 0);

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        //l与getScrollX相似
        super.onScrollChanged(l, t, oldl, oldt);
        float scale = (float)(1.0*l)/menuWidth; // 计算1.0到0
        float rightScale = 0.7f + 0.3f * scale;
        float leftScale = 1.0f - scale * 0.3f;
        float leftAlpha = 0.6f + 0.4f * (1 - scale);

        ObjectAnimator animator = ObjectAnimator.ofFloat(menu,"TranslationX",menuWidth * scale * 0.8f,0);
       // animator.start();
       // ViewHelper.setTranslationX(mMenu, mMenuWidth * scale * 0.8f);

        content.setPivotX(0);
        content.setPivotY(screenHeight/2);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(menu,"scaleX",leftScale,1);
        //animator1.start();
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(menu,"scaleY",leftScale,1);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(content,"scaleX",rightScale,1);
       // ObjectAnimator animator4 = ObjectAnimator.ofFloat(content,"scaleY",rightScale,1);
        ObjectAnimator animator5 = ObjectAnimator.ofFloat(menu,"alpha",leftAlpha,1);
        AnimatorSet set = new AnimatorSet();
       // set.setDuration(500);
       // set.setInterpolator(new BounceInterpolator());
        set.playTogether(animator,animator1, animator2, animator3, animator5);
        set.start();

    }
}
