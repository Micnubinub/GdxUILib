package tbs.uilib.view;

import java.util.ArrayList;

import tbs.uilib.Rect;
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

    public boolean cullView(final Rect bounds) {
        bounds.set(bounds.x + lastRelX, bounds.y + lastRelX, bounds.w, bounds.h);
        rect2.set(lastRelX + x, lastRelY + y, w, h);
        return rect2.contains(bounds);

    }

    public void addView(View view) {
        if (view == null || views.contains(view))
            return;
        print("adding view> " + views.size());
        views.add(view);
    }

}
