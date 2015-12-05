package tbs.uilib.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import tbs.uilib.Adapter;
import tbs.uilib.HUDManager;
import tbs.uilib.Screen;
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
    public boolean checkCollision(Screen.TouchType touchType, int xPos, int yPos) {
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

    }

    @Override
    public void setOnClickListener(UniversalClickListener.OnClickListener onClickListener) {
        super.setOnClickListener(onClickListener);
    }

    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void drawRelative(SpriteBatch batch, int relX, int relY) {
        //Todo
        if ((!HUDManager.camera.isInFrustum(x, y, w, h)))
            return;
        super.drawRelative(batch, relX, relY);
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (!HUDManager.camera.isInFrustum(x, y, w, h))
            return;
        animator.update();
        drawBackground(batch);
        if (adapter == null || adapter.getCount() < 1)
            return;

        for (int i = 0; i < adapter.getCount(); i++) {
            final View v = adapter.getView(i);
            if (checkCollision(v.x, v.y, v.w, v.h) && HUDManager.camera.isInFrustum(v.x, v.y, v.w, v.h)) {
                v.draw(batch);
            }
        }


    }

    @Override
    public void handleFling(int x, int y, float velocityX, float velocityY) {
        super.handleFling(x, y, velocityX, velocityY);
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
