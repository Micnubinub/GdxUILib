package tbs.uilib;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;

/**
 * Created by linde on 18-Oct-15.
 */
public class Background {
    private static final Color color = new Color();
    private static final Rectangle scissors = new Rectangle(), clipBounds = new Rectangle();
    int tmpC;
    private Type type = Type.COLOR;
    private Object background;

    public Background(Object background, Type type) {
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


    public void setBackgroundBackgroundSprite(Sprite background) {
        this.type = Type.REGION;
        this.background = background;

        if (background == null)
            defaultBackground();

    }

    public void removeBackground() {
        type = Type.NONE;
        background = null;
    }

    public void setBackgroundBackgroundTexture(Texture background) {
        if (background == null) {
            removeBackground();
        } else {
            this.type = Type.REGION;
            this.background = background;
        }
    }

    public void setBackgroundBackgroundSprite(TextureRegion background) {
        if (background == null) {
            removeBackground();
        } else {
            this.type = Type.REGION;
            this.background = background;
        }

    }

    public void setBackgroundBackgroundColor(int color) {
        this.type = Type.COLOR;
        tmpC = color;
        this.background = color;
    }

    public void setBackgroundBackgroundColor(Color color) {
        if (color == null) {
            removeBackground();
        } else {
            this.type = Type.COLOR;
            tmpC = color.toIntBits();
            this.background = color;
        }
    }

    private void defaultBackground() {
        type = Type.COLOR;
        background = 0x00000099;
        tmpC = 0x00000099;
    }

    public void drawRelative(final SpriteBatch batch, final ShapeRenderer renderer, float x, float y, float w, float h) {
        clipBounds.set(x, y, w, h);



        switch (type) {
            case COLOR:
                Utility.initRenderer(batch, renderer);
                ScissorStack.calculateScissors(HUDManager.camera, renderer.getTransformMatrix(), clipBounds, scissors);
                ScissorStack.pushScissors(scissors);
                color.set(tmpC);
                renderer.setColor(color);
                renderer.rect(x, y, w, h);
                renderer.flush();
                break;
            case REGION:
                Utility.initBatch(batch, renderer);
                ScissorStack.calculateScissors(HUDManager.camera, batch.getTransformMatrix(), clipBounds, scissors);
                ScissorStack.pushScissors(scissors);
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
                batch.flush();
                break;
        }

        ScissorStack.popScissors();
    }

    public enum Type {
        REGION, COLOR, NONE
    }
}
