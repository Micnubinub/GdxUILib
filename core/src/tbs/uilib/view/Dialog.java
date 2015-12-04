package tbs.uilib.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import tbs.ui.test.DialogManager;
import tbs.ui.test.Screen;
import tbs.ui.test.Values;


/**
 * Created by Michael on 3/8/2015.
 */
public class Dialog extends View {
    public static boolean dimBackground = true;
    protected static View view;
    private static DialogListener listener;
    private static Texture dimmer;

    static {
        setDimPercentage(20);
    }

    private boolean showDialog = false;
    //Todo somehow make it draw over other objects
    //Todo add dismiss on touch outside
    private int x, y;

    public Dialog(View view, int w, int h) {
        Dialog.view = view;
        setWidth(w);
        setHeight(h);
    }

    public static void setDimPercentage(int percent) {
        setDimBackground(percent > 4);

        percent = percent < 0 ? 0 : percent;
        percent = percent > 100 ? 100 : percent;

        final Color dim = new Color(0, 0, 0, percent / 100f);
        final Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(dim);
        pixmap.fill();
        dimmer = new Texture(pixmap);
    }

    public static void setDimBackground(boolean dimBackground) {
        Dialog.dimBackground = dimBackground;
    }

    @Override
    public void dispose() {
        //Todo fill in
    }

    @Override
    public void handleFling(float x, float y, float velocityX, float velocityY) {
        view.handleFling(x, y, velocityX, velocityY);
    }

    public void setDialogListener(DialogListener listener) {
        Dialog.listener = listener;
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (!showDialog)
            return;
        //Todo make sure its drawn in the center, with padding(add to the values you are meant to create in utility >utility.dialog_padding = 1...
        if (dimBackground)
            batch.draw(dimmer, 0, 0, Screen.w, Screen.h);

        if (view != null) {
            view.drawRelative(x, y);
        }
    }

    @Override
    public void drawBackground(SpriteBatch batch) {
        //TODO
    }

    @Override
    public void setHeight(int h) {
        h = h < Values.DIALOG_PADDING ? Values.DIALOG_PADDING : h;
        final int diff = Screen.h - Values.DIALOG_PADDING - Values.DIALOG_PADDING;
        this.h = h > diff ? diff : h;
        x = (Screen.w - view.w) / 2;
        y = (Screen.h - view.h) / 2;
    }

    @Override
    public boolean checkCollision(Screen.TouchType touchType, int xPos, int yPos) {
        rect.set(x, y, w, h);
        if (!rect.contains(xPos, yPos)) {
            dismiss();
            return false;
        }
        return view.checkCollision(touchType, xPos, yPos);
    }

    @Override
    public void drawRelative(SpriteBatch batch, int relX, int relY) {

    }

    @Override
    public void setWidth(int w) {
        w = w < Values.DIALOG_PADDING ? Values.DIALOG_PADDING : w;
        final int diff = Screen.w - Values.DIALOG_PADDING - Values.DIALOG_PADDING;
        this.w = w > diff ? diff : w;
        x = (Screen.w - view.w) / 2;
        y = (Screen.h - view.h) / 2;
    }

    public void dismiss() {
        showDialog = false;

        if (listener != null)
            listener.onDismiss();

        DialogManager.removeDialog(this);


    }

    public void show() {
        showDialog = true;
        DialogManager.addDialog(this);
        if (listener != null)
            listener.onShow();
    }

    public interface DialogListener {
        void onDismiss();

        void onShow();
    }
}
