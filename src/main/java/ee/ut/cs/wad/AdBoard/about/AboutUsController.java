package ee.ut.cs.wad.AdBoard.about;

import ee.ut.cs.wad.AdBoard.Variables;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(path = "/about", method = RequestMethod.GET)
public class AboutUsController {
    private static final String ABOUTUS_PAGE = "aboutUs/about";

    @RequestMapping(method = RequestMethod.GET)
    //@RequestMapping(value = "/findWork")
    public String offer(Model model) {
        model.addAttribute("greeting", "About Us");
        Variables v = new Variables();
        v.turn_nav_barOn(model);

        return ABOUTUS_PAGE;
    }
}
