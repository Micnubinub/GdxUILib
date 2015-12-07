package tbs.uilib;


/**
 * Created by Michael on 2/1/2015.
 */
public interface InteractiveObject {
    Rect rect = new Rect();
    Rect rect2 = new Rect();

    boolean checkCollision(UniversalClickListener.TouchType touchType, int xPos, int yPos);

    boolean checkCollision(int xPos, int yPos, int width, int height);

    boolean drag(float startX, float startY, float dx, float dy);

    boolean fling(float vx, float vy);
}
