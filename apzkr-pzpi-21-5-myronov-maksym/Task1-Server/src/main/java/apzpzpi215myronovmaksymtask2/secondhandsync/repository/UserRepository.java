package apzpzpi215myronovmaksymtask2.secondhandsync.repository;

import apzpzpi215myronovmaksymtask2.secondhandsync.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

}
