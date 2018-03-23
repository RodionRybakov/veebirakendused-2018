package ee.ut.cs.wad.AdBoard.application.config;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ErrorControllerImpl implements ErrorController {
	
	private static final String ERROR_PAGE = "error/error";
	
	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public String error() {
		return ERROR_PAGE;
	}
	
	@Override
	public String getErrorPath() {
		return "/error";
	}
}
