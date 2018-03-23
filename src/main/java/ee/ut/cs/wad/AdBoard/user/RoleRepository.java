package ee.ut.cs.wad.AdBoard.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
//	@Query(value = "SELECT * FROM roles WHERE name = :name", nativeQuery = true)
	Role findRoleByName(String role);
}
