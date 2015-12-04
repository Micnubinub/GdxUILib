package tbs.uilib;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;


/**
 * Created by linde on 17-Oct-15.
 */
public class StoreItem extends com.badlogic.gdx.scenes.scene2d.ui.Widget {
    private static Texture tecture;
    private static Color color = new Color();
    private static float cr, hcr;
    private String priceS;
    private int price, c1, c2, id, r;
    private boolean logged;


    public StoreItem(int id, int color1, int color2, int price, int radius) {
        this.price = price;
        this.priceS = String.valueOf(price);
        this.id = id;
        c1 = color1;
        c2 = color2;
        r = radius;
        cr = 0.9f * r;
        hcr = cr / 2;
        tecture = new Texture("data/pre_particle.png");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        final float w = getWidth();
        final float h = getHeight();
        final float center = getX() + w - ((w - r - r) / 2);

        batch.draw(tecture, getX(), getY(), r + r, r + r);
        Utility.drawCenteredText(batch, Color.WHITE, priceS, center, getY() + (h / 2), Utility.getScale(h / 2.75f));
    }

}
