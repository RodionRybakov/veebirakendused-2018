package ee.ut.cs.wad.AdBoard.application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	private final DataSource dataSource;
	
	@Autowired
	public SecurityConfiguration(@Qualifier("dataSource") DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		String[] resources = {"/", "/signup", "/about", "/offers", "/img/**", "/css/**", "/js/**", "/webjars/**"};
		
		http.cors().and().csrf().disable();
		http.authorizeRequests()
				.antMatchers(HttpMethod.GET, resources).permitAll()
				.antMatchers(HttpMethod.POST, resources).permitAll()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
//				.loginProcessingUrl("/login")
//				.failureUrl("/login/failure")
				.usernameParameter("username")
				.passwordParameter("password")
				.successHandler(new RefererRedirectionAuthenticationSuccessHandler())
				.permitAll()
				.and()
			.logout()
//				.logoutUrl("/logout")
//				.logoutSuccessUrl("/")
				.permitAll();
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		auth.jdbcAuthentication()
				.usersByUsernameQuery("SELECT username, password, active FROM users WHERE username=?")
				.authoritiesByUsernameQuery("SELECT username, name FROM users INNER JOIN user_role ON (users.id = user_id) INNER JOIN roles ON (roles.id = role_id) WHERE username=?")
				.dataSource(dataSource)
				.passwordEncoder(passwordEncoder());
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public AuthenticationSuccessHandler successHandler() {
		SimpleUrlAuthenticationSuccessHandler handler = new SimpleUrlAuthenticationSuccessHandler();
		handler.setUseReferer(true);
		return handler;
	}
}
