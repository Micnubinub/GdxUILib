package tbs.uilib.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
    public void fling(float vx, float vy) {

    }

    @Override
    public void drag(int startX, int startY, int x, int y) {

    }

    @Override
    public void draw(SpriteBatch batch, ShapeRenderer renderer, float relX, float relY) {
        if (!HUDManager.camera.isInFrustum(x, y, w, h))
            return;

        if (background != null)
            background.drawRelative(batch, renderer, x, y, w, h);

        for (Drawable drawable : drawables) {
            if (w > 0 && h > 0) {
                batch.draw(drawable.sprite, x, y, w, h);
            }
        }

        Utility.drawCenteredText(batch, (state == State.TOUCH_DOWN) ? "touchDown" : text, textColor, 0.5f, x + (w / 2), y + (h / 2));
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
