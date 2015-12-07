package tbs.uilib;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

import tbs.uilib.view.View;

/**
 * Created by Michael on 4/18/2015.
 */

public class UniversalClickListener implements InputProcessor, GestureDetector.GestureListener {
    public static boolean isTouchDownSinceLastPan;
    protected static float flingX, flingY;
    static float initZoom;
    static float tDownX, tDownY;
    private static UniversalClickListener universalClickListener;
    private static InputMultiplexer multiplexer;


    private UniversalClickListener() {
        Utility.print("universal click listener declaration");
    }

    public static UniversalClickListener getUniversalClickListener() {
        Utility.print("universal click listener init");
        if (universalClickListener == null) {
            universalClickListener = new UniversalClickListener();
            multiplexer = new InputMultiplexer(universalClickListener, new GestureDetector(universalClickListener));
            Gdx.input.setInputProcessor(multiplexer);
        }

        return universalClickListener;
    }

    public static void click(TouchType touchType, int x, int y) {
        y = Gdx.graphics.getHeight() - y;
        HUDManager.getHUDManager().checkCollision(touchType, x, y);
    }

    public static void confirmClick() {

    }

    public static void confirmFling() {
        isTouchDownSinceLastPan = false;
    }

    public static float getInitialTouchDownX() {
        return tDownX;
    }

    public static float getInitialTouchDownY() {
        return tDownY;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {

        initZoom = initZoom < 1 ? 1 : initZoom;
        click(TouchType.TOUCH_DOWN, (int) x, (int) y);
        isTouchDownSinceLastPan = true;
//Todo        initZoom = camera.getZoom();
        tDownX = x;
        tDownY = y;
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        isTouchDownSinceLastPan = true;
        click(TouchType.CLICK, (int) x, (int) y);
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        //TODO implement longPress
        print("longPress");
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        HUDManager.getHUDManager().fling(flingX, flingY);
        print("fling > " + velocityX + ", " + velocityY);

//        final double vector = Math.sqrt((velocityX * velocityX) + (velocityY * velocityY));
//        final double vectorScreen = Math.sqrt((w * w) + (h * h));
//        isTouchDownSinceLastPan = false;
//        panAnimator.setDuration((vector / vectorScreen) * 250);
//        panAnimator.start();
//        flingX = velocityX / 6;
//        flingY = velocityY / 6;
//        initCamXBeforeFling = camera.position.x;
//        initCamYBeforeFling = camera.position.y;
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        //TODO fix pinch issues

//        camera.setZoom(initZoom * initialPointer1.dst(initialPointer2) / pointer1.dst(pointer2));
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                UILib.x -= 15;
                break;
            case Input.Keys.RIGHT:
                UILib.x += 15;
                break;

            case Input.Keys.UP:
                UILib.y += 15;
                break;

            case Input.Keys.DOWN:
                UILib.y -= 15;
                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
//        click(TouchType.TOUCH_UP, screenX, screenY);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        //Todo handle touch events here, have a boolean called touch intercepted

        screenY = Gdx.graphics.getHeight() - screenY;
        HUDManager.getHUDManager().drag(tDownX, tDownY, screenX - tDownX, screenY - tDownY);

        return false;
    }

    public void print(String s) {
        Utility.print(s);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
//        float zoom = camera.getZoom();
//
//        if (amount > 0)
//            zoom += 0.02f;
//        else if (amount < 0)
//            zoom -= 0.02f;
//
//        camera.setZoom(zoom);
        return false;
    }

    public enum TouchType {
        CLICK, TOUCH_DOWN, TOUCH_UP, FLING, PAN
    }

    public interface OnClickListener {
        void onClick(View view);
    }

    public interface OnTouchListener {
        void onTouch(final View view, final TouchType touchType);

        void onDrag(final int screenX, final int screenY);

        void onFling(final float velocityX, final float velocityY);
    }

}
