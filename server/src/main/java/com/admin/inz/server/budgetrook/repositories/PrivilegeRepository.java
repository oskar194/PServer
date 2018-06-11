package com.admin.inz.server.budgetrook.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.admin.inz.server.budgetrook.model.Privilege;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
	public Privilege findByName(String name);

	@Override
	void delete(Privilege privilege);
}
