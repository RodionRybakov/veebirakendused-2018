package ee.ut.cs.wad.AdBoard.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	
	@Autowired
	public UserService(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
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
}
