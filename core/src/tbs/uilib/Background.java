package tbs.uilib;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.sun.istack.internal.NotNull;

/**
 * Created by linde on 18-Oct-15.
 */
public class Background {
    private static final Color color = new Color();
    int tmpC;
    private Type type = Type.COLOR;
    private Object background;

    public Background(Object background, @NotNull Type type) {
        this.type = type;
        this.background = background;

        if (background == null)
            defaultBackground();
        else if (Type.COLOR == type) {
            try {
                tmpC = (Integer) background;
            } catch (Exception e) {
                defaultBackground();
                e.printStackTrace();
            }
        }
    }

    public void setBackground(Object background, Type type) {
        this.type = type;
        this.background = background;

        if (background == null)
            defaultBackground();
        else if (Type.COLOR == type) {
            try {
                tmpC = (Integer) background;
            } catch (Exception e) {
                defaultBackground();
                e.printStackTrace();
            }
        }
    }

    private void defaultBackground() {
        type = Type.COLOR;
        background = 0x00000099;
        tmpC = 0x00000099;
    }

    public void drawRelative(final SpriteBatch batch, final ShapeRenderer renderer, float x, float y, float w, float h) {
        switch (type) {
            case COLOR:
                color.set(tmpC);
                renderer.setColor(color);
                renderer.rect(x, y, w, h);
                break;
            case REGION:
                TextureAtlas.AtlasRegion region;
                try {
                    region = (TextureAtlas.AtlasRegion) background;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                if (background == null || region == null)
                    return;

                batch.draw(region, x, y, w, h);
                break;
        }
    }

    enum Type {
        REGION, COLOR
    }
}
