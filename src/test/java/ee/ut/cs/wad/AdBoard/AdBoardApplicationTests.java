package ee.ut.cs.wad.AdBoard;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AdBoardApplicationTests {
	static WebDriver driver;
	String demo = "https://search4work.herokuapp.com/";
	//String demo = "http://localhost:8080/";
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

	@Test
	public void ketSensetiveTest() {
		String username = randomString();
		String email = username+"@random.ee";
		String pswd1 = "123456a";
		String pswd2 = "123456A";
		registAccount(username,email,pswd1,pswd2);
		String style = driver.findElement(By.id("passwordConfirmation")).getAttribute("style");
		assertEquals("background-color: rgb(218, 79, 74);",
				style);
	}

	@Test
	public void languageTest() {
		setEstLang();

		assertEquals("Töö päevaks või aastaks",
				driver.findElements(By.tagName("h3")).get(0).getText());
		assertEquals("Tuhanded inimesi otsivad tööd iga päev",
				driver.findElements(By.tagName("h3")).get(1).getText());

		assertEquals("Otsin tööd",
				driver.findElements(By.tagName("span")).get(3).getText());

		assertEquals("Otsin töötajaid",
				driver.findElements(By.tagName("span")).get(4).getText());
		assertEquals("Meist",
				driver.findElement(By.id("navbar-collapse-2")).findElements(By.tagName("li")).get(2).getText());
	driver.get("https://search4work.herokuapp.com/about");
		assertEquals("Tere tulemast Search4Work. See on tasuta platvorm kus te saate leida ja otsida tööd.",
				driver.findElement(By.tagName("h4")).getText());
		goHome();
		setEngLang();
		assertEquals("Job for a day or a year",
				driver.findElements(By.tagName("h3")).get(0).getText());
		assertEquals("Thousands of workers search every day",
				driver.findElements(By.tagName("h3")).get(1).getText());

		assertEquals("I want to work",
				driver.findElements(By.tagName("span")).get(3).getText());

		assertEquals("I need workers",
				driver.findElements(By.tagName("span")).get(4).getText());
		assertEquals("About Us",
				driver.findElement(By.id("navbar-collapse-2")).findElements(By.tagName("li")).get(2).getText());
		driver.get("https://search4work.herokuapp.com/about");

		assertEquals("Welcome to Search4Work. It is a free platform where you can find and offer work.",
				driver.findElement(By.tagName("h4")).getText());

		goHome();
		String userName = createUserName();
		goHome();
		assertEquals("Add offer",
				driver.findElement(By.tagName("ul")).findElements(By.tagName("li")).get(3).getText());
		assertEquals("Account",
				driver.findElement(By.tagName("ul")).findElements(By.tagName("li")).get(4).getText());
		assertEquals("Logout",
				driver.findElement(By.tagName("ul")).findElements(By.tagName("li")).get(5).getText());
		setEstLang();

		assertEquals("Lisa kuulutus",
				driver.findElement(By.tagName("ul")).findElements(By.tagName("li")).get(3).getText());
		assertEquals("Konto",
				driver.findElement(By.tagName("ul")).findElements(By.tagName("li")).get(4).getText());
		assertEquals("Välju",
				driver.findElement(By.tagName("ul")).findElements(By.tagName("li")).get(5).getText());

	}

	@Test
	public void findWorkTest() {
		goToPage("I want to work");
		assertEquals("https://search4work.herokuapp.com/offers",
				driver.getCurrentUrl());
	}
	@Test
	public void needWorkersTest() {
		goToPage("I need workers");
		assertEquals("https://search4work.herokuapp.com/login",
				driver.getCurrentUrl());
		String user = createUserName();
		goHome();
		goToPage("I need workers");
		assertEquals("https://search4work.herokuapp.com/offers/add",
				driver.getCurrentUrl());
		addAdw(user,user+"TITLE");
		goHome();
		goToPage("I want to work");
		assertEquals(findWorkByName(user+"TITLE"),true);

	}


	@Test
	public void loginTests() {
		String username = randomString();
		String email = username+"@random.ee";
		String pswd1 = "123456";
		registAccount(username,email,pswd1,pswd1);
		logOutClick();
		logIn(username,pswd1);
		driver.get("https://search4work.herokuapp.com/account");
		assertEquals("My offers",
				driver.findElement(By.tagName("h2")).getText());
		logOutClick();

		driver.get("https://search4work.herokuapp.com/login");
		logIn(username,pswd1);
		assertEquals("You have been logged in successfully",
				driver.findElement(By.tagName("h2")).getText());
		logOutClick();
	}
	@Test
	public void WrongPageTest(){
		createUserName();
		driver.get("https://search4work.herokuapp.com/asd");

		assertEquals("404 Not Found\n" +
						"Something went wrong",
				driver.findElement(By.tagName("h1")).getText());
		driver.findElement(By.linkText("Take Me Home")).click();
		assertEquals("https://search4work.herokuapp.com/",
				driver.getCurrentUrl());
		logOutClick();
	}

	@Test
	public void logOutTests() {
		String username = randomString();
		String email = username+"@random.ee";
		String pswd1 = "123456";
		//System.out.println(username);
		registAccount(username,email,pswd1,pswd1);
		logOutClick();
		driver.get("https://search4work.herokuapp.com/account");
		System.out.println(driver.getCurrentUrl()+ " URL");
		assertEquals("Login",
				driver.findElement(By.tagName("h2")).getText());
	}

	@Test
	public void navBarButtonTest(){
		goToSignUpPageThroughtNavBar();
		assertEquals("Feel free to Sign Up",driver.findElements(By.tagName("span")).get(3).getText());
		assertEquals("https://search4work.herokuapp.com/signup",
				driver.getCurrentUrl());
		goToGooglePageThroughtNavBar();
		assertEquals("accounts.google.com",
				driver.getCurrentUrl().replace("https://","").split("/")[0]);
		assertEquals(2,
				driver.getCurrentUrl().split("search4work.herokuapp.com").length);
		goHome();
		goToSmartIdPageThroughtNavBar();
		assertEquals("Smart-ID",
				driver.findElement(By.tagName("h2")).getText());
		driver.findElement(By.linkText("About Us")).click();
		assertEquals("https://search4work.herokuapp.com/about",
				driver.getCurrentUrl());
		driver.findElement(By.linkText("Search4Work")).click();
		assertEquals("https://search4work.herokuapp.com/",
				driver.getCurrentUrl());
		setEstLang();
		assertEquals("https://search4work.herokuapp.com/?lang=et",
				driver.getCurrentUrl());
		setEngLang();
		assertEquals("https://search4work.herokuapp.com/?lang=en",
				driver.getCurrentUrl());

	}

	@Test
	public void SignUpTest() {
		String username = randomString();
		String email = username+"@random.ee";
		String pswd1 = "123456";
		String pswd2 = "12345612123412341243";

		System.out.println(username);
		registAccount(username,email,pswd1,pswd1);
		assertEquals("You were successfully registered",
				driver.findElement(By.id("fileForm")).findElement(By.tagName("div")).getText());
		logOutClick();
		registAccount(username,email,pswd1,pswd1);
		assertEquals("This username is taken",
				driver.findElement(By.id("fileForm")).findElement(By.tagName("div")).getText());
		goToSignUpPageThroughtNavBar();
		String username2 = randomString();
		registAccount(username2,email,pswd1,pswd2);
		String style = driver.findElement(By.id("passwordConfirmation")).getAttribute("style");
		assertEquals("background-color: rgb(218, 79, 74);",
				style);
	}
	////////////////// DELETE IT
	@Test
	public void checkMethods(){
		goHome();
		goToPage("I want to work");
		List<WebElement> webElements = driver.findElements(By.tagName("h3"));
		for (WebElement el: webElements){
			System.out.println(el.getText());
		}
	}
///////////////////////////////
	@Test
	public void addAdwTest() {
		String username = randomString();
		String email = username+"@random.ee";
		String pswd1 = "123456";
		String title = username+"TITLE";
		registAccount(username,email,pswd1,pswd1);
		goToPage("Add offer");
		addAdw(username,title);
		assertEquals("Offer was successfully added",
				driver.findElement(By.id("fileForm")).findElement(By.tagName("div")).getText());
		goHome();
		goToPage("I want to work");
		assertEquals(findWorkByName(title),true);
		logOutClick();
	}


	@After
	public void tearDown(){
		try {
			logOutClick();
		}
		catch (Exception E){}
		driver.close();
	}


	private void setEstLang(){
		driver.findElement(By.tagName("nav")).findElements(By.tagName("img")).get(1).click();
	}
	private void setEngLang(){
		driver.findElement(By.tagName("nav")).findElements(By.tagName("img")).get(0).click();
	}

	private void goToSignUpPageThroughtNavBar(){
		driver.findElement(By.tagName("nav")).findElement(By.className("dropdown-toggle")).click();
		driver.findElement(By.tagName("nav")).findElement(By.className("dropdown-menu")).findElement(By.className("bottom")).findElement(By.tagName("a")).click();
	}

	private void goToGooglePageThroughtNavBar(){
		driver.findElement(By.tagName("nav")).findElement(By.className("dropdown-toggle")).click();
		driver.findElement(By.tagName("nav")).findElement(By.className("dropdown-menu")).findElement(By.className("social-buttons")).findElements(By.tagName("a")).get(0).click();
	}
	private void goToSmartIdPageThroughtNavBar(){
		driver.findElement(By.tagName("nav")).findElement(By.className("dropdown-toggle")).click();
		driver.findElement(By.tagName("nav")).findElement(By.className("dropdown-menu")).findElement(By.className("social-buttons")).findElements(By.tagName("a")).get(1).click();
	}

	private void goHome(){
		driver.get(demo);
	}

	private String randomString(){
		Random r = new Random();
		StringBuilder sb = new StringBuilder();
		String alphabet  = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz1234567890";
		for (int i = 0; i < 8; i++) {
			sb.append(alphabet .charAt(r.nextInt(alphabet .length())));
			r.nextInt();
		}
		sb.append(Math.round(Math.random()*10000000));
		return sb.toString();
	}

	private void registAccount(String username, String email,String pswd1,String pswd2){
		goHome();
		goToSignUpPageThroughtNavBar();
		driver.findElement(By.id("usernameSignUp")).sendKeys(username);
		driver.findElement(By.id("email")).sendKeys(email);
		driver.findElement(By.id("firstName")).sendKeys(username);
		driver.findElement(By.id("lastName")).sendKeys(username);
		driver.findElement(By.id("passwordSignUp")).sendKeys(pswd1);
		driver.findElement(By.id("passwordConfirmation")).sendKeys(pswd2);
		driver.findElement(By.id("register")).click();
	}
	private void logOutClick(){
		driver.findElement(By.tagName("nav")).findElements(By.tagName("li")).get(5).click();
	}
	private void logInClick(){
		driver.findElement(By.tagName("nav")).findElements(By.tagName("li")).get(3).click();
	}
	private void logIn(String username,String pswd1){
		logInClick();
		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.id("password")).sendKeys(pswd1);
		driver.findElement(By.id("login-nav")).findElement(By.tagName("button")).click();
	}
	private void goToPage(String page){
		driver.findElement(By.linkText(page)).click();
	}

	private boolean findWorkByName(String workTitle){
		List<WebElement> webElements = driver.findElements(By.tagName("h3"));
		for (WebElement el: webElements){
			if(el.getText().equals(workTitle)) return true;
		}
		return false;
	}
	private String createUserName(){
		String username = randomString();
		String email = username+"@random.ee";
		String pswd1 = "123456";
		registAccount(username,email,pswd1,pswd1);
		return username;
	}

	private void addAdw(String username,String title){
		driver.findElements(By.className("form-group")).get(0).findElement(By.tagName("input")).sendKeys(title);
		driver.findElements(By.className("form-group")).get(1).findElement(By.tagName("input")).sendKeys(Math.round(Math.random()*10000000)+"");
		driver.findElements(By.className("form-group")).get(2).findElement(By.tagName("input")).sendKeys(username + " " +Math.round(Math.random()*10000000)+"");
		driver.findElements(By.className("form-group")).get(3).findElement(By.tagName("textarea")).sendKeys(username + " " +Math.round(Math.random()*10000000)+" DESCRIPTION");
		driver.findElements(By.tagName("input")).get(5).click();
	}
}

