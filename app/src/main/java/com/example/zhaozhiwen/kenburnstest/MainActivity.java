package com.example.zhaozhiwen.kenburnstest;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.nineoldandroids.view.animation.AnimatorProxy;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements Animator.AnimatorListener {
    private FrameLayout mContainer;
    private ImageView mView;
    private Random random=new Random();
    private static final int ANIM_COUNT = 4;
    private static final int []PHOTO = new int[]{
     R.drawable.photo1,R.drawable.photo2,R.drawable.photo3,R.drawable.photo4,R.drawable.photo5,R.drawable.photo6
    };
    private int mIndex=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContainer=new FrameLayout(this);
        mContainer.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mView=createNewView();
        mContainer.addView(mView);
        setContentView(mContainer);
        nextAnimation();
    }

    private void nextAnimation() {
        AnimatorSet anim=new AnimatorSet();
        final int index=random.nextInt(ANIM_COUNT);
        switch (index){
            case 0:
                anim.playTogether(ObjectAnimator.ofFloat(mView,"scaleX",1.5f,1f),
                        ObjectAnimator.ofFloat(mView, "scaleY", 1.5f, 1f));
                //ofFloat方法中的四个参数分别是，第一个参数是表示对谁实现动画效果，第二个参数表示对什么属性实现动画效果
                //第三个参数以及第四个参数是表示对由什么变成什么。第二个参数的属性一般由如下几种：
                /*
                1、透明度：alpha
               public void setAlpha(float alpha)

                2、旋转度数：rotation、rotationX、rotationY
                public void setRotation(float rotation)
                public void setRotationX(float rotationX)
                public void setRotationY(float rotationY)

                3、平移：translationX、translationY
                 public void setTranslationX(float translationX)
                 public void setTranslationY(float translationY)

                 4.缩放：scaleX、scaleY
                 public void setScaleX(float scaleX)
                 public void setScaleY(float scaleY)
                 5.背景色变化：BackgroundColor

                 */
                break;
            case 1:
                anim.playTogether(ObjectAnimator.ofFloat(mView, "scaleX", 1, 1.5f),
                        ObjectAnimator.ofFloat(mView, "scaleY", 1, 1.5f));
                break;
            case 2:
                AnimatorProxy.wrap(mView).setScaleX(1.5f);//用AnimatorProxy将ImageView进行包装，设置长宽的缩放比例
                AnimatorProxy.wrap(mView).setScaleY(1.5f);//如果不进行包装，也是可以运行的，但是在移动的过程中会产生留白，因为没有进行
                                                          //强行设置长宽。
                anim.playTogether(ObjectAnimator.ofFloat(mView, "translationY", 80f, 0f));
                break;
            default:
                AnimatorProxy.wrap(mView).setScaleX(1.5f);
                AnimatorProxy.wrap(mView).setScaleY(1.5f);
                anim.playTogether(ObjectAnimator.ofFloat(mView, "translationX", 0f, 40f));
                break;

        }
        anim.setDuration(3000);
        anim.addListener(this);
        anim.start();
    }


    private ImageView createNewView() {
        ImageView  img=new ImageView(this);
        img.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        img.setScaleType(ImageView.ScaleType.FIT_XY);
        img.setImageResource(PHOTO[mIndex]);
        mIndex=(mIndex+1<PHOTO.length)?mIndex+1:0;
        return img;
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        mContainer.removeView(mView);

        mView = createNewView();

        mContainer.addView(mView);

        nextAnimation();

    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
