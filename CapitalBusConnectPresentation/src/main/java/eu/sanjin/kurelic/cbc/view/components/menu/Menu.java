package eu.sanjin.kurelic.cbc.view.components.menu;

import org.apache.commons.lang3.StringUtils;

public class Menu {

    private MenuType menuType;
    private String menuTitle;
    private MenuItems menuItems;

    Menu(MenuType menuType) {
        this(menuType, StringUtils.EMPTY, new MenuItems());
    }

    Menu(MenuType menuType, String menuTitle, MenuItems menuItems) {
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
