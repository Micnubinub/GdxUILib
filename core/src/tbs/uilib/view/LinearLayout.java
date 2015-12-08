package tbs.uilib.view;

import tbs.uilib.UniversalClickListener;

/**
 * Created by Michael on 3/10/2015.
 */
public class LinearLayout extends ViewGroup {
    public static final int VERTICAL_LAYOUT = 0;
    public static final int HORIZONTAL_LAYOUT = 1;
    protected int layoutDirection;
    protected boolean resizeChildrenWhenParentResized;

    public LinearLayout(boolean resizeChildrenWhenParentResized) {
        this.resizeChildrenWhenParentResized = resizeChildrenWhenParentResized;
    }

    @Override
    public float getHeight() {
        //Todo        switch (layoutDirection) {
//            case VERTICAL:
//                h = 0;
//                for (int i = 0; i < views.size(); i++) {
//                    h += views.get(i).h;
//                }
//                break;
//            case HORIZONTAL:
//                h = views.get(0).h;
//                for (int i = 1; i < views.size(); i++) {
//                    h = Math.max(h, views.get(i).h);
//                }
//                break;
//        }
        return h;
    }

    public boolean isResizeChildrenWhenParentResized() {
        return resizeChildrenWhenParentResized;
    }

    public void setResizeChildrenWhenParentResized(boolean resizeChildrenWhenParentResized) {
        this.resizeChildrenWhenParentResized = resizeChildrenWhenParentResized;
    }

    @Override
    public boolean fling(float vx, float vy) {
//        rect.set(lastRelX + x, lastRelY + y, w, h);
//        if (rect.contains(UniversalClickListener.getInitialTouchDownX(), UniversalClickListener.getInitialTouchDownY())) {
//            //Todo pan animator
//        }
        return false;
    }

    @Override
    public boolean checkCollision(UniversalClickListener.TouchType touchType, int xPos, int yPos) {
        return super.checkCollision(touchType, xPos, yPos);
    }

    public void removeView(View view) {
        if (views != null && views.contains(view))
            views.remove(view);
    }

    public void removeAllViews() {
        if (views != null)
            views.clear();
    }

    @Override
    public float getWidth() {

//  TODO      switch (layoutDirection) {
//            case HORIZONTAL:
//                w = 0;
//                for (int i = 0; i < views.size(); i++) {
//                    w += views.get(i).w;
//                }
//                break;
//            case VERTICAL:
//                w = views.get(0).w;
//                for (int i = 1; i < views.size(); i++) {
//                    w = Math.max(w, views.get(i).w);
//                }
//                break;
//        }
        return w;
    }

    @Override
    public void setWidth(float w) {
        this.w = w;

        if (resizeChildrenWhenParentResized)
            for (int i = 0; i < views.size(); i++) {
                final View v = views.get(i);
                v.w = v.w > w ? w : v.w;
            }
    }

    @Override
    public boolean drag(float startX, float startY, float dx, float dy) {
//        rect.set(lastRelX + x, lastRelY + y, w, h);
//        if (rect.contains(x, y)) {
//            //Todo pan animator
//        }
        return false;
    }

    @Override
    public void draw(float relX, float relY) {
        lastRelX = relX;
        lastRelY = relY;
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

    public void setLayoutDirection(int layoutDirection) {
        this.layoutDirection = layoutDirection;
    }

    @Override
    public void setSize(int w, int h) {
        setWidth(w);
        setHeight(h);
    }


    @Override
    public void dispose() {
        for (View view : views) {
            view.dispose();
        }
    }
}
