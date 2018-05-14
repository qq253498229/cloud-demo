package com.example.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author wangbin
 */
public interface UserRepository extends JpaRepository<User, String> {
  /**
   * 通过用户名获取用户实体
   *
   * @param username 用户名
   * @return 用户实体
   */
  @Query("select u from User u left join fetch u.roles r where u.username=:username")
  User findByUsername(@Param("username") String username);
}
