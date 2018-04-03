package ee.ut.cs.wad.AdBoard.offer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfferService {
	
	private final OfferRepository offerRepository;
	
	@Autowired
	public OfferService(OfferRepository offerRepository) {
		this.offerRepository = offerRepository;
	}
	
	public void addOffer(Offer offer) {
		offerRepository.save(offer);
	}
}
