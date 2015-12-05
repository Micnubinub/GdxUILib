package tbs.uilib.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import tbs.uilib.UILib;


public class Launcher {
    public static void main(String[] arg) {
        final LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.height = 960;
        config.width = 540;

        config.resizable = false;
        new LwjglApplication(new UILib(), config);
    }
}
