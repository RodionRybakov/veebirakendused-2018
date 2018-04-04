package ee.ut.cs.wad.AdBoard.user;

import com.sendgrid.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final AuthenticationManager authenticationManager;
	private final UserDetailsService userDetailsService;
	
	@Autowired
	public UserService(UserRepository userRepository,
					   RoleRepository roleRepository,
					   AuthenticationManager authenticationManager,
					   @Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
	}
	
	public void addUser(User user) {
		User existing = userRepository.findUserByUsername(user.getUsername());
		if (existing != null) throw new UnsupportedOperationException("This username is taken");
		
		existing = userRepository.findUserByEmail(user.getEmail());
		if (existing != null) throw new UnsupportedOperationException("User with this email already exists");
		
		Role role = roleRepository.findRoleByName("USER");
		
		user.setActive(1);
		user.setRoles(new HashSet<>(Collections.singletonList(role)));
		userRepository.save(user);
		sendEmail(user);
	}
	
	public void authenticateUserAndSetSession(String username, String password, HttpServletRequest request) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
		
		request.getSession();
		token.setDetails(new WebAuthenticationDetails(request));
		authenticationManager.authenticate(token);
		
		if (token.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(token);
		}
	}
	
	private void sendEmail(User user) {
		Email from = new Email("info@search4work.com");
		String subject = "Search4Work Registration";
		Email to = new Email(user.getEmail());
		Content content = new Content("text/html",
				"Hello " + user.getFirstName() + " " + user.getLastName() + ",<br><br><br>" +
						"Thank you for your registration at Search4Work.<br>" +
						"Your username: " + user.getUsername());
		
		Mail mail = new Mail(from, subject, to, content);
		SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
		Request request = new Request();
		try {
			request.method = Method.POST;
			request.endpoint = "mail/send";
			request.body = mail.build();
			sg.api(request);
		} catch (IOException e) {
			System.err.println("Email was not sent. " + e.getLocalizedMessage());
		}
	}
}
