package eu.sanjin.kurelic.cbc.view.controller.web;

import eu.sanjin.kurelic.cbc.business.viewmodel.menu.Menu;
import eu.sanjin.kurelic.cbc.business.viewmodel.menu.MenuItem;
import eu.sanjin.kurelic.cbc.business.viewmodel.menu.MenuItems;
import eu.sanjin.kurelic.cbc.business.viewmodel.menu.MenuType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private Menu getAdminMenu() {
        Menu menu = new Menu(MenuType.LINK_BASED);
        MenuItems menuItems = new MenuItems();

        menuItems.add(new MenuItem("navigation.statisticsButton.text", "admin/stats"));
        menuItems.add(new MenuItem("navigation.usersButton.text", "admin/users"));
        menuItems.add(new MenuItem("navigation.routesButton.text", "admin/routes"));

        menu.setMenuItems(menuItems);
        return menu;
    }

    @GetMapping("")
    public ModelAndView adminPage() {
        return statisticsPage();
    }

    @GetMapping("/stats")
    public ModelAndView statisticsPage() {
        var viewModel = new ModelAndView("admin/statistics");
        // Menu
        viewModel.addObject("menuItem", getAdminMenu());

        return viewModel;
    }

    @GetMapping("/users")
    public ModelAndView usersPage() {
        var viewModel = new ModelAndView("admin/users");
        // Menu
        viewModel.addObject("menuItem", getAdminMenu());

        return viewModel;
    }

    @GetMapping("/user/{username}")
    public ModelAndView userPage(@PathVariable String username) {
        var viewModel = new ModelAndView("admin/user");
        // Menu
        viewModel.addObject("menuItem", getAdminMenu());

        return viewModel;
    }

    @GetMapping("/routes")
    public ModelAndView routesPage() {
        var viewModel = new ModelAndView("admin/routes");
        // Menu
        viewModel.addObject("menuItem", getAdminMenu());

        return viewModel;
    }

}
