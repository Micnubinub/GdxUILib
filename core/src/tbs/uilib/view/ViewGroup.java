package tbs.uilib.view;

import java.util.ArrayList;

import tbs.uilib.UniversalClickListener;

/**
 * Created by linde on 07-Dec-15.
 */
public abstract class ViewGroup extends View {
    protected final ArrayList<View> views = new ArrayList<View>();

    @Override
    public boolean checkCollision(UniversalClickListener.TouchType touchType, int xPos, int yPos) {
        for (int i = (views.size() - 1); i >= 0; i--) {
            final View view = views.get(i);
            if (view.checkCollision(touchType, xPos, yPos)) {
                return true;
            }
        }
        return super.checkCollision(touchType, xPos, yPos);
    }

    public boolean cullView(final View v) {
        rect2.set(lastRelX + x, lastRelY + y, w, h);

        if (rect2.contains(v.getViewBounds())) {
            return true;
        } else {
            v.setLastRelX(lastRelX);
            v.setLastRelY(lastRelY);
        }
        return false;
    }

    public void addView(View view) {
        if (view == null || views.contains(view))
            return;
        views.add(view);
    }

}
