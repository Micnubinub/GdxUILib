package tbs.uilib;

import com.badlogic.gdx.graphics.g2d.TextureRegion;


/**
 * Created by Michael on 4/11/2015.
 */
public class DrawableParser {
    public static int referenceWidth = 1920;

    public static Drawable getDrawable(String name, int x, int y, int wIn) {
        final float scale = referenceWidth / 1920f;
        final int w = Math.round(scale * wIn);
        x = Math.round(x * scale);
        y = Math.round(y * scale);
        final TextureRegion region = Map.textureAtlas.findRegion(name);
        final int h = Math.round(scale * (region.getRegionHeight() / (float) region.getRegionWidth()) * wIn);
        return new Drawable(region, name, x, y, w, h);
    }

}
