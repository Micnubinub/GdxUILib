package tbs.uilib.view;

import com.badlogic.gdx.graphics.Texture;

import tbs.uilib.Background;
import tbs.uilib.Screen;
import tbs.uilib.UniversalClickListener;
import tbs.uilib.Values;


/**
 * Created by Michael on 3/8/2015.
 */
public class Dialog extends View {
    public static boolean dimBackground = true;
    protected static View view;
    private static DialogListener listener;
    private static Texture dimmer;

    private boolean showDialog = false;
    //Todo somehow make it draw over other objects
    //Todo add dismiss on touch outside
    private int x, y;

    public Dialog(ViewGroup view, int w, int h) {
        Dialog.view = view;
        setWidth(w);
        setHeight(h);
        background = new Background(0x00000099, Background.Type.COLOR);
    }


    @Override
    public void dispose() {
        //Todo fill in
    }

    @Override
    public boolean drag(float startX, float startY, float dx, float dy) {
        return view.drag(startX, startY, dx, dy);
    }

    @Override
    public boolean fling(float vx, float vy) {
        return view.fling(vx, vy);
    }

    public void setDialogListener(DialogListener listener) {
        Dialog.listener = listener;
    }

    @Override
    public void draw(float relX, float relY, float relW, float relH) {

        lastRelX = relX;
        lastRelY = relY;
        if (!showDialog)
            return;
        //Todo make sure its drawn in the center, with padding(add to the values you are meant to create in utility >utility.dialog_padding = 1...
        if (dimBackground)
            drawBackground(relX, relY);

        if (view != null) {
            view.draw(relX + x, relY + y, Math.min(w, relW), Math.min(h, relH));
        }
    }

    @Override
    public void setHeight(float h) {
        h = h < Values.DIALOG_PADDING ? Values.DIALOG_PADDING : h;
        final int diff = Screen.h - Values.DIALOG_PADDING - Values.DIALOG_PADDING;
        this.h = h > diff ? diff : h;
        x = (int) ((Screen.w - view.w) / 2);
        y = (int) ((Screen.h - view.h) / 2);
    }

    @Override
    public boolean checkCollision(UniversalClickListener.TouchType touchType, int xPos, int yPos) {
        rect.set(x, y, w, h);
        if (!rect.contains(xPos, yPos)) {
            dismiss();
            return false;
        }
        return view.checkCollision(touchType, xPos, yPos);
    }


    @Override
    public void setWidth(float w) {
        w = w < Values.DIALOG_PADDING ? Values.DIALOG_PADDING : w;
        final int diff = Screen.w - Values.DIALOG_PADDING - Values.DIALOG_PADDING;
        this.w = w > diff ? diff : w;
        x = (int) ((Screen.w - view.w) / 2);
        y = (int) ((Screen.h - view.h) / 2);
    }

    public void dismiss() {
        showDialog = false;

        if (listener != null)
            listener.onDismiss();

//      Todo  DialogManager.removeDialog(this);
    }

    public void show() {
        showDialog = true;
//      Todo  DialogManager.addDialog(this);
        if (listener != null)
            listener.onShow();
    }

    public interface DialogListener {
        void onDismiss();

        void onShow();
    }
}
