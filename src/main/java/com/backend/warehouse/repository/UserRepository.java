package com.backend.warehouse.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.warehouse.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query(value = "SELECT * FROM users u WHERE u.username = ?1", nativeQuery = true)
	Optional<User> findByUser(String username);
	@Query(value = "SELECT * FROM users u WHERE u.username = ?1", nativeQuery = true)
	Optional<User> existsByUser(String username);
	
//	public Optional<User> findByUsername(String username);
//	
//	@Query(value = "SELECT * From users u Where u.user_id IN :users", nativeQuery = true)
//	public List<User> findAllUsersByUserIds(@Param("users") List<Long> userIds);
//	
//	@Query(value = "SELECT DISTINCT * From users u Where u.profile_name LIKE %:query% OR u.gmail LIKE %:query%", nativeQuery = true)
//	public List<User> findByQuery(@Param("query") String query);
//	
}
