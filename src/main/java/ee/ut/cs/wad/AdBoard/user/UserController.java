package ee.ut.cs.wad.AdBoard.user;

import ee.ut.cs.wad.AdBoard.user.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {
	
	private static final String LOGIN_PAGE = "user/login";
	private static final String SIGNUP_PAGE = "user/signup";
	private static final String ACCOUNT_PAGE = "user/account";
	
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserController(UserService userService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, String error, String logout) {
		if (error != null) model.addAttribute("error", "Invalid username and password.");
		if (logout != null) model.addAttribute("logout", "You have been logged out successfully.");
		return LOGIN_PAGE;
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup() {
		return SIGNUP_PAGE;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String addUser(@ModelAttribute UserDTO userDTO, Model model, HttpServletRequest request) {
		User user = new User();
		user.setUsername(userDTO.getUsername());
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setEmail(userDTO.getEmail());
		
		try {
			userService.addUser(user);
			model.addAttribute("success", "You were successfully registered");
			userService.authenticateUserAndSetSession(userDTO.getUsername(), userDTO.getPassword(), request);
		} catch (UnsupportedOperationException e) {
			model.addAttribute("error", e.getMessage());
		}
		return SIGNUP_PAGE;
	}
	
	@RequestMapping(value = "/account", method = RequestMethod.GET)
	public String account() {
		return ACCOUNT_PAGE;
	}
}
