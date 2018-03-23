package ee.ut.cs.wad.AdBoard.about;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AboutController {

    private static final String ABOUT_PAGE = "about/about";

    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String about() {
        return ABOUT_PAGE;
    }
}
