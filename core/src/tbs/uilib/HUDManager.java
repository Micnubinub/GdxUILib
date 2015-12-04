package tbs.uilib;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import tbs.uilib.view.View;

import java.util.ArrayList;

/**
 * Created by Michael on 2/20/2015.
 */
public class HUDManager implements InteractiveObject, Viewable {
    public static HUDCamera camera;
    private static ArrayList<View> views = new ArrayList<View>();
    //Todo show dialog

    public HUDManager() {
        camera = new HUDCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false, camera.viewportWidth, camera.viewportHeight);
        camera.position.x = camera.viewportWidth / 2;
        camera.position.y = camera.viewportHeight / 2;
        camera.update();
    }

    public static void addView(View view) {

        if (!views.contains(view))
            views.add(view);
    }

    public static int getViewIndex(View view) {
        return views.indexOf(view);
    }

    public static void removeView(View view) {
        if (views.contains(view))
            views.remove(view);
    }

    public static void removeViewByID(int iD) {
        for (int i = 0; i < views.size(); i++) {
            final View v = views.get(i);
            if (v.getID() == iD)
                views.remove(v);
        }
    }

    public static void removeViewByTag(Object tag) {
        for (int i = 0; i < views.size(); i++) {
            final View v = views.get(i);
            if (tag.equals(v.getTag()))
                views.remove(v);
        }
    }


    public static void print(String str) {
        System.out.println(str);
    }


    @Override
    public void handleClick(UniversalClickListener.TouchType touchType, int x, int y) {
        switch (touchType) {
            case CLICK:
                //Todo check this loop
                for (int i = (views.size() - 1); i >= 0; i--) {
                    if (!Screen.isContinueCheckingClicks())
                        return;

                    final View view = views.get(i);

                    if (view.checkCollision(touchType, x, y)) {
                        Screen.setContinueCheckingClicks(false);
                        //Todo UniversalClickListener.handleClick(x,y,);
                    }
                }
                break;
            case TOUCH_DOWN:
                setTouchDown(x, y);
                break;
            case TOUCH_UP:
                setTouchUp();
                break;
        }
    }

    @Override
    public void drawRelative(float x, float y) {

    }

    @Override
    public boolean checkCollision(UniversalClickListener.TouchType touchType, int xPos, int yPos) {
        return false;
    }

    @Override
    public void fling(float vx, float vy) {
        for (View view : views) {
            if (Screen.isContinueCheckingForFling())
                view.handleFling(x, y, velocityX, velocityY);
        }
    }


    @Override
    public void drawRelative(int x, int y) {
        final SpriteBatch batch = Screen.getBatch();
        batch.end();
        final Matrix4 proj = batch.getProjectionMatrix().cpy();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        if (views == null || views.size() < 1)
            return;

        for (int i = 0; i < views.size(); i++) {
            views.get(i).draw();
        }

        Utility.drawCenteredText(batch, String.valueOf(Gdx.graphics.getFramesPerSecond()) + "fps", Color.WHITE, 0.25f, 1800, 60);
        batch.setProjectionMatrix(proj);
    }


    @Override
    public boolean checkCollision(int xPos, int yPos, int width, int height) {
        return false;
    }

    @Override
    public void setState(State state) {

    }

    @Override
    public void setTouchDown(boolean touchDown) {

    }

    @Override
    public Object getTag() {
        return null;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void draw(SpriteBatch batch, ShapeRenderer renderer, float relX, float relY) {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void drag(int startX, int startY, int x, int y) {
    }

    public void setTouchDown(int x, int y) {
        for (int i = 0; i < views.size(); i++) {
            final View view = views.get(i);

            if (view.getState() != State.DISABLED && view.checkCollision(Screen.TouchType.TOUCH_DOWN, x, y)) {
                view.setState(State.TOUCH_DOWN);
                return;
            }
        }
    }

    public void setTouchUp() {
        for (int i = 0; i < views.size(); i++) {
            final View view = views.get(i);

            if (view.getState() != State.DISABLED) {
                view.setState(State.TOUCH_UP);
            }
        }
    }


    public static class HUDCamera extends OrthographicCamera {
        private static final Rect r1 = new Rect(), r2 = new Rect();

        public HUDCamera(float viewportWidth, float viewportHeight) {
            super(viewportWidth, viewportHeight);
        }

        public boolean isInFrustum(Rect rect) {
            r1.set(camera.position.x, camera.position.y, viewportWidth, viewportHeight);
            return r1.contains(rect);
        }

        public boolean isInFrustum(float x, float y, float w, float h) {
            r1.set(camera.position.x, camera.position.y, viewportWidth, viewportHeight);
            r2.set(camera.position.x + x, camera.position.y + y, w, h);
            return r1.contains(r2);
        }
    }
}
