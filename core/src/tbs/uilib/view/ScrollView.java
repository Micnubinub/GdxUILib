package tbs.uilib.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import tbs.uilib.HUDManager;
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
    protected final ValueAnimator panAnimator = new ValueAnimator(ValueAnimator.Interpolator.DECELERATE, new ValueAnimator.UpdateListener() {
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
        panAnimator.update();

        if (!HUDManager.camera.isInFrustum(x, y, w, h))
            return;

        //Todo draw background
        for (int i = 0; i < views.size(); i++) {
            final View v = views.get(i);
            if (HUDManager.camera.isInFrustum(v.x, v.y, v.w, v.h))
                v.draw(batch, renderer, w, y);
        }
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
    public void drag(int startX, int startY, int x, int y) {
//Todo setScroll
    }

    @Override
    public void fling(float vx, float vy) {
        if (!HUDManager.isContinueCheckingClicks() || !checkCollision(UniversalClickListener.TouchType.TOUCH_DOWN, x, y))
            return;

        HUDManager.setContinueCheckingClicks(false);
        final double vector = Math.sqrt((vx * vx) + (vy * vy));
        final double vectorScreen = Math.sqrt((w * w) + (h * h));
        panAnimator.setUpdateListener(flingListener);
        panAnimator.setDuration((vector / vectorScreen) * 250);
        panAnimator.start();
        flingX = vx / 6;
        flingY = vy / 6;
        initScrollX = scrollX;
        initScrollY = scrollY;
    }

    @Override
    public void setTouchDown(boolean touchDown) {

    }
}
