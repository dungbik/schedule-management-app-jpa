package nbc.sma.repository;

import nbc.sma.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * email 이 일치하는 User 정보 가져오기
     * @param email 이메일
     * @return email 이 일치하는 User 정보
     */
    Optional<User> findByEmail(String email);
}
