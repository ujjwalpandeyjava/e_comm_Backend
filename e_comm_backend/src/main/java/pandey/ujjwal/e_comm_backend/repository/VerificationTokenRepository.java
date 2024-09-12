package pandey.ujjwal.e_comm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pandey.ujjwal.e_comm_backend.entity.VerificationToken;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

	VerificationToken findByToken(String tokenToVerify);
}
