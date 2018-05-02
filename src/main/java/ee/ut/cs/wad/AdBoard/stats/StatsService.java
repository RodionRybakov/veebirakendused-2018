package ee.ut.cs.wad.AdBoard.stats;

import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;

@Service
public class StatsService {
	
	private final StatsRepository statsRepository;
	
	public StatsService(StatsRepository statsRepository) {
		this.statsRepository = statsRepository;
	}
	
	public void saveUserAgent(HttpServletRequest request) {
		String remoteAddr = request.getRemoteAddr();
		UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
		String browser = userAgent.getBrowser().getName();
		String operatingSystem = userAgent.getOperatingSystem().getName();
		
		Stats stats = new Stats();
		stats.setIp(remoteAddr);
		stats.setBrowser(browser);
		stats.setOperatingSystem(operatingSystem);
		stats.setDate(new Date());
		
		if (!statsRepository.existsStatsByIpAndDateAfter(remoteAddr, yesterday())) {
			statsRepository.save(stats);
		}
	}
	
	private Date yesterday() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}
}
