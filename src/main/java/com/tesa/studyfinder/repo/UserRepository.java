package com.tesa.studyfinder.repo;

import com.tesa.studyfinder.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    // No extra methods needed—findById(email) and save() are inherited
}
