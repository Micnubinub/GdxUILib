package tbs.uilib;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * Created by linde on 17-Oct-15.
 */
public class TestDrawable implements Drawable {
    private Texture texture;

    public TestDrawable(int backrgroundColor) {
        final Pixmap p = new Pixmap(1, 1, Pixmap.Format.RGBA4444);
        p.setColor(backrgroundColor);
        p.fill();
        texture = new Texture(p);
    }

    @Override
    public void draw(Batch batch, float x, float y, float width, float height) {
        batch.draw(texture, x, y, width, height);
    }

    @Override
    public float getLeftWidth() {
        return 0;
    }

    @Override
    public void setLeftWidth(float leftWidth) {

    }

    @Override
    public float getRightWidth() {
        return 0;
    }

    @Override
    public void setRightWidth(float rightWidth) {

    }

    @Override
    public float getTopHeight() {
        return 0;
    }

    @Override
    public void setTopHeight(float topHeight) {

    }

    @Override
    public float getBottomHeight() {
        return 0;
    }

    @Override
    public void setBottomHeight(float bottomHeight) {

    }

    @Override
    public float getMinWidth() {
        return 0;
    }

    @Override
    public void setMinWidth(float minWidth) {

    }

    @Override
    public float getMinHeight() {
        return 0;
    }

    @Override
    public void setMinHeight(float minHeight) {

    }
}
