package ee.ut.cs.wad.AdBoard.user;

import ee.sk.smartid.AuthenticationHash;
import ee.sk.smartid.SmartIdAuthenticationResponse;
import ee.sk.smartid.SmartIdClient;
import ee.sk.smartid.rest.dao.NationalIdentity;
import ee.ut.cs.wad.AdBoard.application.config.Messages;
import ee.ut.cs.wad.AdBoard.offer.OfferRepository;
import ee.ut.cs.wad.AdBoard.user.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
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
	private static final String SMARTID_PAGE = "user/smartid";
	private static final String SIGNUP_PAGE = "user/signup";
	private static final String ACCOUNT_PAGE = "user/account";
	private static final String ADMIN_PAGE = "user/admin";
	
	private final UserService userService;
	private final UserRepository userRepository;
	private final OfferRepository offerRepository;
	private final PasswordEncoder passwordEncoder;
	private final Messages messages;

	@Autowired
	public UserController(UserService userService,
						  UserRepository userRepository,
						  OfferRepository offerRepository,
						  PasswordEncoder passwordEncoder,
						  Messages messages) {
		this.userService = userService;
		this.userRepository = userRepository;
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
	
	@RequestMapping(value = "/login/smartid", method = RequestMethod.GET)
	public String login() {
		return SMARTID_PAGE;
	}
	
	@RequestMapping(value = "/login/smartid", method = RequestMethod.POST)
	public String login(Model model, String identityNumber) {
		SmartIdClient client = new SmartIdClient();
		client.setRelyingPartyUUID("00000000-0000-0000-0000-000000000000");
		client.setRelyingPartyName("DEMO");
		client.setHostUrl("https://sid.demo.sk.ee/smart-id-rp/v1/");
		
		NationalIdentity nationalIdentity = new NationalIdentity("EE", identityNumber);
		AuthenticationHash authenticationHash = AuthenticationHash.generateRandomHash();
		String verificationCode = authenticationHash.calculateVerificationCode();
		
		User user = userRepository.findUserByIdentityNumber(identityNumber);
		
		if (user != null) {
//			SmartIdAuthenticationResponse response = client
//					.createAuthentication()
//					.withNationalIdentity(nationalIdentity)
//					.withAuthenticationHash(authenticationHash)
//					.withCertificateLevel("QUALIFIED")
//					.authenticate();
			
			String username = user.getUsername();
			String password = user.getPassword();
			
			Authentication authentication = new UsernamePasswordAuthenticationToken(username, password, AuthorityUtils.createAuthorityList("USER"));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			return "redirect:/account";
		}
		return SMARTID_PAGE;
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
			return ADMIN_PAGE;
		}
		
		User owner = userRepository.findUserByUsername(auth.getName());
		model.addAttribute("offers", offerRepository.findOffersByOwner(owner));
		return ACCOUNT_PAGE;
	}
}
