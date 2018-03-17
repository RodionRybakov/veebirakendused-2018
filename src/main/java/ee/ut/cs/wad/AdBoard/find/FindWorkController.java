package ee.ut.cs.wad.AdBoard.find;

import ee.ut.cs.wad.AdBoard.Variables;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/findWork")
public class FindWorkController {

    private static final String FINDWORK_PAGE = "findWork/findWork";

    @RequestMapping(method = RequestMethod.GET)
    //@RequestMapping(value = "/findWork")
    public String find(Model model) {
        model.addAttribute("greeting", "It is findWork Page");
        Variables v = new Variables();
        v.turn_nav_barOn(model);

        return FINDWORK_PAGE;
    }
}
