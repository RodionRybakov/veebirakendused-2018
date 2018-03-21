package ee.ut.cs.wad.AdBoard.user;

import ee.ut.cs.wad.AdBoard.user.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
	
	private static final String HOME_PAGE = "home/home";
	private static final String SIGNUP_PAGE = "signup/signup";
	private final UserService userService;
	private final SecurityService securityService;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserController(UserService userService, SecurityService securityService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.securityService = securityService;
		this.passwordEncoder = passwordEncoder;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, String error, String logout) {
		if (error != null) model.addAttribute("error", "Your username and password is invalid.");
		if (logout != null) model.addAttribute("message", "You have been logged out successfully.");
		return "signin/signin";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup() {
		return SIGNUP_PAGE;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String addUser(@ModelAttribute UserDTO userDTO, Model model) {
		User user = new User();
		user.setUsername(userDTO.getUsername());
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setEmail(userDTO.getEmail());
		
		try {
			userService.addUser(user);
			model.addAttribute("successMessage", "You were successfully registered");
			securityService.autologin(userDTO.getUsername(), userDTO.getPassword());
		} catch (UnsupportedOperationException e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		return SIGNUP_PAGE;
	}
}
