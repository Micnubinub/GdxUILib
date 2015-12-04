package tbs.uilib;


/**
 * Created by Michael on 2/1/2015.
 */
public interface InteractiveObject {
    Rect rect = new Rect();
    Rect rect2 = new Rect();

    boolean checkCollision(UniversalClickListener.TouchType touchType, int xPos, int yPos);

    boolean checkCollision(int xPos, int yPos, int width, int height);

    void drag(int startX, int startY, int x, int y);

    void fling(float vx, float vy);
}
