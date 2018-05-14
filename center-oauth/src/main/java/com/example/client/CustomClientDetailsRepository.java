package com.example.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author wangbin
 */
public interface CustomClientDetailsRepository extends JpaRepository<CustomClientDetails, String> {
  /**
   * 通过clientId查找client信息
   *
   * @param clientId clientId
   * @return client信息
   */
  @Query("select c from CustomClientDetails c where c.clientId=:clientId")
  CustomClientDetails findByClientId(@Param("clientId") String clientId);
}
