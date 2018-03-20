package ee.ut.cs.wad.AdBoard.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	private final UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public void addUser(User user) {
		User existing = userRepository.findUserByUsername(user.getUsername());
		if (existing != null) throw new UnsupportedOperationException("This username is taken");
		
		existing = userRepository.findUserByEmail(user.getEmail());
		if (existing != null) throw new UnsupportedOperationException("User with this email already exists");
		userRepository.save(user);
	}
}
