package tbs.uilib.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import tbs.uilib.Adapter;
import tbs.uilib.HUDManager;
import tbs.uilib.UniversalClickListener;

/**
 * Created by Michael on 3/10/2015.
 */
public class ListView extends ScrollView {
    //Todo get methods from Scrollview
    private OnItemClickListener listener;
    private Adapter adapter;


    public ListView(Adapter adapter, OnItemClickListener listener) {
        this.listener = listener;
        this.adapter = adapter;
    }

    @Override
    public boolean checkCollision(UniversalClickListener.TouchType touchType, int xPos, int yPos) {
        for (int i = 0; i < adapter.getCount(); i++) {
            final View v = adapter.getView(i);
            if (HUDManager.camera.isInFrustum(v.x, v.y, v.w, v.h) && v.checkCollision(touchType, xPos, yPos)) {
                if (listener != null)
                    listener.onItemClick(v, i);
                return true;
            }
        }
        return false;
    }

    @Override
    public void draw(SpriteBatch batch, ShapeRenderer renderer, float relX, float relY) {
        if (!HUDManager.camera.isInFrustum(x, y, w, h))
            return;
        animator.update();
        drawBackground(batch, renderer);
        if (adapter == null || adapter.getCount() < 1)
            return;

        for (int i = 0; i < adapter.getCount(); i++) {
            final View v = adapter.getView(i);
            if (checkCollision((int) v.x, (int) v.y, (int) v.w, (int) v.h) && HUDManager.camera.isInFrustum(v.x, v.y, v.w, v.h)) {
                v.draw(batch, renderer, relX, relY);
            }
        }
    }

    @Override
    public void setOnClickListener(UniversalClickListener.OnClickListener onClickListener) {
        super.setOnClickListener(onClickListener);
    }

    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void dispose() {
        for (View view : views) {
            view.dispose();
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int pos);
    }

}
