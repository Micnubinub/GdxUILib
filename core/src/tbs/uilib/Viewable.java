package tbs.uilib;

/**
 * Created by Michael on 2/26/2015.
 */
public interface Viewable {
    void setState(State state);

    void setTouchDown(boolean touchDown);

    Object getTag();

    int getID();

    void draw(final float relX, final float relY);

    void dispose();
}
