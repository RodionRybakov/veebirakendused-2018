package ee.ut.cs.wad.AdBoard.offer;

import ee.ut.cs.wad.AdBoard.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class OfferService {
	
	private final OfferRepository offerRepository;
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	public OfferService(OfferRepository offerRepository) {
		this.offerRepository = offerRepository;
	}
	
	public List<Offer> getOffers(User owner) {
		return offerRepository.findOffersByOwner(owner);
	}
	
	public void addOffer(Offer offer) {
		offerRepository.save(offer);
	}
	
	public Offer getOffer(Long id) {
		Offer offer = offerRepository.findOfferById(id);
		if (offer == null) throw new RuntimeException("No offer with id " + id);
		return offer;
	}
	
	@Transactional
	public void removeOffer(Long id) {
		System.out.println(id);
		em.createNativeQuery("DELETE FROM offers WHERE id = :id").setParameter("id", id).executeUpdate();
		em.createNativeQuery("DELETE FROM offers_users WHERE offer_id = :id").setParameter("id", id).executeUpdate();
	}
}
