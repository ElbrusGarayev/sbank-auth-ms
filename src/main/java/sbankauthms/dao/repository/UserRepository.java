package sbankauthms.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sbankauthms.dao.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findFirstByCif(String cif);

}
