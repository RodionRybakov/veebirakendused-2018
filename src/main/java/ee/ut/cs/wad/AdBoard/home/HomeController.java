package ee.ut.cs.wad.AdBoard.home;

import ee.ut.cs.wad.AdBoard.stats.StatsRepository;
import ee.ut.cs.wad.AdBoard.stats.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping({"/", "/home"})
public class HomeController {
	
	private static final String HOME_PAGE = "home/home";
	
	private final StatsService statsService;
	
	@Autowired
	public HomeController(StatsService statsService, StatsRepository statsRepository) {
		this.statsService = statsService;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String home(HttpServletRequest request) {
		statsService.saveUserAgent(request);
		return HOME_PAGE;
	}
}
