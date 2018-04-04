package ee.ut.cs.wad.AdBoard.offer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ee.ut.cs.wad.AdBoard.offer.dto.OfferDTO;
import ee.ut.cs.wad.AdBoard.user.User;
import ee.ut.cs.wad.AdBoard.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OfferController {
	
	private static final String OFFERS_PAGE = "offer/offers";
	private static final String ADD_OFFER_PAGE = "offer/add";
	
	private final OfferService offerService;
	private final OfferRepository offerRepository;
	private final UserRepository userRepository;
	
	@Autowired
	public OfferController(OfferService offerService, OfferRepository offerRepository, UserRepository userRepository) {
		this.offerService = offerService;
		this.offerRepository = offerRepository;
		this.userRepository = userRepository;
	}
	
	@RequestMapping(value = "/offers", method = RequestMethod.GET)
	public String getOffers(Model model) {
		model.addAttribute("offers", offerRepository.findAll());
		return OFFERS_PAGE;
	}
	
	@RequestMapping(value = "/offers/add", method = RequestMethod.GET)
	public String addOffer() {
		return ADD_OFFER_PAGE;
	}
	
	@RequestMapping(value = "/offers/add", method = RequestMethod.POST)
	public String addOffer(@ModelAttribute OfferDTO offerDTO, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User owner = userRepository.findUserByUsername(auth.getName());
		
		Offer offer = new Offer();
		offer.setTitle(offerDTO.getTitle());
		offer.setPhone(offerDTO.getPhone());
		offer.setAddress(offerDTO.getAddress());
		offer.setDescription(offerDTO.getDescription());
		offer.setOwner(owner);
		
		try {
			offerService.addOffer(offer);
			model.addAttribute("success", "Offer was successfully added");
		} catch (UnsupportedOperationException e) {
			model.addAttribute("error", e.getMessage());
		}
		return ADD_OFFER_PAGE;
	}
	
	@ResponseBody
	@RequestMapping(value = "/offers/get", method = RequestMethod.POST)
	public String getOffer(Long offerId) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		Offer offer = offerService.getOffer(offerId);
		return mapper.writeValueAsString(offer);
	}
	
	@ResponseBody
	@RequestMapping(value = "/offers/remove", method = RequestMethod.POST)
	public String removeOffer(Long offerId) {
		offerService.removeOffer(offerId);
		return "success";
	}
}
