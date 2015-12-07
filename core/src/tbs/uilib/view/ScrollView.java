package tbs.uilib.view;

import tbs.uilib.HUDManager;
import tbs.uilib.UniversalClickListener;
import tbs.uilib.ValueAnimator;

/**
 * Created by Michael on 2/11/2015.
 */
public class ScrollView extends LinearLayout {
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

    public ScrollView(boolean resizeChildrenWhenParentResized) {
        super(resizeChildrenWhenParentResized);
    }

    @Override
    public void dispose() {
        //Todo fill in
    }


    @Override
    public void draw(float relX, float relY) {
        panAnimator.update();
        lastRelX = relX;
        lastRelY = relY;
        if (!HUDManager.camera.isInFrustum(x, y, w, h))
            return;

        drawBackground(relX, relY);

        int cumulative = 0;
        final float viewTop = relY + y + h;

        for (int i = 0; i < views.size(); i++) {
            final View v = views.get(i);
            if (resizeChildrenWhenParentResized)
                v.w = v.w > w ? w : v.w;

            cumulative += v.h;

            if (cullView(v.getViewBounds()))
                v.draw(relX + x, viewTop - cumulative);
        }
    }

    @Override
    public void addView(View view) {
        super.addView(view);
        print("scrollView adding view > " + views.size());
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
    public boolean drag(float startX, float startY, float dx, float dy) {
        rect.set(lastRelX + x, lastRelY + y, w, h);
        print("checking drag in scrollview > " + rect.toString() + " | > | " + startX + ", " + startY);
        if (rect.contains(startX, startY)) {
            //Todo pan animator

            scrollX += dx;
            scrollY += dy;

            print("scrollViewDrag > " + scrollX + " , " + scrollY);
            return true;
        }
        return false;
    }

    @Override
    public boolean fling(float vx, float vy) {
        rect.set(lastRelX + x, lastRelY + y, w, h);
        if (rect.contains(UniversalClickListener.getInitialTouchDownX(), UniversalClickListener.getInitialTouchDownY())) {
            //Todo pan animator
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

        return false;
    }

    @Override
    public void setTouchDown(boolean touchDown) {

    }
}
