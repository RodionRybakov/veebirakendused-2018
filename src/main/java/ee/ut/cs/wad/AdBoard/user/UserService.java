package ee.ut.cs.wad.AdBoard.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashSet;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final AuthenticationManager authenticationManager;
	private final UserDetailsService userDetailsService;
	
	@Autowired
	public UserService(UserRepository userRepository, RoleRepository roleRepository, AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
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
}
