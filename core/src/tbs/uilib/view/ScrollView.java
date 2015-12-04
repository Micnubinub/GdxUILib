package tbs.uilib.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import tbs.uilib.HUDManager;
import tbs.uilib.Screen;
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
    private LayoutDirection layoutDirection = LayoutDirection.VERTICAL;

    @Override
    public void dispose() {
        //Todo fill in
    }

    @Override
    public void handleFling(int x, int y, float velocityX, float velocityY) {
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
    public void setLayoutDirection(LayoutDirection layoutDirection) {
        this.layoutDirection = layoutDirection;
    }

    @Override
    public boolean checkCollision(Screen.TouchType touchType, int xPos, int yPos) {
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

    public void fling(float startX, float startY, float vX, float vY) {
        //Todo fill in
    }

    @Override
    public void drawRelative(SpriteBatch batch, int relX, int relY) {
        //Todo fill in
//        for (Drawable drawable : drawables) {
//            if (w > 0 && h > 0) {
//                batch.draw(drawable.sprite, relX + x, relY + y, w, h);
//            }
//        }

        lastRelX = relX;
        lastRelY = relY;

        for (int i = 0; i < views.size(); i++) {
            final View v = views.get(i);
            if (checkCollision(v.x, v.y, v.w, v.h) && HUDManager.camera.isInFrustum(v.x, v.y, v.w, v.h))
                v.drawRelative(batch, relX + x, relY + y);
        }
        getH();
    }

    @Override
    public void draw(SpriteBatch batch) {
        animator.update();

        if (!HUDManager.camera.isInFrustum(x, y, w, h))
            return;

        //Todo draw background
        for (int i = 0; i < views.size(); i++) {
            final View v = views.get(i);
            if (checkCollision(v.x, v.y, v.w, v.h) && HUDManager.camera.isInFrustum(v.x, v.y, v.w, v.h))
                v.drawRelative(batch, w, y);
        }
        getH();
    }

    @Override
    public boolean checkInGameCollision(int xPos, int yPos) {
        return false;
    }


    @Override
    public void setTouchDown(boolean touchDown) {

    }
}
