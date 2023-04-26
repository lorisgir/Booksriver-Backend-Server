package com.ghibo.userserver.repositories;

import com.ghibo.userserver.domain.exceptions.ResourceNotFoundException;
import com.ghibo.userserver.domain.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailOrGoogleId(String email, String googleId);

    default User getById(long id) {
        Optional<User> optionalUser = findById(id);
        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return optionalUser.get();
    }


    List<User> findTop100ByUsernameContainingIgnoreCase(String query);

}
