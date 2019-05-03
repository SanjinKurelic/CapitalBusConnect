package eu.sanjin.kurelic.cbc.view.components.menu;

import org.apache.commons.lang3.StringUtils;

public class Menu {

    private final MenuType menuType;
    private final String menuTitle;
    private MenuItems menuItems;

    Menu(MenuType menuType) {
        this(menuType, StringUtils.EMPTY, new MenuItems());
    }

    Menu(MenuType menuType, String menuTitle, MenuItems menuItems) {
        setMenuItems(menuItems);
        this.menuTitle = menuTitle;
        this.menuType = menuType;
    }

    public MenuType getMenuType() {
        return menuType;
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public MenuItems getMenuItems() {
        return menuItems;
    }

    void setMenuItems(MenuItems menuItems) {
        this.menuItems = menuItems;
    }
}
