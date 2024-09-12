package pandey.ujjwal.e_comm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pandey.ujjwal.e_comm_backend.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

}
