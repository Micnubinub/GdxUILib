package tbs.uilib.view;

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
        super(true);
        this.listener = listener;
        this.adapter = adapter;
    }

    @Override
    public boolean drag(float startX, float startY, float dx, float dy) {
        rect.set(lastRelX + x, lastRelY + y, w, h);
        if (rect.contains(startX, startY)) {
            //Todo pan animator
            scrollX += dx;
            scrollY += dy;
            return true;
        }
        return false;
    }

    @Override
    public boolean checkCollision(UniversalClickListener.TouchType touchType, int xPos, int yPos) {
        for (int i = 0; i < adapter.getCount(); i++) {
            final View v = adapter.getView(i);
            rect.set(x, y, w, h);
            if (rect.contains(v.x, v.y, v.w, v.h))
                if (HUDManager.camera.isInFrustum(v.x, v.y, v.w, v.h) && v.checkCollision(touchType, xPos, yPos)) {
                    if (listener != null)
                        listener.onItemClick(v, i);
                    return true;
                }
        }
        return false;
    }

    @Override
    public void draw(float relX, float relY, float parentRight, float parentTop) {
        lastRelX = relX;
        lastRelY = relY;
        if (!HUDManager.camera.isInFrustum(x, y, w, h))
            return;
        panAnimator.update();
        drawBackground(relX, relY);
        if (adapter == null || adapter.getCount() < 1)
            return;

        for (int i = 0; i < views.size(); i++) {
            final View v = views.get(i);
            if (resizeChildrenWhenParentResized)
                v.w = v.w > w ? w : v.w;

            cumulative += v.h;
//            v.x += scrollX;
//            v.y -= scrollY;
            v.setLastRelX(relX + x + scrollX);
            v.setLastRelY(viewTop - cumulative - scrollY);

            if (cullView(v))
                v.draw(relX + x + scrollX, viewTop - cumulative - scrollY, Math.min(relX + x + scrollX + w, parentRight), Math.min(viewTop - cumulative - scrollY + h, parentTop));
        }
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
