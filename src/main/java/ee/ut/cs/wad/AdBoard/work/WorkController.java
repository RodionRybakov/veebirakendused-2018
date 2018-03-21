package ee.ut.cs.wad.AdBoard.work;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WorkController {
	
	private static final String OFFERS_PAGE = "work/work";
	
	@RequestMapping(value = "/offers", method = RequestMethod.GET)
	public String offers() {
		return OFFERS_PAGE;
	}
}
