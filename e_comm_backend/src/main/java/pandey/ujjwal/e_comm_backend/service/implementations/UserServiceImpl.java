package pandey.ujjwal.e_comm_backend.service.implementations;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pandey.ujjwal.e_comm_backend.entity.User;
import pandey.ujjwal.e_comm_backend.entity.VerificationToken;
import pandey.ujjwal.e_comm_backend.model.UserModel;
import pandey.ujjwal.e_comm_backend.repository.UserRepository;
import pandey.ujjwal.e_comm_backend.repository.VerificationTokenRepository;
import pandey.ujjwal.e_comm_backend.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private VerificationTokenRepository verificationTokenRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	private final static String VALID_TOKEN = "VALID_TOKEN";
	private final static String INVALID_TOKEN = "IN_VALID_TOKEN";
	private final static String EXPIRED_TOKEN = "EXPIRED_TOKEN";

	@Override
	public Map<String, Object> registerUser(UserModel userModel) {
		System.out.println("Inside register user: ");
		Map<String, Object> response = new HashMap<>();
		response.put("MESSAGE", "error");
		User newUser = new User();
		if (!userModel.getPassword().equals(userModel.getMatchingPassword())) {			
			response.replace("MESSAGE", "PasswordMismatched");
			return response;
		} else {
			User userExists = userRepository.findByEmail(userModel.getEmail());
			if(userExists != null) {
				response.replace("MESSAGE", "DUPLICATE_ENTRY");
				response.put("user", userExists);
				return response;
			} else {
				newUser.setEmail(userModel.getEmail());
				newUser.setFirstName(userModel.getFirstName());
				newUser.setLastName(userModel.getLastName());
				newUser.setPassword(passwordEncoder.encode(userModel.getPassword()));
				User savedUser = userRepository.save(newUser);
				response.replace("MESSAGE", "SUCCESS");
				response.put("user", savedUser);
				return response;
			}
		}
	}

	@Override
	public VerificationToken saveVerificationTokenForUser(User user, String token) {
		// Connecting Token and User, then save in DB with expiration time
		VerificationToken userConnectedVerificationToken = new VerificationToken(user, token); // Connecting
		return verificationTokenRepository.save(userConnectedVerificationToken);
	}

	@Override
	public String validateVerificationToken(String tokenToVerify) {
		VerificationToken tokenRow = verificationTokenRepository.findByToken(tokenToVerify);
		if (tokenRow == null) {
			return INVALID_TOKEN;
		} else {
			User user = tokenRow.getUser();
			if (tokenRow.getExpirationTime().after(new Date())) {
				user.setEnabled(true);
				userRepository.save(user);
				verificationTokenRepository.delete(tokenRow);
				return VALID_TOKEN;
			} else {
				userRepository.delete(user);
				verificationTokenRepository.delete(tokenRow);
				return EXPIRED_TOKEN;
			}
		}
	}
}