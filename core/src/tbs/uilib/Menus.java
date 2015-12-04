package tbs.uilib;


import tbs.uilib.view.Button;
import tbs.uilib.view.LinearLayout;

import java.util.ArrayList;


/**
 * Created by Michael on 4/18/2015.
 */
public class Menus {
    //TODO make all the menus for when the user clicks a map item ** common items being info>open sidebar, move, remove

    public static void getSiloMenu(LinearLayout container) {
        container.addView(new Button());
    }

    public static void getMenu(final Drawable drawable) {
        final ArrayList<MenuButton> menuButtons = Menu.getMenuItems();
        menuButtons.clear();

        final MenuButton move = new MenuButton("Move");
        final MenuButton remove = new MenuButton("Remove");
        final MenuButton info = new MenuButton("Info");
        menuButtons.add(move);
        menuButtons.add(remove);
        menuButtons.add(info);

        Menu.setMenuItemClickListener(new Menu.MenuItemClickListener() {
            @Override
            public void onMenuItemClick(int i) {
                switch (i) {
                    case 0:
                        Screen.log("0");
                        break;
                    case 1:
                        Screen.log("1");
                        break;

                }
            }
        });


        // drawable.name;
    }
}
