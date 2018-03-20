package ee.ut.cs.wad.AdBoard.application.config;
//https://www.thymeleaf.org/doc/articles/springsecurity.html
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		String[] resources = {"/","/home","/findWork","/signIn","/signup","/about","/offerWork", "/css/**", "/js/**"};
		
		http.cors().and().csrf().disable();
		http.authorizeRequests()
				.antMatchers(HttpMethod.GET, resources).permitAll()
				.antMatchers(HttpMethod.POST, resources).permitAll()
				.anyRequest().authenticated().and().formLogin().permitAll();

//		http
//				.formLogin()
//				.loginPage("/signin/signIn.html")
//				.failureUrl("/errors/error.html")
//				.and()
//				.logout()
//				.logoutSuccessUrl("/aboutUs/about.html");
	}
}
