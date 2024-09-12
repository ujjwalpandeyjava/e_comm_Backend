package pandey.ujjwal.e_comm_backend.configs;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import pandey.ujjwal.e_comm_backend.entity.User;
import pandey.ujjwal.e_comm_backend.event.RegistrationCompleteEvent;

//Auto-Detection: Spring Boot automatically detects @Configuration classes and incorporates their beans into the application context.
//Injection: Inject beans into other components using @Autowired or constructor injection.
@Configuration
public class BeanHandler {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(11);
	}

	@Bean
	public User getUser() {
		return new User();
	}

	@Bean
	@Scope(value = "prototype")
	public ApplicationEvent getRegistrationCompleteEvent() {
		return new RegistrationCompleteEvent(getUser());
	}

	@Bean
	public CustomAuthenticationFailureHandler customAuthenticationHandler() {
		return new CustomAuthenticationFailureHandler();
	}

	@Bean	// Not using
	public AuthenticationSuccessHandler customAuthenticationHandlerSimple() {
		return new SimpleUrlAuthenticationSuccessHandler("/loginSuccess");
	}
}
