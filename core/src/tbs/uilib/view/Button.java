package tbs.uilib.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;

import tbs.uilib.Drawable;
import tbs.uilib.HUDManager;
import tbs.uilib.Utility;

public class Button extends View {
    private String text;
    private Color textColor = Color.WHITE;

    public Button(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }



    @Override
    public void draw(float relX, float relY) {
        if (!HUDManager.camera.isInFrustum(x, y, w, h))
            return;
        lastRelX = relX;
        lastRelY = relY;
        drawBackground(relX, relY);

        final SpriteBatch batch = getSpriteBatch(relX, relY);
        for (Drawable drawable : drawables) {
            if (w > 0 && h > 0) {
                batch.draw(drawable.sprite, x, y, w, h);
            }
        }

        Utility.drawCenteredText(batch, text, textColor, 0.5f, relX + x + (w / 2), relY + y + (h / 2));

    }

    public void flushRenderer(Batch shapeRendererOrSpriteBatch) {
        try {
            shapeRendererOrSpriteBatch.flush();
        } catch (Exception e) {
        }
        try {
            ScissorStack.popScissors();
        } catch (Exception e) {
        }
    }

    @Override
    public void dispose() {
        //Todo fill in
    }

    @Override
    public Object getTag() {
        return null;
    }

    @Override
    public int getID() {
        return 0;
    }
}
