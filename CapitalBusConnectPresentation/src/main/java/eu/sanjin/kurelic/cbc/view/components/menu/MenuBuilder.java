package eu.sanjin.kurelic.cbc.view.components.menu;

import eu.sanjin.kurelic.cbc.view.components.ActiveTabItem;
import eu.sanjin.kurelic.cbc.view.configuration.SpringSecurityConfiguration;
import org.apache.commons.lang3.StringUtils;

public class MenuBuilder {

    // Admin
    private static final String ADMIN_STATISTICS_URL = "admin/stats";
    private static final String ADMIN_USERS_URL = "admin/users";
    private static final String ADMIN_ROUTES_URL = "admin/routes";
    // User
    private static final String USER_SETTINGS_URL = "user/settings";
    private static final String USER_TRAVELS_URL = "user/travels";
    private static final String USER_DISCOUNTS_URL = "user/discounts";
    // Login
    private static final String LOGIN_TAB_ID = "loginTab";
    private static final String REGISTER_TAB_ID = "registerTab";
    private static final String ACTIVE_TAB = "active";

    public static Menu getAdminMenu() {
        MenuItems menuItems = new MenuItems();
        menuItems.add(new MenuItem(MenuTitleKey.ADMIN_STATISTICS_ITEM, ADMIN_STATISTICS_URL));
        menuItems.add(new MenuItem(MenuTitleKey.ADMIN_USERS_ITEM, ADMIN_USERS_URL));
        menuItems.add(new MenuItem(MenuTitleKey.ADMIN_ROUTES_ITEM, ADMIN_ROUTES_URL));
        menuItems.add(new MenuItem(MenuTitleKey.LOGOUT_ITEM, SpringSecurityConfiguration.LOGOUT_PAGE_URL));
        return getMenu(MenuType.LINK_BASED, menuItems);
    }

    public static Menu getUserMenu() {
        MenuItems menuItems = new MenuItems();
        menuItems.add(new MenuItem(MenuTitleKey.USER_SETTINGS_ITEM, USER_SETTINGS_URL));
        menuItems.add(new MenuItem(MenuTitleKey.USER_TRAVELS_ITEM, USER_TRAVELS_URL));
        menuItems.add(new MenuItem(MenuTitleKey.USER_DISCOUNTS_ITEM, USER_DISCOUNTS_URL));
        menuItems.add(new MenuItem(MenuTitleKey.LOGOUT_ITEM, SpringSecurityConfiguration.LOGOUT_PAGE_URL));
        return getMenu(MenuType.LINK_BASED, menuItems);
    }

    public static Menu getLoginPageMenu(ActiveTabItem tabItem) {
        MenuItems menuItems = new MenuItems();
        menuItems.add(new MenuItem(
                MenuTitleKey.LOGIN_ITEM, LOGIN_TAB_ID,
                tabItem.equals(ActiveTabItem.LOGIN_PAGE) ? ACTIVE_TAB : StringUtils.EMPTY
        ));
        menuItems.add(new MenuItem(
                MenuTitleKey.REGISTER_ITEM, REGISTER_TAB_ID,
                tabItem.equals(ActiveTabItem.REGISTER_PAGE) ? ACTIVE_TAB : StringUtils.EMPTY
        ));
        return getMenu(MenuType.TABULAR_BASED, menuItems);
    }

    public static Menu getEmptySimpleMenu(String title) {
        return new Menu(MenuType.SIMPLE, title, new MenuItems());
    }

    public static Menu getBuyMenu(String title, String buyUrl) {
        MenuItems menuItems = new MenuItems();
        menuItems.add(new MenuItem(MenuTitleKey.BUY_BUTTON, buyUrl));
        return new Menu(MenuType.SIMPLE, title, menuItems);
    }

    private static Menu getMenu(MenuType menuType, MenuItems menuItems) {
        Menu menu = new Menu(menuType);
        menu.setMenuItems(menuItems);
        return menu;
    }

}
