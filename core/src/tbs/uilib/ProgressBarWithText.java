package tbs.uilib;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Michael on 1/31/2015.
 */
public class ProgressBarWithText extends GameObject {
    public Color background = Color.BLACK, progressColor = Color.BLUE;
    public int progress, innerR, intermidLength, r;
    private Sprite backgroundCap, innerCap;
    private Texture backgroundIntermid, innerIntermid;
    private float max = 100;
    private String text;
    private boolean complete = false;
    private ProgressListener listener;

    public ProgressBarWithText(String text, int max, Color background, Color progressColor, int w, int h) {
        this.text = text;
        this.max = max;
        this.progressColor = progressColor;
        this.background = background;
        this.w = w;
        this.h = h;
        getSprites();
    }

    @Override
    public void drawRelative(SpriteBatch batch, int x, int y) {
        draw(batch);
    }

    private void getSprites() {
        final Pixmap midPix = new Pixmap(1, 1, Pixmap.Format.RGBA4444);

        midPix.setColor(progressColor);
        midPix.fill();
        innerIntermid = new Texture(midPix);

        midPix.setColor(background);
        midPix.fill();
        backgroundIntermid = new Texture(midPix);

        midPix.dispose();

        r = h / 2;
        intermidLength = w - h;

        innerR = Math.round(r * 0.9f);

        final Pixmap p = new Pixmap(r, h, Pixmap.Format.RGBA4444);
        p.setColor(background);
        p.fillCircle(0, r, r);
        backgroundCap = new Sprite(new Texture(p));
        p.dispose();

        final Pixmap p1 = new Pixmap(innerR, innerR * 2, Pixmap.Format.RGBA4444);
        p1.setColor(progressColor);
        p1.fillCircle(0, innerR, innerR);
        innerCap = new Sprite(new Texture(p1));
        p1.dispose();

        backgroundCap.setSize(r, h);
        innerCap.setSize(innerR, innerR + innerR);

    }

    public void setProgressColor(Color progressColor) {
        this.progressColor = progressColor;
        getSprites();
    }

    public void setBackground(Color background) {
        this.background = background;
        getSprites();
    }

    public void reset() {
        complete = false;
        progress = 0;
    }

    public void setProgress(int progress) {
        if (complete)
            return;

        progress = progress < 0 ? 0 : progress;
        progress = progress > max ? (int) max : progress;

        complete = progress >= max;

        if (listener != null) {
            if (complete)
                listener.onComplete();
            else
                listener.onProgressChanged(progress, max);
        }
        this.progress = progress;
    }

    public void setProgressChangeListener(ProgressListener listener) {
        this.listener = listener;
    }

    public boolean isComplete() {
        return complete;
    }

    public boolean isRunning() {
        return !complete;
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (!Screen.camera.isInFrustum(x, y, w, h))
            return;

        backgroundCap.setRotation(180);
        backgroundCap.setPosition(x, y);

        backgroundCap.draw(batch);

        backgroundCap.setRotation(0);
        backgroundCap.setPosition(x + w - r, y);
        backgroundCap.draw(batch);

        batch.draw(backgroundIntermid, x + r, y, w - h, h);

        final int diff = r - innerR;
        final float innerIntermidLength = intermidLength * (progress / max);

        innerCap.setRotation(180);
        innerCap.setPosition(x + diff, y + diff);
        innerCap.draw(batch);

        innerCap.setRotation(0);
        innerCap.setPosition(x + r + innerIntermidLength, y + diff);
        innerCap.draw(batch);

        batch.draw(innerIntermid, x + r, y + diff, innerIntermidLength, h - diff - diff);
        Utility.drawCenteredText(batch, "progress : " + progress + "%", Color.WHITE, 0.25f, x + (w / 2), y + (1.25f * h));

    }


    @Override
    public void drawBackground(SpriteBatch batch) {
    }

    public String getText() {
        return text == null ? "" : text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public interface ProgressListener {
        void onProgressChanged(int progress, float max);

        void onComplete();

        void onStart();
    }
}
