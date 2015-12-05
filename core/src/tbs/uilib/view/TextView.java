package tbs.uilib.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import tbs.uilib.Drawable;
import tbs.uilib.HUDManager;
import tbs.uilib.Screen;
import tbs.uilib.Utility;


/**
 * Created by Michael on 2/9/2015.
 */
public class TextView extends View {
    //Todo make it so that the text view cuts off the drawing once the text drawing position exceeds the height.. ellipsize
    public int padding, maxNumLines;
    protected String text;
    protected float textScale = 0.2f, startingPoint;
    protected Color textColor = new Color(0xffffffff);
    protected float textHeight;
    protected int w, h, x, y;
    protected String[] textStrings = {};
    protected Gravity gravity;

    public TextView(int w) {
        this.w = w;
    }

    public TextView(Color textColor, int w) {
        this.textColor = textColor;
        this.w = w;
    }

    public TextView(String text, int w) {
        setText(text);
        this.w = w;
    }

    public TextView(String text, Color textColor, int w) {
        this.textColor = textColor;
        setText(text);
        this.w = w;
    }

    public void setW(int w) {
        this.w = w;
        getTextStrings();
    }

    @Override
    public void setX(int x) {
        this.x = x;
        getTextStrings();
    }

    public void setH(int h) {
        this.h = h;
        getTextStrings();
    }

    @Override
    public void setY(int y) {
        this.y = y;
        getTextStrings();
    }

    public void setMaxNumLines(int maxNumLines) {
        this.maxNumLines = maxNumLines;
    }

    public void setTextScale(float textScale) {
        this.textScale = textScale;
    }

    public void setText(String text) {
        this.text = text;
        getTextStrings();
    }

    @Override
    public void drag(int startX, int startY, int x, int y) {

    }

    public void getTextStrings() {
        if (text == null || text.length() < 1)
            return;
// Todo       Screen.font.setScale(textScale);
//        final BitmapFont.TextBounds bounds = Screen.font.getBounds(text);
//
//        int numLines = (int) Math.ceil(bounds.width / (w));
//        numLines = numLines > 25 ? 25 : numLines;
//
//        final int numLettersInOneLine = (int) Math.ceil(text.length() / numLines);
//        textHeight = bounds.height;
//        h = Math.round((textHeight * numLines) + (padding * (numLines + 1)));
//        startingPoint = (y + h - padding) - (textHeight / 2);
//        textStrings = new String[numLines];
//        for (int i = 0; i < numLines; i++) {
//            String out = "";
//            if (!(numLines - 1 == i)) {
//                int index = i * numLettersInOneLine;
//                if (!(index + numLettersInOneLine > text.length()))
//                    out = text.substring(index, index + numLettersInOneLine);
//            } else {
//                out = text.substring(i * numLettersInOneLine, text.length());
//            }
//            textStrings[i] = out;
//        }
    }


    protected void drawText(SpriteBatch batch, int relX, int relY) {
        for (int i = 0; i < textStrings.length; i++) {
            if (gravity == null)
                gravity = Gravity.LEFT;
            switch (gravity) {
                case CENTER:

                    break;
                case LEFT:

                    break;
                case RIGHT:

                    break;
                default:
            }
            Utility.drawCenteredText(batch, textStrings[i], textColor, textScale, relX + x + (w / 2), relY + startingPoint - (i * textHeight));
        }
    }

    @Override
    public void draw(SpriteBatch batch, ShapeRenderer renderer, float relX, float relY) {
        lastRelX = relX;
        lastRelY = relY;
        if (!HUDManager.camera.isInFrustum(relX + x, relY + y, w, h))
            return;
        for (Drawable drawable : drawables) {
            if (w > 0 && h > 0) {
                drawable.draw(batch, renderer, relX, relY);
            }
        }

        drawText(Screen.getBatch(), 0, 0);
    }

    public void setGravity(Gravity gravity) {
        this.gravity = gravity;
        getTextStrings();
    }

    @Override
    public Object getTag() {
        return tag;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public void dispose() {
        textStrings = null;
    }


    public enum Gravity {
        CENTER, LEFT, RIGHT
    }
}
