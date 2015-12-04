package tbs.uilib.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import tbs.uilib.Drawable;
import tbs.uilib.HUDManager;
import tbs.uilib.State;
import tbs.uilib.Utility;

public class Button extends View {
    private String text;
    private Color textColor = Color.WHITE;

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
    public void draw() {

    }

    @Override
    public void drawRelative(float relX, float relY) {

    }

    @Override
    public void fling(float vx, float vy) {

    }

    @Override
    public void handleFling(float x, float y, float velocityX, float velocityY) {

    }

    @Override
    public void draw(SpriteBatch batch) {
        if (!HUDManager.camera.isInFrustum(x, y, w, h))
            return;

        switch (backgroundType) {
            case INT_REGION:

                break;
            case STRING_REGION:

                break;
            case TEXTURE:

                break;
            default:
        }

        for (Drawable drawable : drawables) {
            if (w > 0 && h > 0) {
                batch.draw(drawable.sprite, x, y, w, h);
            }
        }

        Utility.drawCenteredText(batch, (state == State.TOUCH_DOWN) ? "touchDown" : text, textColor, 0.5f, x + (w / 2), y + (h / 2));
    }

    @Override
    public void drawRelative(SpriteBatch batch, int relX, int relY) {
        lastRelX = relX;
        lastRelY = relY;
        if (!HUDManager.camera.isInFrustum(x, y, w, h))
            return;

        for (Drawable drawable : drawables) {
            if (w > 0 && h > 0) {
                batch.draw(drawable.sprite, relX + x, relY + y, w, h);
            }
        }

        Utility.drawCenteredText(batch, text, textColor, 0.25f, relX + x + (w / 2), relY + y + (h / 2));
    }

    @Override
    public void dispose() {
        //Todo fill in
    }

    @Override
    public void drawBackground() {
        super.drawBackground();
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
