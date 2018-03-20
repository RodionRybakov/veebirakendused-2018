package ee.ut.cs.wad.AdBoard.signup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignUpService {

	private final SignUpRepository signUpRepository;

	@Autowired
	public SignUpService(SignUpRepository signUpRepository) {
		this.signUpRepository = signUpRepository;
	}
	
	public void addUser(User user) {
		User existing = signUpRepository.findUserByUsername(user.getUsername());
		if (existing != null) throw new UnsupportedOperationException("This username is taken");
		signUpRepository.save(user);
	}
}
