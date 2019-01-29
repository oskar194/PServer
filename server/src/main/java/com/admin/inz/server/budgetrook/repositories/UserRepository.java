package com.admin.inz.server.budgetrook.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.admin.inz.server.budgetrook.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);
	Optional<User> findById(Long id);
	@Override
	void delete(User user);
}
