package ee.ut.cs.wad.AdBoard.offer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ee.ut.cs.wad.AdBoard.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "offers")
public class Offer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String title;
	
	@NotNull
	private String description;
	
	private int phone;
	private String address;
	private String city;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "offers_categories", joinColumns = @JoinColumn(name = "offer_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	private Set<Category> categories;
	
	@JsonIgnore
	@ManyToOne
	@JoinTable(name = "offers_users", joinColumns = @JoinColumn(name = "offer_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private User owner;
	
	@Override
	public String toString() {
		return "Offer{" +
				"id=" + id +
				", title='" + title + '\'' +
				", description='" + description + '\'' +
				", phone=" + phone +
				", address='" + address + '\'' +
				", city='" + city + '\'' +
				", categories=" + categories.toString() +
				'}';
	}
}
