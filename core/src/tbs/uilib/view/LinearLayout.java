package tbs.uilib.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import tbs.uilib.Screen;

import java.util.ArrayList;

/**
 * Created by Michael on 3/10/2015.
 */
public class LinearLayout extends View {

    protected final ArrayList<View> views = new ArrayList<View>();
    protected int h, w;
    public static final int VERTICAL_LAYOUT = 0;
    public static final int HORIZONTAL_LAYOUT = 1;
    protected int layoutDirection;


    public int getH() {
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

    public void setH(int h) {
        this.h = h;
    }

    @Override
    public void handleFling(float x, float y, float velocityX, float velocityY) {

    }

    @Override
    public void fling(float vx, float vy) {

    }

    public void removeView(View view) {
        if (views != null && views.contains(view))
            views.remove(view);
    }

    public void removeAllViews() {
        if (views != null)
            views.clear();
    }

    public int getW() {

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

    public void setW(int w) {
        print("setW " + w);
        this.w = w;

        for (int i = 0; i < views.size(); i++) {
            final View v = views.get(i);
            v.w = v.w > w ? w : v.w;
        }
    }

    @Override
    public void drag(int startX, int startY, int x, int y) {

    }

    @Override
    public void draw(SpriteBatch batch, ShapeRenderer renderer, float relX, float relY) {
//Todo test        if (!HUDManager.camera.isInFrustum(relX + x, relY + y, w, h))
//            return;
        lastRelX = relX;
        lastRelY = relY;

        int cumulative = 0;
        for (int i = 0; i < views.size(); i++) {
            final View v = views.get(i);
            v.draw(batch, renderer, relX + x, relY + y + cumulative);
            cumulative += v.h;
        }
    }

    public void setLayoutDirection(int layoutDirection) {
        this.layoutDirection = layoutDirection;
    }

    @Override
    public void setHeight(int h) {
        setH(h);
    }

    @Override
    public void setWidth(int w) {
        setW(w);
    }

    public void addView(View view) {
        if (view == null || views.contains(view))
            return;

        views.add(view);
    }

    @Override
    public void dispose() {
        for (View view : views) {
            view.dispose();
        }
    }
}
