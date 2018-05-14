package com.example.user;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wangbin
 */
public interface RoleRepository extends JpaRepository<Role, String> {
}
