package ee.ut.cs.wad.AdBoard.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
	
//	@Query(value = "SELECT * FROM users WHERE username = :username", nativeQuery = true)
	User findUserByUsername(@Param("username") String username);
//	@Query(value = "SELECT * FROM users WHERE email = :email", nativeQuery = true)
	User findUserByEmail(@Param("email") String email);
	@Query(value = "SELECT COUNT(*) FROM users", nativeQuery = true)
	int getAll();
}
