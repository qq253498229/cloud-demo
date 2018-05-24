package com.example.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author wangbin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_user")
public class User {
  @Id
  private String id;
  @Column(unique = true, nullable = false)
  private String username;
  @Column(nullable = false)
  private String password;
  @Column(nullable = false)
  private Boolean enabled = true;
}
