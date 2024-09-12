package pandey.ujjwal.e_comm_backend.configs;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import pandey.ujjwal.e_comm_backend.enums.UserTypes;

//@Enable WebSecurity auto enables the componentScan in the package
//@ComponentScan(basePackages = "pandey.ujjwal.e_comm_backend.config")
@Configuration	// present with @EnableWebSecurity
@EnableWebSecurity
// because of it here, all the classes in same package will be automatically scanned for beans
public class WebSecurityConfig {
	@Autowired
	private CustomAuthenticationFailureHandler customAuthenticationHandler;

	@Bean
	public CorsConfigurationSource corsConfigSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("https://example.com", "http://localhost:3000"));
		configuration.setAllowedMethods(Arrays.asList("*"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	// Configuring our authorization with authorization server
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.cors(cors -> cors.disable()) // Cross-Origin Resource Sharing
				.csrf(csrf -> csrf.disable()) // Cross-Site Request Forgery
				.authorizeHttpRequests(authorize -> {
					authorize.requestMatchers("/", "/home", "/loginPage", "/index", "/test").permitAll();
					authorize.requestMatchers("/actuator/**").hasRole("ADMIN");
//					authorize.requestMatchers("/actuator/**").permitAll();
					authorize.requestMatchers("/register/**").permitAll();
					authorize.anyRequest().authenticated();	// Authentication for all other requests
				})
//				.formLogin(Customizer.withDefaults())
				.formLogin(form -> {
//					form.loginPage("/loginPage");	// set custom login page
					form.failureHandler(customAuthenticationHandler);
					form.successHandler(customAuthenticationHandler);
				})
				.oauth2Login(Customizer.withDefaults())
				.build();
	}

	@Bean // Hard coded users working fine for formLogin
	public UserDetailsService hardCodedUsers() {
		UserDetails admin = org.springframework.security.core.userdetails.User
				.withUsername(UserTypes.ADMIN.getUserName())
				.password(new BCryptPasswordEncoder(11).encode(UserTypes.ADMIN.getUserPassword()))
				.roles(UserTypes.ADMIN.getRole())
				.build();
		UserDetails simple = org.springframework.security.core.userdetails.User
				.withUsername(UserTypes.SIMPLE.getUserName())
				.password(new BCryptPasswordEncoder(11).encode(UserTypes.SIMPLE.getUserPassword()))
				.roles(UserTypes.SIMPLE.getRole(), UserTypes.USER.getRole())
				.build();
		return new InMemoryUserDetailsManager(admin, simple);
	}
}
