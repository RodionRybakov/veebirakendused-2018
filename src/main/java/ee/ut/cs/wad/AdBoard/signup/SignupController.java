package ee.ut.cs.wad.AdBoard.signup;

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
        return SIGNUP_PAGE;
    }
}
