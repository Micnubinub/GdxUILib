package tbs.uilib.view;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import tbs.uilib.Screen;
import tbs.uilib.UniversalClickListener;


/**
 * Created by Michael on 1/31/2015.
 */
public class ProgressBar extends View {
    public int progress, backgroundColor, progressColor;
    private float max = 100, innerR, intermidLength, r, diff;
    private boolean complete = false;
    private ProgressListener listener;

    public ProgressBar(int max, int background, int progressColor, int w, int h) {
        this.max = max;
        this.progressColor = progressColor;
        this.backgroundColor = background;
        this.w = w;
        this.h = h;
        setRadii();
    }

    private void setRadii() {
        r = Math.min(w, h);
        innerR = r * 0.92f;
        diff = r - innerR;
    }

    @Override
    public void fling(float vx, float vy) {

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean checkCollision(UniversalClickListener.TouchType touchType, int xPos, int yPos) {
        return false;
    }

    @Override
    public void handleFling(float x, float y, float velocityX, float velocityY) {

    }

    @Override
    public void drawRelative(float relX, float relY) {
//  Todo      if (!HUDManager.camera.isInFrustum(x, y, w, h))
//            return;

        final ShapeRenderer renderer = Screen.getRenderer();
        lastRelX = relX;
        lastRelY = relY;

        color.set(backgroundColor);
        renderer.setColor(color);
        renderer.circle(relX + x + r, relY + y + r, r);
        renderer.rect(relX + x + r, relY + y, w - r - r, h);
        renderer.circle(relX + x + w - r, relY + y, r);

        intermidLength = w - h;
        final float innerIntermidLength = intermidLength * (progress / max);

        color.set(progressColor);
        renderer.setColor(color);
        renderer.circle(relX + x + r, relY + y + r, innerR);
        renderer.circle(relX + x + r + innerIntermidLength, relY + y + diff, innerR);
        renderer.rect(relX + x + r, relY + y + diff, innerIntermidLength, h - diff - diff);
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
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

    @Override
    public void setHeight(int h) {
        super.setHeight(h);
        setRadii();
    }

    @Override
    public void setWidth(int w) {
        super.setWidth(w);
        setRadii();
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
    public void draw() {
        drawRelative(0, 0);
    }


    public interface ProgressListener {
        void onProgressChanged(int progress, float max);

        void onComplete();

        void onStart();
    }
}
