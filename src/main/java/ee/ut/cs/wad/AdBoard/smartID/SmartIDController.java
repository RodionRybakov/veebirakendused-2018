package ee.ut.cs.wad.AdBoard.smartID;

import ee.sk.smartid.*;
import ee.sk.smartid.rest.dao.NationalIdentity;
import ee.ut.cs.wad.AdBoard.user.User;
import ee.ut.cs.wad.AdBoard.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SmartIDController {
	
	private static final String SMARTID_PAGE = "user/smartid";
	
	private final UserRepository userRepository;
	private final Environment env;
	
	private SmartIdAuthenticationResponse authenticationResponse;
	
	@Autowired
	public SmartIDController(UserRepository userRepository, Environment env) {
		this.userRepository = userRepository;
		this.env = env;
	}
	
	@RequestMapping(value = "/smartid", method = RequestMethod.GET)
	public String login(Model model, String error) {
		if (error != null) model.addAttribute("error", error);
		return SMARTID_PAGE;
	}
	
	@ResponseBody
	@RequestMapping(value = "/smartid/init", method = RequestMethod.POST)
	public String init(String countryCode, String identityNumber) {
		SmartIdClient client = new SmartIdClient();
		client.setHostUrl(env.getProperty("security.smartid.base-URL"));
		client.setRelyingPartyUUID(env.getProperty("security.smartid.relying-party-UUID"));
		client.setRelyingPartyName(env.getProperty("security.smartid.relying-party-name"));
		NationalIdentity nationalIdentity = new NationalIdentity(countryCode, identityNumber);
		
		AuthenticationHash authenticationHash = AuthenticationHash.generateRandomHash();
//		authenticationResponse = client
//				.createAuthentication()
//				.withNationalIdentity(nationalIdentity)
//				.withAuthenticationHash(authenticationHash)
//				.withCertificateLevel("QUALIFIED")
//				.authenticate();
		
		return authenticationHash.calculateVerificationCode();
	}
	
	@ResponseBody
	@RequestMapping(value = "/smartid/status", method = RequestMethod.POST)
	public String status() {
		AuthenticationResponseValidator authenticationResponseValidator = new AuthenticationResponseValidator();
		SmartIdAuthenticationResult authenticationResult = authenticationResponseValidator.validate(authenticationResponse);

		if (authenticationResult.isValid()) {
			AuthenticationIdentity authIdentity = authenticationResult.getAuthenticationIdentity();
			String firstName = authIdentity.getGivenName();
			String lastName = authIdentity.getSurName();
			String identityNumber = authIdentity.getIdentityCode();
			User user = userRepository.findUserByIdentityNumber(identityNumber);
			
			if (user != null) {
				String username = user.getUsername();
				String password = user.getPassword();
				
				Authentication authentication = new UsernamePasswordAuthenticationToken(username, password, AuthorityUtils.createAuthorityList("USER"));
				SecurityContextHolder.getContext().setAuthentication(authentication);
				return "success";
			}
		}
		return null;
	}
}
