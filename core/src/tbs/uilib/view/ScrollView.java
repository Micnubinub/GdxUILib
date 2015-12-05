package tbs.uilib.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import tbs.uilib.HUDManager;
import tbs.uilib.Screen;
import tbs.uilib.UniversalClickListener;
import tbs.uilib.ValueAnimator;

import java.util.ArrayList;

/**
 * Created by Michael on 2/11/2015.
 */
public class ScrollView extends LinearLayout {

    public final ArrayList<View> views = new ArrayList<View>();
    public int x, y;
    protected int scrollX, scrollY, initScrollX, initScrollY;
    protected final ValueAnimator animator = new ValueAnimator(ValueAnimator.Interpolator.DECELERATE, new ValueAnimator.UpdateListener() {
        @Override
        public void update(double animatedValue) {
//Todo setScroll(x,y);
//            if (!isTouchDownSinceLastPan)
//                camera.setPosition((float) (initX - (animatedValue * flingX)), (float) (initY + (animatedValue * flingY)));
        }

        @Override
        public void onAnimationStart() {
            initScrollX = scrollX;
            initScrollY = scrollY;
        }

        @Override
        public void onAnimationFinish() {

        }
    });
    protected float flingX, flingY;
    protected boolean isTouchDownSinceLastPan;
    protected ValueAnimator.UpdateListener flingListener = new ValueAnimator.UpdateListener() {
        @Override
        public void update(double animatedValue) {
            if (!isTouchDownSinceLastPan) {
                setScrollY(Math.round((float) (initScrollY + (animatedValue * flingY))));
                setScrollX(Math.round((float) (initScrollX - (animatedValue * flingX))));
            }
        }

        @Override
        public void onAnimationStart() {

        }

        @Override
        public void onAnimationFinish() {

        }
    };

    @Override
    public void dispose() {
        //Todo fill in
    }

    @Override
    public void draw(SpriteBatch batch, ShapeRenderer renderer, float relX, float relY) {
        animator.update();

        if (!HUDManager.camera.isInFrustum(x, y, w, h))
            return;

        //Todo draw background
        for (int i = 0; i < views.size(); i++) {
            final View v = views.get(i);
            if (HUDManager.camera.isInFrustum(v.x, v.y, v.w, v.h))
                v.draw(batch, renderer, w, y);
        }
    }



    @Override
    public boolean checkCollision(UniversalClickListener.TouchType touchType, int xPos, int yPos) {
        rect.set(x, y, w, h);
        return rect.contains(xPos, yPos);
    }

    public int getScrollX() {
        return scrollX;
    }

    public void setScrollX(int scrollX) {
        this.scrollX = scrollX;
    }

    public int getScrollY() {
        return scrollY;
    }

    public void setScrollY(int scrollY) {
        this.scrollY = scrollY;
    }

    @Override
    public boolean checkCollision(int xPos, int yPos, int width, int height) {
        rect.set(x, y, w, h);
        rect2.set(xPos, yPos, width, height);
        return rect.contains(rect2);
    }

    @Override
    public void drag(int startX, int startY, int x, int y) {
//Todo setScroll
    }

    @Override
    public void handleFling(float x, float y, float velocityX, float velocityY) {
        super.handleFling(x, y, velocityX, velocityY);
        if (!Screen.isContinueCheckingForFling() || !checkCollision(Screen.TouchType.TOUCH_DOWN, x, y))
            return;

        Screen.setContinueCheckingForFling(false);
        final double vector = Math.sqrt((velocityX * velocityX) + (velocityY * velocityY));
        final double vectorScreen = Math.sqrt((w * w) + (h * h));
        panAnimator.setUpdateListener(flingListener);
        panAnimator.setDuration((vector / vectorScreen) * 250);
        panAnimator.start();
        flingX = velocityX / 6;
        flingY = velocityY / 6;
        initScrollX = scrollX;
        initScrollY = scrollY;
    }

    @Override
    public void fling(float vx, float vy) {
        super.fling(vx, vy);
        if (!Screen.isContinueCheckingForFling() || !checkCollision(Screen.TouchType.TOUCH_DOWN, x, y))
            return;

        Screen.setContinueCheckingForFling(false);
        final double vector = Math.sqrt((velocityX * velocityX) + (velocityY * velocityY));
        final double vectorScreen = Math.sqrt((w * w) + (h * h));
        panAnimator.setUpdateListener(flingListener);
        panAnimator.setDuration((vector / vectorScreen) * 250);
        panAnimator.start();
        flingX = velocityX / 6;
        flingY = velocityY / 6;
        initScrollX = scrollX;
        initScrollY = scrollY;
    }

    @Override
    public void setTouchDown(boolean touchDown) {

    }
}
