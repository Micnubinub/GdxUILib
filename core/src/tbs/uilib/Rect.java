package tbs.uilib;

/**
 * Created by Michael on 3/10/2015.
 */
public class Rect {
    private static boolean b1, b2;
    private static Rect rect1;
    public float x, y, w, h;

    public void set(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void set(Rect r) {
        x = r.x;
        y = r.y;
        w = r.w;
        h = r.h;
    }

    public boolean contains(float x, float y) {
        return (x > this.x && y > this.y) && (x < this.x + w && y < this.y + h);
//        return (this.x <= x) && (this.y <= y)&&(this.x + w > this.x + x) && (this.y + h > this.y + y) ;
    }

    public boolean contains(float x, float y, float w, float h) {
        rect1.set(x, y, w, h);
        return contains(rect1);
    }

    public boolean contains(Rect r) {
        b1 = x < r.x + r.w && x + w > r.x && y < r.y + r.h && y + h > r.y;
        b2 = contains(r.x, r.y) && contains(r.x + r.w, r.y + r.h)
                || contains(r.x, r.y + h) && contains(r.x + r.w, r.y);

//        return contains(r.x, r.y, r.w, r.h);
        return b1 || b2;
    }

    @Override
    public String toString() {
        return String.format("x : %.0f, y : %.0f | w : %.0f, h : %.0f", x, y, w, h);
    }
}
