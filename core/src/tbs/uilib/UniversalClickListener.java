package tbs.uilib;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import tbs.uilib.view.View;

/**
 * Created by Michael on 4/18/2015.
 */

public class UniversalClickListener implements InputProcessor, GestureDetector.GestureListener {
    public static boolean continueCheckingClicks, continueCheckingForFling;
    public static boolean isTouchDownSinceLastPan, isInitialTouchHUD;
    protected static int touchDownX1, touchDownY1;
    protected static float initX, initY, flingX, flingY;
    static float initZoom;
    static float tDownX, tDownY;
    private static HUDManager hudManager;

    public UniversalClickListener(HUDManager manager) {
        Gdx.input.setInputProcessor(this);
        hudManager = manager;
    }

    public static boolean isContinueCheckingForFling() {
        return continueCheckingForFling;
    }

    public static void setContinueCheckingForFling(boolean continueCheckingForFling) {
        continueCheckingForFling = continueCheckingForFling;
    }

    public static void click(TouchType touchType, int x, int y) {
        continueCheckingClicks = true;
        y = Gdx.graphics.getHeight() - y;

        if (hudManager != null) {
            switch (touchType) {
                case TOUCH_DOWN:
                case PAN:
                    isInitialTouchHUD = hudManager.checkCollision(touchType, x, y);
                default:
                    hudManager.handleClick(touchType, x, y);
                    break;
            }
        }

        if (!continueCheckingClicks)
            return;

        if (!continueCheckingClicks)
            return;

    }

    public static void confirmClick() {

    }

    public static void confirmFling() {
        isTouchDownSinceLastPan = false;

    }

    public static boolean isContinueCheckingClicks() {
        return continueCheckingClicks;
    }

    public static synchronized void setContinueCheckingClicks(boolean continueCheckingClicks) {
        continueCheckingClicks = continueCheckingClicks;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        initZoom = initZoom < 1 ? 1 : initZoom;
        click(TouchType.TOUCH_DOWN, (int) x, (int) y);
        isTouchDownSinceLastPan = true;
        initZoom = camera.getZoom();
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
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        continueCheckingForFling = true;
        HUDManager.handleFling((int) initX, (int) initY, flingX, flingY);

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
        click(TouchType.TOUCH_DOWN, screenX, screenY);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        click(TouchType.TOUCH_UP, screenX, screenY);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        //Todo handle touch events here, have a boolean called touch intercepted

        return false;
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
        CLICK, TOUCH_DOWN, TOUCH_UP, FLING, DRAG
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
