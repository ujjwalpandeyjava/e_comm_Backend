package pandey.ujjwal.e_comm_backend.controllers;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import pandey.ujjwal.e_comm_backend.entity.User;
import pandey.ujjwal.e_comm_backend.event.RegistrationCompleteEvent;
import pandey.ujjwal.e_comm_backend.model.UserModel;
import pandey.ujjwal.e_comm_backend.service.UserService;
import pandey.ujjwal.e_comm_backend.utlity.ReqResRelated;


@Controller // Generic annotation
// Working with Thymeleaf, Add the Thymeleaf dependency to your project's pom.xml
public class UIEndpoints {

	@Autowired
	private UserService userService;
	@Autowired
	private ApplicationEventPublisher appEvntPublisher;
	@Autowired
	private RegistrationCompleteEvent registrationCompleteEvent;

	@GetMapping(path = { "", "/", "/home" })
	public String home() {
		System.err.println("home");
		return "home";
	}

	@GetMapping(path = { "/index" })
	public String homeIndex() {
		return "index";
	}

	@GetMapping(path = { "/loginPage" })
	public String loginPage() {
		System.err.println("loginPage");
		return "loginPage";
	}

	@GetMapping(value = "/customRegister")
	public String customRegister() {
		return "customRegister";
	}

	@PostMapping("/registerUser")
	@ResponseBody
	public ResponseEntity<Map<String, String>> registerUser(@RequestBody UserModel userModel,
			final HttpServletRequest req) {
		System.out.println("\n\nRegister url: " + userModel);
		Map<String, String> response = new HashMap<>();
		Map<String, Object> newUser = userService.registerUser(userModel);

		System.out.println("newUser: " + newUser);
		if (newUser.get("MESSAGE").toString().equalsIgnoreCase("PasswordMismatched")) {
			response.put("MESSAGE", "Confirm Password Mismatched!");
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.NOT_ACCEPTABLE);
		} else if (newUser.get("MESSAGE").toString().equalsIgnoreCase("DUPLICATE_ENTRY")) {
			response.put("MESSAGE", "FAILED: ID already exists!");
			response.put("NEXT_ACTION", "Use other e-mail account");
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.FOUND);
		} else {
			registrationCompleteEvent.setUser((User) newUser.get("user"));
			registrationCompleteEvent.setApplicationActivationUrl(ReqResRelated.getApplicationURl(req));
			appEvntPublisher.publishEvent(registrationCompleteEvent);
			response.put("MESSAGE", "SUCCESS");
			response.put("NEXT_ACTION", "Check e-mail to activate account");
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.CREATED);
		}
	}

	@PostMapping("/registerUser/verifyRegistration")
	@ResponseBody
	public String verifyUserRegistration(@RequestParam("token") String tokenToVerify) {
		String results = userService.validateVerificationToken(tokenToVerify);
		if (results.equalsIgnoreCase("VALID_TOKEN"))
			return "User verified";
		else if (results.equalsIgnoreCase("EXPIRED_TOKEN"))
			return "Time Expired, Register Again.";
		else // IN_VALID_TOKEN
			return "Bad User";
	}

	@PostMapping("/registerUser/reGenerateVerificationToken")
	@ResponseBody
	public String reGenerateVerificationToken() {
		return null;
	}

	@GetMapping("forgetPassword")
	@ResponseBody
	public Principal forgetPassword(Principal pricipal) {
		System.out.println(pricipal);
		return pricipal;
	}

	@PostMapping("/loginUser")
	@ResponseBody
	public String loginUser() {
		return null;
	}
}
