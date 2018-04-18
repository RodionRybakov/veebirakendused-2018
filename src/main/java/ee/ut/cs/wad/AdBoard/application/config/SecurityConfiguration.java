package ee.ut.cs.wad.AdBoard.application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	private final UserDetailsService userDetailsService;
	private final Environment env;
	
	@Autowired
	public SecurityConfiguration(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService, Environment env) {
		this.userDetailsService = userDetailsService;
		this.env = env;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		String[] resources = {"/", "/signup", "/about", "/offers", "/login/smartid"};
		
		http.cors().and().csrf().disable();
		http.authorizeRequests()
				.antMatchers(HttpMethod.GET, resources).permitAll()
				.antMatchers(HttpMethod.POST, resources).permitAll()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.successHandler(successHandler())
				.permitAll()
				.and()
			.oauth2Login()
				.loginPage("/login")
				.clientRegistrationRepository(clientRegistrationRepository())
				.authorizedClientService(authorizedClientService())
				.and()
			.logout()
				.logoutSuccessUrl("/")
				.permitAll();
	}
	
	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers( "/css/**", "/js/**", "/img/**", "/webjars/**");
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
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
	
	@Bean
	public SpringSecurityDialect securityDialect() {
		return new SpringSecurityDialect();
	}
	
	@Bean
	public ClientRegistrationRepository clientRegistrationRepository() {
		List<ClientRegistration> registrations = Stream.of("google")
				.map(this::getRegistration)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());

		return new InMemoryClientRegistrationRepository(registrations);
	}

	@Bean
	public OAuth2AuthorizedClientService authorizedClientService() {
		return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository());
	}

	private ClientRegistration getRegistration(String client) {
		String clientId = env.getProperty("security.oauth2.client.client-id");
		String clientSecret = env.getProperty("security.oauth2.client.client-secret");

		return CommonOAuth2Provider.GOOGLE.getBuilder(client)
				.clientId(clientId)
				.clientSecret(clientSecret)
				.build();
	}
}
