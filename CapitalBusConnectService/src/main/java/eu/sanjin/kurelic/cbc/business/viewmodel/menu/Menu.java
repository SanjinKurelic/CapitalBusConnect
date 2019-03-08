/*
 * Created by Sanjin Kurelic (kurelic@sanjin.eu)
 */
package eu.sanjin.kurelic.cbc.business.viewmodel.menu;

import java.util.ArrayList;
import java.util.List;

public class Menu {

    private MenuType menuType;
    private String menuTitle;
    private MenuItems menuItems;

    public Menu(MenuType menuType) {
        this(menuType, "", new MenuItems());
    }

    public Menu(MenuType menuType, MenuItems menuItems) {
        this(menuType, "", menuItems);
    }

    public Menu(MenuType menuType, String menuTitle, MenuItems menuItems) {
        this.menuItems = menuItems;
        this.menuTitle = menuTitle;
        this.menuType = menuType;
    }

    public MenuType getMenuType() {
        return menuType;
    }

    public void setMenuType(MenuType menuType) {
        this.menuType = menuType;
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }

    public MenuItems getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(MenuItems menuItems) {
        this.menuItems = menuItems;
    }
}
