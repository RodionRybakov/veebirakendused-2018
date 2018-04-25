package ee.ut.cs.wad.AdBoard;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AdBoardApplicationTests {
	static WebDriver driver;
	String demo = "https://search4work.herokuapp.com/";
	@Before
	public void setUp(){
		System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(demo);
	}

	@Test
	public void contextLoads() {
		driver.get(demo);
	}


}

