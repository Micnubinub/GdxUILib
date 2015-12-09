package tbs.uilib.view;

import tbs.uilib.HUDManager;
import tbs.uilib.UniversalClickListener;
import tbs.uilib.ValueAnimator;

/**
 * Created by Michael on 2/11/2015.
 */
public class ScrollView extends LinearLayout {
    protected int scrollX, scrollY, initScrollX, initScrollY, cumulative;
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
    protected float flingX, flingY, viewTop;
    protected boolean isTouchDownSinceLastPan;
    protected ValueAnimator.UpdateListener flingListener = new ValueAnimator.UpdateListener() {
        @Override
        public void update(double animatedValue) {
            if (!isTouchDownSinceLastPan) {
                //Todo
                setScrollX(Math.round((float) (initScrollX + (animatedValue * flingX))));
                setScrollY(Math.round((float) (initScrollY + (animatedValue * flingY))));
            }
        }

        @Override
        public void onAnimationStart() {
            initScrollX = scrollX;
            initScrollY = scrollY;
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
    public void draw(float relX, float relY, float parentRight, float parentTop) {
        panAnimator.update();
        lastRelX = relX;
        lastRelY = relY;
        if (!HUDManager.camera.isInFrustum(x, y, w, h))
            return;

        drawBackground(relX, relY);

        viewTop = relY + y + h;

        for (int i = 0; i < views.size(); i++) {
            final View v = views.get(i);
            if (resizeChildrenWhenParentResized)
                v.w = v.w > w ? w : v.w;

            cumulative += v.h;
//            v.x += scrollX;
//            v.y -= scrollY;
            v.setLastRelX(relX + x + scrollX);
            v.setLastRelY(viewTop - cumulative - scrollY);

            if (cullView(v))
                v.draw(relX + x + scrollX, viewTop - cumulative - scrollY, Math.min(relX + x + scrollX + w, parentRight), Math.min(viewTop - cumulative - scrollY + h, parentTop));
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
        print("setCrollY>" + scrollY);
        this.scrollY = scrollY;
    }

    @Override
    public boolean drag(float startX, float startY, float dx, float dy) {
        rect.set(lastRelX + x, lastRelY + y, w, h);
        if (rect.contains(startX, startY)) {
            //Todo pan animator
            scrollX += dx;
            scrollY += dy;
            return true;
        }
        return false;
    }

    @Override
    public boolean fling(float vx, float vy) {
        rect.set(lastRelX + x, lastRelY + y, w, h);
        print("fling sv > " + vx + ", " + vy);

        if (rect.contains(UniversalClickListener.getInitialTouchDownX(), UniversalClickListener.getInitialTouchDownY())) {
            //Todo pan animator
            HUDManager.setContinueCheckingClicks(false);
            final double vector = Math.sqrt((vx * vx) + (vy * vy));
            print("vector > " + vector);
            final double vectorScreen = Math.sqrt((w * w) + (h * h));
            panAnimator.setDuration((vector / vectorScreen) * 250);
            if (panAnimator.duration > 65) {
                panAnimator.setUpdateListener(flingListener);
                panAnimator.start();
                flingX = vx / 6;
                flingY = vy / 6;
            }
            initScrollX = scrollX;
            initScrollY = scrollY;
        }
        return false;
    }

    @Override
    public void setTouchDown(boolean touchDown) {

    }
}
