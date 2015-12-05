package tbs.uilib;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by Michael on 2/26/2015.
 */
public interface Viewable {
    void setState(State state);

    void setTouchDown(boolean touchDown);

    Object getTag();

    int getID();

    void draw(final SpriteBatch batch, final ShapeRenderer renderer, final float relX, final float relY);

    void dispose();
}
