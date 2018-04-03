package ee.ut.cs.wad.AdBoard.offer;

import ee.ut.cs.wad.AdBoard.offer.dto.OfferDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class OfferController {
	
	private static final String OFFERS_PAGE = "offer/offers";
	private static final String ADD_OFFER_PAGE = "offer/add";
	
	private final OfferService offerService;
	private final OfferRepository offerRepository;
	
	@Autowired
	public OfferController(OfferService offerService, OfferRepository offerRepository) {
		this.offerService = offerService;
		this.offerRepository = offerRepository;
	}
	
	@RequestMapping(value = "/offers", method = RequestMethod.GET)
	public String getOffers(Model model) {
//		model.addAttribute("total", offerRepository.countAllById());
		model.addAttribute("offers", offerRepository.findAll());
		return OFFERS_PAGE;
	}
	
	@RequestMapping(value = "/add-offer", method = RequestMethod.GET)
	public String addOffer() {
		return ADD_OFFER_PAGE;
	}
	
	@RequestMapping(value = "/add-offer", method = RequestMethod.POST)
	public String addOffer(@ModelAttribute OfferDTO offerDTO, Model model) {
		Offer offer = new Offer();
		
		try {
			offerService.addOffer(offer);
			model.addAttribute("success", "Offer was successfully added");
		} catch (UnsupportedOperationException e) {
			model.addAttribute("error", e.getMessage());
		}
		return ADD_OFFER_PAGE;
	}
}
