package ee.ut.cs.wad.AdBoard.offer;

import ee.ut.cs.wad.AdBoard.Variables;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/offerWork")
public class OfferWorkController {

    private static final String FINDWORK_PAGE = "offerWork/offerWork";

    @RequestMapping(method = RequestMethod.GET)
    //@RequestMapping(value = "/findWork")
    public String offer(Model model) {
        model.addAttribute("greeting", "It is OfferWork Page");
        Variables v = new Variables();
        v.turn_nav_barOn(model);

        return FINDWORK_PAGE;
    }
}
