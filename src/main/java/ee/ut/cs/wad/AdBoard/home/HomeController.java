package ee.ut.cs.wad.AdBoard.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping({"/", "/home"})
public class HomeController {
	
	private static final String HOME_PAGE = "home/home";

	@RequestMapping(method = RequestMethod.GET)
	public String hello(Model model) {
		model.addAttribute("greeting", "test");
		return HOME_PAGE;
	}
}
