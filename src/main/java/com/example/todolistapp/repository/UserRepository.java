package com.example.todolistapp.repository;

import com.example.todolistapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);

//    @Query(nativeQuery = true, value = "select * :: users @@ user like %:question% or email like %:question%")
    @Query(nativeQuery = true, value = "select * from  users  where username like :question " +
            "or first_name like :question or last_name like :question")
    Optional<List<User>> findBySearch(String question);
}
