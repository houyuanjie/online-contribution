package edu.xsyu.onlinesubmit.repository;

import edu.xsyu.onlinesubmit.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository extends BaseRepository<User> {

    public UserRepository() {
        super(User.class);
    }

    public Optional<User> findUserByUsername(String username) {
        return Optional.ofNullable(username)
                .filter(str -> !str.isBlank())
                .flatMap(un ->
                        Optional.ofNullable(
                                em.createQuery("SELECT u FROM User u WHERE u.username = :uname", User.class)
                                        .setParameter("uname", un)
                                        .getSingleResult()
                        )
                );
    }

}
