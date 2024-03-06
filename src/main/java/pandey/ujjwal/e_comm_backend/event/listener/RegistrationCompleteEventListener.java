package pandey.ujjwal.e_comm_backend.event.listener;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import pandey.ujjwal.e_comm_backend.entity.User;
import pandey.ujjwal.e_comm_backend.entity.VerificationToken;
import pandey.ujjwal.e_comm_backend.event.RegistrationCompleteEvent;
import pandey.ujjwal.e_comm_backend.service.UserService;

@Component
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

	private final Logger slf4jLogger = LoggerFactory.getLogger(RegistrationCompleteEventListener.class);

	@Autowired
	private UserService userService;

	@Override
	public void onApplicationEvent(RegistrationCompleteEvent event) {
		System.out.println("Inside event listenere." + event.toString());
		// Step-1: Create verification token for the user
		User user = event.getUser();
		String token = UUID.randomUUID().toString();
		VerificationToken savedVerificationTokenForUser = userService.saveVerificationTokenForUser(user, token);
		System.out.println("Verification Token For User:\n" + savedVerificationTokenForUser);

		// Step-2: Link for activation to user
		String contextPathApplicationURl = event.getApplicationActivationUrl() + "verifyRegistration?token=" + token;

		// Create utlity to send send account Verification Email, and use that utlity to
		// Temperary way:
		slf4jLogger.info("\n\nClick link to verify account:\n" + contextPathApplicationURl);
		// Step-3: Send main to the user
	 }

}
