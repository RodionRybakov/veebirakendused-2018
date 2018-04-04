package ee.ut.cs.wad.AdBoard.offer;

import ee.ut.cs.wad.AdBoard.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {

//	@Query(value = "SELECT * FROM offers WHERE username = :username", nativeQuery = true)
	List<Offer> findAll();
//	@Query(value = "SELECT * FROM offers_users JOIN users user_id=users.id JOIN offers ON offer_id=offers.id WHERE username = :username", nativeQuery = true)
	List<Offer> findOffersByOwner(User owner);
//	@Query(value = "SELECT * FROM offer WHERE id = :id", nativeQuery = true)
	Offer findOfferById(Long id);
}
