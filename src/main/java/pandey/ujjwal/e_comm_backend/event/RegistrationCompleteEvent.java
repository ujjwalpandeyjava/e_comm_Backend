package pandey.ujjwal.e_comm_backend.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import pandey.ujjwal.e_comm_backend.entity.User;

@Component
public class RegistrationCompleteEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	private User user;
	private String applicationActivationUrl = null;

	public RegistrationCompleteEvent(User user) {
		super(user);
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getApplicationActivationUrl() {
		return applicationActivationUrl;
	}

	public void setApplicationActivationUrl(String applicationActivationUrl) {
		this.applicationActivationUrl = applicationActivationUrl;
	}

	@Override
	public String toString() {
		return "RegistrationCompleteEvent [user=" + user + ", applicationActivationUrl=" + applicationActivationUrl
				+ "]";
	}
}