package com.diptam.SpringBatchLearn.repository;

import com.diptam.SpringBatchLearn.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
