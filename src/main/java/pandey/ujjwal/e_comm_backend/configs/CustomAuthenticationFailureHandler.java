package pandey.ujjwal.e_comm_backend.configs;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler, AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		String message = "User not exist or allowed to view this page";
		System.out.println(message + " | " + request.getContextPath() + "/loginPage?error");
		request.getSession().setAttribute("error", message);
		response.sendRedirect(request.getContextPath() + "/loginPage?error");
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		String message = "User allowed to view this page, success!";
		System.out.println(message + " | " + request.getContextPath() + "/loginPage?success");
		request.getSession().setAttribute("success", message);
		response.sendRedirect(request.getContextPath() + "/loginPage?success");
	}

}
