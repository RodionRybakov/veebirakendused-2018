package ee.ut.cs.wad.AdBoard.offer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {
	
	List<Offer> findAll();
}
