package tbs.uilib;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import tbs.uilib.view.base_classes.Camera;

/**
 * Created by Michael on 1/28/2015.
 */
public class Drawable extends GameObject implements Viewable {
    public boolean hasMultipleSides = false;
    public int x, y, w, h;
    public String name;
    public TextureRegion sprite;

    public Drawable(TextureRegion sprite, String name, int x, int y, int w, int h) {
        this.name = name;
        this.sprite = sprite;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
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
    public void draw(float relX, float relY) {

    }

    @Override
    public void dispose() {

    }

    public void setW(int w) {
        this.w = w;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setH(int h) {
        this.h = h;
    }

    public void setHasMultipleSides(boolean hasMultipleSides) {
        this.hasMultipleSides = hasMultipleSides;
    }


    @Override
    public String toString() {
        return String.format("%d : %d, %d >> %d, %d", sprite, x, y, w, h);
    }


}
