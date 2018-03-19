package ee.ut.cs.wad.AdBoard.signup;

import ee.ut.cs.wad.AdBoard.Variables;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/signUp")
public class SignupController {
    private static final String SIGNUP_PAGE = "signUp/signUp";

    @RequestMapping(method = RequestMethod.GET)
    public String hello(Model model) {
        model.addAttribute("greeting", "It is SignUp Page");
        Variables v = new Variables();
        v.turn_nav_barOn(model);
        return SIGNUP_PAGE;
    }
}

