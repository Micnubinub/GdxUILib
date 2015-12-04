package tbs.uilib;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import tbs.uilib.view.base_classes.Camera;

/**
 * Created by Michael on 1/28/2015.
 */
public class Drawable extends GameObject implements InteractiveObject, DrawableItem {
    public boolean hasMultipleSides = false;
    public int x, y, w, h;
    public String name;
    public TextureRegion sprite;

    //Todo add some kind of tag to identify views so you can identify views when clicked

    public Drawable(TextureRegion sprite, String name, int x, int y, int w, int h) {
        this.name = name;
        this.sprite = sprite;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }


    @Override
    public void drag(int startX, int startY, int x, int y) {
    }

    @Override
    public boolean checkCollision(UniversalClickListener.TouchType touchType, int xPos, int yPos) {
        //Todo use ttype
        if (Camera.currentViewRect == null)
            rect.set(x, y, w, h);
        else
            rect.set(x - Camera.currentViewRect.getX(), y - Camera.currentViewRect.getY(), w, h);

        if (rect.contains(xPos, yPos)) {
            UniversalClickListener.confirmClick();
            return true;
        }
        return false;
    }

    @Override
    public boolean checkInGameCollision(int xPos, int yPos) {
        //TODO check this
//        xPos += camera.position.x - Camera.getOgArea().x;
//        yPos += camera.position.y - Camera.getOgArea().y;

        float rXratio = (xPos - x) / (float) w;
        float rYratio = (yPos - y) / (float) h;

        if (rXratio > 1 || rYratio > 1 || rXratio < 0 || rYratio < 0)
            return false;

        int rX = 0, rY = 0;
        try {
            rX = Math.round(rXratio * sprite.getRegionWidth());
            rY = Math.round(rYratio * sprite.getRegionHeight());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

//        if (!Utility.isPixelOrClickTransparent(sprite, rX, rY)) {
//            setContinueCheckingClicks(false);
//            return true;
//        }
        return false;
    }



    @Override
    public boolean checkCollision(int xPos, int yPos, int width, int height) {
        rect.set(x, y, w, h);
        rect2.set(xPos, yPos, width, height);

        if (rect.contains(rect2)) {
            UniversalClickListener.confirmClick();
            return true;
        }
        return false;
    }

    @Override
    public void drawRelative(float x, float y) {

    }

    @Override
    public void fling(float vx, float vy) {

    }

    public void setW(int w) {
        this.w = w;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setH(int h) {
        this.h = h;
    }

    public void setHasMultipleSides(boolean hasMultipleSides) {
        this.hasMultipleSides = hasMultipleSides;
    }


    @Override
    public String toString() {
        return String.format("%d : %d, %d >> %d, %d", sprite, x, y, w, h);
    }


}
