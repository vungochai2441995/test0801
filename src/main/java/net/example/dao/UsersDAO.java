package net.example.dao;

import net.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersDAO extends JpaRepository<User,Integer> {
    User findByUsername(String username);
    User findByEmail(String email);

}
