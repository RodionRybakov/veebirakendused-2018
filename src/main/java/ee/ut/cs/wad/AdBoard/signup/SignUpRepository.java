package ee.ut.cs.wad.AdBoard.signup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface SignUpRepository extends JpaRepository<User, Long>{
	
//	@Query(value = "SELECT * FROM users WHERE username = :username", nativeQuery = true)
	User findUserByUsername(@Param("username") String username);
}
