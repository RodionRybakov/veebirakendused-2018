package ee.ut.cs.wad.AdBoard.signin;

import ee.ut.cs.wad.AdBoard.Variables;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping(path = "/signIn", method = RequestMethod.GET)
public class SignInController {

    private static final String SIGNIN_PAGE = "signin/signIn";
    @RequestMapping(method = RequestMethod.GET)
    public String hello(Model model) {
        model.addAttribute("greeting", "Sign in");

        Variables v = new Variables();
        v.turn_nav_barOn(model);
        return SIGNIN_PAGE;
    }

}
