package ee.ut.cs.wad.AdBoard.user;

import ee.ut.cs.wad.AdBoard.application.config.Messages;
import ee.ut.cs.wad.AdBoard.offer.OfferRepository;
import ee.ut.cs.wad.AdBoard.stats.StatsRepository;
import ee.ut.cs.wad.AdBoard.user.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
	private static final String ADMIN_PAGE = "user/admin";
	
	private final UserService userService;
	private final UserRepository userRepository;
	private final StatsRepository statsRepository;
	private final OfferRepository offerRepository;
	private final PasswordEncoder passwordEncoder;
	private final Messages messages;

	@Autowired
	public UserController(UserService userService,
						  UserRepository userRepository,
						  StatsRepository statsRepository, OfferRepository offerRepository,
						  PasswordEncoder passwordEncoder,
						  Messages messages) {
		this.userService = userService;
		this.userRepository = userRepository;
		this.statsRepository = statsRepository;
		this.offerRepository = offerRepository;
		this.passwordEncoder = passwordEncoder;
		this.messages = messages;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, String error, String logout) {
		if (error != null) model.addAttribute("error", messages.get("login.error"));
		if (logout != null) model.addAttribute("logout", messages.get("login.success.logout"));
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
			model.addAttribute("success", messages.get("signup.success"));
			userService.authenticateUserAndSetSession(userDTO.getUsername(), userDTO.getPassword(), request);
		} catch (UnsupportedOperationException e) {
			model.addAttribute("error", e.getMessage());
		}
		return SIGNUP_PAGE;
	}
	
	@RequestMapping(value = "/account", method = RequestMethod.GET)
	public String account(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (auth.getName().equals("admin")) {
			model.addAttribute("total", userRepository.getAll());
			model.addAttribute("stats", statsRepository.findAll());
			model.addAttribute("browser", statsRepository.popularBrowser());
			return ADMIN_PAGE;
		}
		
		User owner = userRepository.findUserByUsername(auth.getName());
		model.addAttribute("offers", offerRepository.findOffersByOwner(owner));
		return ACCOUNT_PAGE;
	}
}
