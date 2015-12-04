package tbs.uilib.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import tbs.uilib.*;

import java.util.ArrayList;


/**
 * Created by Michael on 2/9/2015.
 */
public abstract class View implements InteractiveObject, Viewable {
    public static final Color color = new Color();
    //Todo implement some kind of wrapContent, and fill parent
    public State state = State.TOUCH_UP;
    public float x, y, w, h, initCamXBeforeFling, initCamYBeforeFling;
    public ArrayList<Drawable> drawables = new ArrayList<Drawable>();
    public UniversalClickListener.OnClickListener onClickListener;
    public UniversalClickListener.OnTouchListener onTouchListener;
    public Object tag;
    public Background background;
    protected float lastRelX, lastRelY, flingX, flingY;
    protected int id;

    @Override
    public void fling(float vx, float vy) {
        final double vector = Math.sqrt((vx * vx) + (vy * vy));
        final double vectorScreen = Math.sqrt((w * w) + (h * h));
        UniversalClickListener.confirmFling();
//   Todo     panAnimator.setDuration((vector / vectorScreen) * 250);
//        panAnimator.start();
        flingX = vx / 6;
        flingY = vy / 6;
        initCamXBeforeFling = x;
        initCamYBeforeFling = y;
    }

    @Override
    public boolean checkCollision(UniversalClickListener.TouchType touchType, int xPos, int yPos) {
        if (state == State.DISABLED)
            return false;
        rect.set(lastRelX + x, lastRelY + y, w, h);

        final boolean clicked = rect.contains(xPos, yPos);

        if (clicked && !(state == State.DISABLED)) {
            switch (touchType) {
                case TOUCH_DOWN:
                    setTouchDown(true);
                    if (onTouchListener != null)
                        onTouchListener.onTouch(this, touchType);
                    break;
                case CLICK:
                    if (onClickListener != null)
                        onClickListener.onClick(this);
                    break;
            }
        }
        return clicked;
    }


    @Override
    public boolean checkCollision(int xPos, int yPos, int width, int height) {
        rect.set(x, y, w, h);
        rect2.set(xPos, yPos, width, height);
        return rect.contains(rect2);
    }

    public State getState() {
        return state;
    }

    @Override
    public void setState(State state) {
        this.state = state;
    }

    @Override
    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    @Override
    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    public void setHeight(int h) {
        this.h = h;
    }

    public void setWidth(int w) {
        this.w = w;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setDrawables(ArrayList<Drawable> drawables) {
        this.drawables = drawables;
    }

    public void setOnClickListener(UniversalClickListener.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public void setTouchDown(boolean touchDown) {
        setState(touchDown ? State.TOUCH_DOWN : State.TOUCH_UP);
    }

    @Override
    public abstract void dispose();

    public void print(String str) {
        System.out.println(str);
    }


    public void drawBackground(final SpriteBatch batch, final ShapeRenderer renderer) {
        background.drawRelative(batch, renderer, x, y, w, h);
    }
}
