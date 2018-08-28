package com.henry.boot.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FollowingRepository extends JpaRepository<Following, Long> {
	
	List<Following> findAllByUserId(Long id);
	
	@Query("SELECT COUNT(p) FROM Following p WHERE p.id=id")
	Integer findCountAllByUserId(Long id);
}
