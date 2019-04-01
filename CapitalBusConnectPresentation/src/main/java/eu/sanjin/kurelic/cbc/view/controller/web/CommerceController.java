package eu.sanjin.kurelic.cbc.view.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CommerceController {

    @GetMapping("/buy")
    public ModelAndView bought() {
        return null;
    }

}
