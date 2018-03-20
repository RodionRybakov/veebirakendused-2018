package ee.ut.cs.wad.AdBoard.signup;

import ee.ut.cs.wad.AdBoard.signup.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/signup")
public class SignUpController {
	
	private static final String SIGNUP_PAGE = "signup/signup";
	private final SignUpService signUpService;

	@Autowired
	SignUpController(SignUpService signUpService) {
		this.signUpService = signUpService;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("greeting", "test");
		return SIGNUP_PAGE;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String addUser(@ModelAttribute UserDTO userDTO, Model model) {
		User user = new User();
		user.setUsername(userDTO.getUsername());
		user.setPassword(userDTO.getPassword());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setEmail(userDTO.getEmail());
		
		try {
			signUpService.addUser(user);
			model.addAttribute("successMessage", "You were successfully registered");
		} catch (UnsupportedOperationException e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		return SIGNUP_PAGE;
	}
}
