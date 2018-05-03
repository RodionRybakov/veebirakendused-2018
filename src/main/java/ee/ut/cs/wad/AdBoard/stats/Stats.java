package ee.ut.cs.wad.AdBoard.stats;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "stats")
public class Stats {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String ip;
	private String browser;
	private String operatingSystem;
	private Date date;
	
}
