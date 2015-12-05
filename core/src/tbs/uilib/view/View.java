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
    public OnClickListener onClickListener;
    public OnTouchListener onTouchListener;
    public Object tag;
    public Background background;
    protected float lastRelX, lastRelY, flingX, flingY;
    protected int id;

    public static void initRenderer(SpriteBatch batch, ShapeRenderer renderer) {
        if (batch.isDrawing())
            try {
                batch.end();
            } catch (Exception e) {
                e.printStackTrace();
            }

        if (!renderer.isDrawing())
            try {
                renderer.begin(ShapeRenderer.ShapeType.Filled);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public static void initBatch(SpriteBatch batch, ShapeRenderer renderer) {
        if (renderer.isDrawing())
            try {
                renderer.end();
            } catch (Exception e) {
                e.printStackTrace();
            }

        if (!batch.isDrawing())
            try {
                batch.begin();
            } catch (Exception e) {
                e.printStackTrace();
            }
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
                        onTouchListener.onTouch(this, touchType, xPos, yPos);
                    break;
                case TOUCH_UP:
                    setTouchDown(false);
                    if (onTouchListener != null)
                        onTouchListener.onTouch(this, touchType, xPos, yPos);
                    break;
                case CLICK:
                    if (onClickListener != null)
                        onClickListener.onClick(this, xPos, yPos);
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

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(int w, int h) {
        this.w = w;
        this.h = h;
    }

    public void setDrawables(ArrayList<Drawable> drawables) {
        this.drawables = drawables;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
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

    public interface OnClickListener {
        void onClick(View view, int x, int y);
    }

    public interface OnTouchListener {
        void onTouch(View view, UniversalClickListener.TouchType touchType, int x, int y);
    }
}
