package pandey.ujjwal.e_comm_backend.service;

import java.util.Map;

import pandey.ujjwal.e_comm_backend.entity.User;
import pandey.ujjwal.e_comm_backend.entity.VerificationToken;
import pandey.ujjwal.e_comm_backend.model.UserModel;

public interface UserService {

	Map<String, Object> registerUser(UserModel userModel);

	VerificationToken saveVerificationTokenForUser(User user, String token);

	String validateVerificationToken(String tokenToVerify);

}
