package ee.ut.cs.wad.AdBoard.signin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping(path = "/signin")
public class SignInController {

    private static final String SIGNIN_PAGE = "signin/signin";
    @RequestMapping(method = RequestMethod.GET)
    public String signup(Model model) {
        model.addAttribute("greeting", "Sign in");
        return SIGNIN_PAGE;
    }

}
