package tbs.uilib.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;

import java.util.ArrayList;

import tbs.uilib.Background;
import tbs.uilib.Drawable;
import tbs.uilib.HUDManager;
import tbs.uilib.InteractiveObject;
import tbs.uilib.Rect;
import tbs.uilib.State;
import tbs.uilib.UniversalClickListener;
import tbs.uilib.Viewable;


/**
 * Created by Michael on 2/9/2015.
 */
public abstract class View implements InteractiveObject, Viewable {
    public static final Color color = new Color();
    public static final com.badlogic.gdx.math.Rectangle scissors = new com.badlogic.gdx.math.Rectangle(), clipBounds = new com.badlogic.gdx.math.Rectangle();
    //Todo implement some kind of wrapContent, and fill parent
    public State state = State.TOUCH_UP;
    public float x, y, w, h;
    public ArrayList<Drawable> drawables = new ArrayList<Drawable>();
    public OnClickListener onClickListener;
    public OnTouchListener onTouchListener;
    public Object tag;
    public Background background;
    protected float lastRelX, lastRelY;
    protected int id;

    public final ShapeRenderer initShapeRenderer(SpriteBatch batch, ShapeRenderer renderer, float relX, float relY) {
        if (batch.isDrawing())
            try {
                try {
                    batch.end();
                } catch (Exception e) {
                }
                try {
                    ScissorStack.popScissors();
                } catch (Exception e) {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        if (!renderer.isDrawing()) {
            try {
                clipBounds.set(relX + x, relY + y, w, h);
                ScissorStack.calculateScissors(HUDManager.camera, renderer.getTransformMatrix(), clipBounds, scissors);
                ScissorStack.pushScissors(scissors);
                renderer.begin(ShapeRenderer.ShapeType.Filled);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                renderer.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                ScissorStack.popScissors();
            } catch (Exception e) {
            }
        }

        return renderer;
    }

    @Override
    public boolean fling(float vx, float vy) {
        return false;
    }

    public final SpriteBatch initSpriteBatch(SpriteBatch batch, ShapeRenderer renderer, float relX, float relY) {
        if (renderer.isDrawing())
            try {
                try {
                    renderer.end();
                } catch (Exception e) {
                }
                try {
                    ScissorStack.popScissors();
                } catch (Exception e) {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        if (!batch.isDrawing()) {
            try {
                clipBounds.set(relX + x, relY + y, w, h);
                ScissorStack.calculateScissors(HUDManager.camera, batch.getTransformMatrix(), clipBounds, scissors);
                ScissorStack.pushScissors(scissors);
                batch.begin();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                batch.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                ScissorStack.popScissors();
            } catch (Exception e) {
            }
        }

        return batch;
    }

    @Override
    public boolean checkCollision(UniversalClickListener.TouchType touchType, int xPos, int yPos) {
        //Todo
        if (state == State.DISABLED || ((touchType != UniversalClickListener.TouchType.CLICK) && (onTouchListener == null)))
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

    public ShapeRenderer getShapeRenderer(float relX, float relY) {
        return initShapeRenderer(HUDManager.getSpriteBatch(), HUDManager.getShapeRenderer(), relX, relY);
    }

    public SpriteBatch getSpriteBatch(float relX, float relY) {
        return initSpriteBatch(HUDManager.getSpriteBatch(), HUDManager.getShapeRenderer(), relX, relY);
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
        state = touchDown ? State.TOUCH_DOWN : State.TOUCH_UP;
    }

    public Rect getViewBounds() {
        rect.set(lastRelX + x, lastRelY + y, w, h);
        return rect;
    }

    @Override
    public boolean drag(float startX, float startY, float dx, float dy) {
        return false;
    }

    public float getWidth() {
        return w;
    }

    public void setWidth(float w) {
        this.w = w;
    }

    public float getHeight() {
        return h;
    }

    public void setHeight(float h) {
        this.h = h;
    }

    @Override
    public abstract void dispose();

    public void print(String str) {
        System.out.println(str);
    }

    public void drawBackground(final float relX, final float relY) {
        if (background != null)
            background.drawRelative(relX + x, relY + y, w, h);
    }

    public void setLastRelX(float lastRelX) {
        this.lastRelX = lastRelX;
    }

    public void setLastRelY(float lastRelY) {
        this.lastRelY = lastRelY;
    }

    public interface OnClickListener {
        void onClick(View view, int x, int y);
    }

    public interface OnTouchListener {
        void onTouch(View view, UniversalClickListener.TouchType touchType, int x, int y);
    }
}
